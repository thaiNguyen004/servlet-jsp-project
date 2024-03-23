package org.servlet.assignment.user.service;

import org.servlet.assignment.user.User;
import org.servlet.assignment.user.dao.impl.UserDao;

import java.util.List;

public class UserService {

    private final UserDao userDao = new UserDao();

    public List<User> findAllUsers(int limit, int offset) {
        return userDao.findAll(limit, offset);
    }

    public User findById(Long id) {
        return userDao.findById(id);
    }

    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    public boolean existsByUsername(String username) {
        return userDao.findByUsername(username) != null;
    }
}
