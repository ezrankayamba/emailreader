package tz.co.nezatech.systems.api.emailreader.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import tz.co.nezatech.systems.api.emailreader.data.Status;

public interface IDataRepository<T extends Object> {
	public RowMapper<T> getRowMapper();

	public List<T> getAll();

	public List<T> getAll(String column, Object value);

	public Status create(final T entity);

	public T findById(int id);

	public Status update(Integer id, T entity);

	public Status update(T entity);

	public Status delete(Integer id);

	public String sqlFindAll();

	public String sqlFindById();

	public PreparedStatement psCreate(T entity, Connection conn);

	public PreparedStatement psUpdate(T entity, Connection conn);

	public PreparedStatement psUpdateByKey(T entity, Connection conn);

	public PreparedStatement psDelete(Integer id, Connection conn);

	public JdbcTemplate getJdbcTemplate();

	public T schema();

	public boolean updateOnDuplicate();

	public Status onSave(T entity, Status status);

	public List<T> onList(List<T> list);
}
