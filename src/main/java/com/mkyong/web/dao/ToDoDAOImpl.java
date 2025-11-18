package com.mkyong.web.dao;

import com.mkyong.web.model.ToDo;
import com.mkyong.web.model.ToDoSearchCriteria;
import com.mkyong.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ToDoDAOImpl implements ToDoDAO {

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

        if (toDoCriteria.getCompleted() != null) {
            sql.append(" AND completed = ?");
            params.add(toDoCriteria.getCompleted());
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), new ToDoRowMapper());
    }


    public void create(ToDo toDo) {
        String sql = "INSERT INTO todos (userId, title, description, completed) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, toDo.getUserId());
            ps.setString(2, toDo.getTitle());
            ps.setString(3, toDo.getDescription());
            ps.setBoolean(4, toDo.isCompleted());
            return ps;
        }, keyHolder);

        toDo.setId(keyHolder.getKey().longValue());
    }

    public void update(ToDo toDo) {
        String sql = "UPDATE todos SET userId = ?, title = ?, description = ?, completed = ? WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql,
                toDo.getUserId(),
                toDo.getTitle(),
                toDo.getDescription(),
                toDo.isCompleted(),
                toDo.getId()
        );

        if (rowsAffected > 0) {
            System.out.println("ToDo with ID " + toDo.getId() + " was updated successfully.");
        } else {
            System.out.println("No ToDo found with ID " + toDo.getId());
        }
    }


    public void delete(Long id) {
        String deleteQuery = "delete from todos where id = ?";
        jdbcTemplate.update(deleteQuery, id);
    }


    public List<ToDo> findByUser(User user) {

        String sql = "SELECT id, userId, title, description, completed FROM todos WHERE userId = ?";

        List<ToDo> toDoList = jdbcTemplate.query(sql, new Object[]{user.getId()}, new ToDoDAOImpl.ToDoRowMapper());
        return toDoList;
    }

    public void updateCompleted(ToDo toDo) {

        String sql = "UPDATE todos SET completed = ? WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql,
                toDo.isCompleted(),
                toDo.getId()
        );
        if (rowsAffected > 0) {
            System.out.println("ToDo with ID " + toDo.getId() + " was updated successfully.");
        } else {
            System.out.println("No ToDo found with ID " + toDo.getId());
        }
    }

    public List<ToDo> findCompletedByUser(User user) {

        String sql = "SELECT * FROM todos WHERE userId = ? AND completed = ?";

        List<ToDo> result = jdbcTemplate.query(sql, new ToDoRowMapper(), user.getId(), true);
        return result;
    }

    public List<ToDo> findPendingByUser(User user) {

        String sql = "SELECT * FROM todos WHERE userId = ? AND completed = ?";

        List<ToDo> result = jdbcTemplate.query(sql, new ToDoRowMapper(), user.getId(), false);
        return result;
    }
}
