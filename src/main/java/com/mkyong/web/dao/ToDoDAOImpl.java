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
import java.util.ArrayList;
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
        String sql = "SELECT * FROM todos WHERE id = ?";
        List<ToDo> result = jdbcTemplate.query(sql, new ToDoRowMapper(), id);
        return result.stream().findFirst();
    }


    public List<ToDo> searchToDoByCriteria(ToDoSearchCriteria toDoCriteria) {
        StringBuilder sql = new StringBuilder("SELECT id, userId, title, description, completed FROM todos WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (toDoCriteria.getUserId() != null) {
            sql.append(" AND userId = ?");
            params.add(toDoCriteria.getUserId());
        }

        if (toDoCriteria.getTitle() != null && !toDoCriteria.getTitle().isEmpty()) {
            sql.append(" AND title ILIKE ?");
            params.add("%" + toDoCriteria.getTitle() + "%");
        }

        if (toDoCriteria.getCompleted() != null ) {
            sql.append(" AND completed = ?");
            params.add(toDoCriteria.getCompleted());
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), new ToDoRowMapper());
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
