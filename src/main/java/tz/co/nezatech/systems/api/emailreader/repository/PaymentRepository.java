/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tz.co.nezatech.systems.api.emailreader.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tz.co.nezatech.systems.api.emailreader.data.Payment;

/**
 *
 * @author godfred.nkayamba
 */
@Repository
public class PaymentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<Payment> findAll() {
        return jdbcTemplate.query("select * from tbl_payment",
                new PaymentRowMapper());
    }

    @Transactional(readOnly = true)
    public Payment findUserById(String txnId) {
        return jdbcTemplate.queryForObject(
                "select * from tbl_payment where txn_id=?",
                new Object[]{txnId}, new PaymentRowMapper());
    }

    public Payment create(final Payment payment) {
        final String sql = "insert into tbl_payment(txn_source,txn_id,payee_msisdn,amount,msisdn,record_date,recorded_by,txn_date,last_update,txn_status) values(?,?, ?,?,?,?,?,?,?,?)";

        //KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                //PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, payment.getTxnSource());
                ps.setString(2, payment.getTxnId());
                ps.setString(3, payment.getPayeeMsisdn());
                ps.setDouble(4, payment.getAmount());
                ps.setString(5, payment.getMsisdn());
                ps.setTimestamp(6, new Timestamp((new Date()).getTime()));
                ps.setString(7, payment.getRecordedBy());
                ps.setTimestamp(8, new Timestamp(payment.getTransDate().getTime()));
                ps.setTimestamp(9, new Timestamp((new Date()).getTime()));
                ps.setInt(10, 0);
                return ps;
            }
        });//holder could be the last param

        //int newUserId = holder.getKey().intValue();
        //payment.setId(newUserId);
        return payment;
    }
}

class PaymentRowMapper implements RowMapper<Payment> {

    @Override
    public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Payment payment = new Payment(rs.getString("txn_source"),rs.getString("txn_id"), rs.getString("payee_msisdn"), rs.getDouble("amount"), rs.getString("msisdn"), rs.getString("recorded_by"), rs.getTimestamp("txn_date"));
        return payment;
    }
}
