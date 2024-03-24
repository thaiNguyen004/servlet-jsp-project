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

    public User authenticate(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userDao.findByUsername(username) != null;
    }

    public void lockUser(Long id) {
        userDao.lockUserById(id);
    }

    public void unLockUser(Long id) {
        userDao.unlockUserById(id);
    }

    public void deactivateUser(Long id) {
        userDao.deactivateUserById(id);
    }

    public void activateUser(Long id) {
        userDao.activateUserById(id);
    }
}
