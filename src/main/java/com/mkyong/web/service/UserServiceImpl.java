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

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public Optional<User> getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return userDAO.findById(id);
    }


    public void createUser(User user) {

        if (user == null) {
            throw new IllegalArgumentException("User object cannot be null");
        }

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        userDAO.create(user);
    }


    public void updateUser(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("Cannot update user without ID");
        }
        userDAO.update(user);
    }

    public void deleteUser(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        userDAO.delete(id);
    }

    public List<User> findByCriteria(SearchCriteria criteria) {
        return userDAO.findByCriteria(criteria);
    }


    public Optional<User> login(String email, String password) {
        if (email == null || password == null) {
            return Optional.empty();
        }

        return userDAO.findByEmail(email)
                .filter(user -> user.getPassword().equals(password));
    }
}
