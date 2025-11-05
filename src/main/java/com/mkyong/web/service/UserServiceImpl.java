package com.mkyong.web.service;

import com.mkyong.web.dao.UserDAO;
import com.mkyong.web.model.SearchCriteria;
import com.mkyong.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return userDAO.findById(id);
    }

    @Override
    public void createUser(User user) {
        userDAO.create(user);
    }

    @Override
    public void updateUser(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("Cannot update user without ID");
        }
        userDAO.update(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        userDAO.delete(id);
    }

    @Override
    public List<User> findByCriteria(SearchCriteria criteria) {
        return userDAO.findByCriteria(criteria);
    }


    public Optional<User> login(String username, String password) {
        if (username == null || password == null) {
            return Optional.empty();
        }

        return userDAO.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));
    }
}
