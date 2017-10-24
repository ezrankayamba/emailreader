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
import java.sql.Statement;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import tz.co.nezatech.systems.api.emailreader.data.Property;
import tz.co.nezatech.systems.api.emailreader.data.Status;

/**
 *
 * @author godfred.nkayamba
 */
@Repository
public class PropertyRepository extends BaseDataRepository<Property>{
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public RowMapper<Property> getRowMapper() {
		return new RowMapper<Property>() {

			@Override
			public Property mapRow(ResultSet rs, int i) throws SQLException {
				Property entity = new Property(rs.getInt("id"), rs.getString("name"), rs.getString("value"));
				return entity;
			}
		};
	}

	@Override
	public String sqlFindAll() {
		return "select p.* from tbl_property p";
	}

	@Override
	public String sqlFindById() {
		return sqlFindAll() + " where p.id = ?";
	}

	@Override
	public PreparedStatement psCreate(Property entity, Connection conn) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("insert into tbl_property (name, value) values (?,?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, entity.getName());
			ps.setString(2, entity.getValue());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ps;
	}

	@Override
	public PreparedStatement psUpdate(Property entity, Connection conn) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("update tbl_property set name=?, value=? where id=?");
			ps.setString(1, entity.getName());
			ps.setString(2, entity.getValue());
			ps.setInt(3, entity.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ps;
	}

	@Override
	public PreparedStatement psDelete(Integer id, Connection conn) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("delete from tbl_property where id=?");
			ps.setInt(1, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ps;
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	@Override
	public Status onSave(Property entity, Status status) {
		return status;
	}

	public Properties getProps() {
		Properties properties=new Properties();
		for(Property p:this.getAll()){
			properties.put(p.getName(), p.getValue());
		}
		
		return properties;
	}
   
}
