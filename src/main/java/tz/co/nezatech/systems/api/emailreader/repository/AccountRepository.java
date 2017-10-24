/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tz.co.nezatech.systems.api.emailreader.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tz.co.nezatech.systems.api.emailreader.data.Account;

/**
 *
 * @author godfred.nkayamba
 */
@Repository
public class AccountRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	RowMapper< Account> rowMapper=new RowMapper<Account>() {
		
		@Override
		public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
			Account e = new Account(rs.getInt("id"), rs.getString("name"), rs.getString("msisdn"), rs.getInt("status"),rs.getString("email"),rs.getString("password"));
			return e;
		}
	};
	

	@Transactional(readOnly = true)
	public List<Account> findAll() {
		return jdbcTemplate.query("select * from tbl_account", rowMapper);
	}

	@Transactional(readOnly = true)
	public Account findByMsisdn(String msisdn) {
		return jdbcTemplate.queryForObject("select * from tbl_account where msisdn=?", new Object[] { msisdn },
				rowMapper);
	}
	@Transactional(readOnly = true)
	public List<Account> findActiveAccounts() {
		return jdbcTemplate.query("select * from tbl_account where status=?", new Object[] { 1 },
				rowMapper);
	}
}
