package com.mkyong.web.service;

import com.mkyong.web.dao.ToDoDAO;
import com.mkyong.web.model.ToDo;
import com.mkyong.web.model.ToDoSearchCriteria;
import com.mkyong.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ToDoServiceImpl implements ToDoService {

    @Autowired
    private ToDoDAO toDoDAO;

    public void createToDo(ToDo toDo) {
        if (toDo == null) {
            throw new IllegalArgumentException("ToDo object cannot be null");
        }

        if (toDo.getTitle() == null || toDo.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }

        if (toDo.getDescription() == null || toDo.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Description is required");
        }
        toDoDAO.create(toDo);
    }

    public void updateToDo(ToDo toDo) {
        if (toDo == null) {
            throw new IllegalArgumentException(" ToDo not exist");
        }
        if (toDo.getId() == null) {
            throw new IllegalArgumentException("Cannot update ToDo without ID");
        }
        toDoDAO.update(toDo);
    }

    public void deleteToDo(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ToDo ID");
        }
        toDoDAO.delete(id);
    }

    public Optional<ToDo> getToDoById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ToDo ID");
        }
        return toDoDAO.findToDoById(id);
    }

    public List<ToDo> getToDosByUser(User user) {
        if (user == null || user.getId() == null || user.getId() <= 0) {
            throw new IllegalArgumentException("Invalid user");
        }
        return toDoDAO.findByUser(user);
    }

    public List<ToDo> getCompletedToDos(User user) {
        if (user == null || user.getId() == null || user.getId() <= 0) {
            throw new IllegalArgumentException("Invalid user");
        }
        return toDoDAO.findCompletedByUser(user);
    }

    public List<ToDo> getPendingToDos(User user) {
        if (user == null || user.getId() == null || user.getId() <= 0) {
            throw new IllegalArgumentException("Invalid user");
        }
        return toDoDAO.findPendingByUser(user);
    }

    public List<ToDo> searchToDos(ToDoSearchCriteria criteria) {
        return toDoDAO.searchToDoByCriteria(criteria);
    }

    public void markAsCompleted(ToDo toDo) {
        if (toDo == null || toDo.getId() == null) {
            throw new IllegalArgumentException("Invalid ToDo");
        }
        toDoDAO.updateCompleted(toDo);
    }
}
