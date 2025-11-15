package com.mkyong.web.dao;

import com.mkyong.web.model.SearchCriteria;
import com.mkyong.web.model.ToDo;
import com.mkyong.web.model.ToDoSearchCriteria;
import com.mkyong.web.model.User;

import java.util.List;
import java.util.Optional;

public interface ToDoDAO {

    List<ToDo> findAll();

    Optional<ToDo> findToDoById(Long id);

    List<ToDo> searchToDoByCriteria(ToDoSearchCriteria toDoCriteria);

    void create(ToDo toDo);

    void update(ToDo toDo);

    void delete(Long id);

    List<ToDo> findByUser(User user);

    void updateCompleted(ToDo toDo);

    List<ToDo> findCompletedByUser(User user);

    List<ToDo> findPendingByUser(User user);


}
