package com.mkyong.web.dao;

import com.mkyong.web.model.SearchCriteria;
import com.mkyong.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findAll() {
        String sql = "SELECT id, username, password, email, phone, address FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT id, username, password, email, phone, address FROM users WHERE id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserRowMapper());
            return Optional.of(user);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findByCriteria(SearchCriteria criteria) {
        StringBuilder sql = new StringBuilder("SELECT id, username, password, email, phone, address FROM users WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (criteria.getUsername() != null && !criteria.getUsername().isEmpty()) {
            sql.append(" AND username = ?");
            params.add(criteria.getUsername());
        }

        if (criteria.getEmail() != null && !criteria.getEmail().isEmpty()) {
            sql.append(" AND email = ?");
            params.add(criteria.getEmail());
        }

        if (criteria.getAddress() != null && !criteria.getAddress().isEmpty()) {
            sql.append(" AND address = ?");
            params.add(criteria.getAddress());
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), new UserRowMapper());
    }

    @Override
    public void create(User user) {

        String sql = "INSERT INTO users (username, password, email, phone, address) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";

        Long id = jdbcTemplate.queryForObject(sql, new Object[]{
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress()
        }, Long.class);

        user.setId(id);
    }

    @Override
    public void update(User user) {

        String sql = "UPDATE users SET username = ?, password = ?, email = ?, phone = ?, address = ? WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getId()
        );

        if (rowsAffected > 0) {
            System.out.println("User with ID " + user.getId() + " was updated successfully.");
        } else {
            System.out.println("No user found with ID " + user.getId());
        }
    }

    @Override
    public void delete(Long id) {

    }

    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setAddress(rs.getString("address"));
            return user;
        }
    }
}
