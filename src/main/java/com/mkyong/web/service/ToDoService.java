package com.mkyong.web.service;

import com.mkyong.web.model.ToDo;
import com.mkyong.web.model.ToDoSearchCriteria;
import com.mkyong.web.model.User;

import java.util.List;
import java.util.Optional;

public interface ToDoService {

    void createToDo(ToDo toDo);

    void updateToDo(ToDo toDo);

    void deleteToDo(Long id);

    Optional<ToDo> getToDoById(Long id);

    List<ToDo> getToDosByUser(User user);

    List<ToDo> getCompletedToDos(User user);

    List<ToDo> getPendingToDos(User user);

    List<ToDo> searchToDos(ToDoSearchCriteria criteria);

    void markAsCompleted(ToDo toDo);

}
