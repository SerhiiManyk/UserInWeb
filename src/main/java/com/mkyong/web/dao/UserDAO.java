package com.mkyong.web.dao;

import com.mkyong.web.model.SearchCriteria;
import com.mkyong.web.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDAO {

    List<User> findAll();

    Optional findById(Long id);

    List<User> findByCriteria(SearchCriteria criteria);

    void create(User user);

    void update(User user);

    void delete(Long id);
}
