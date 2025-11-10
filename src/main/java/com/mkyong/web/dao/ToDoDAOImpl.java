package com.mkyong.web.dao;

import com.mkyong.web.model.ToDo;
import com.mkyong.web.model.ToDoSearchCriteria;
import com.mkyong.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ToDoDAOImpl implements ToDoDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class ToDoRowMapper implements RowMapper<ToDo> {

        public ToDo mapRow(ResultSet rs, int rowNum) throws SQLException {
            ToDo todo = new ToDo();
            todo.setId(rs.getLong("id"));
            todo.setUserId(rs.getLong("user_id"));
            todo.setTitle(rs.getString("title"));
            todo.setDescription(rs.getString("description"));
            todo.setCompleted(rs.getBoolean("completed"));
            return todo;
        }
    }

    public List<ToDo> findAll() {
        String sql = "SELECT * FROM todos";
        return jdbcTemplate.query(sql, new ToDoDAOImpl.ToDoRowMapper());
    }


    public Optional<ToDo> findToDoById(Long id) {
        String sql = "SELECT * FROM todo WHERE id = ?";
        List<ToDo> result = jdbcTemplate.query(sql, new ToDoRowMapper(), id);
        return result.stream().findFirst();
    }


    public List<ToDo> searchToDoByCriteria(ToDoSearchCriteria toDoCriteria) {
        return List.of();
    }


    public void create(ToDo toDo) {

    }


    public void update(ToDo toDo) {

    }


    public void delete(Long id) {

    }


    public List<ToDo> findByUser(User user) {
        return List.of();
    }
}
