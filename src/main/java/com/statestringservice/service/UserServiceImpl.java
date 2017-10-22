package com.statestringservice.service;

import com.statestringservice.model.User;
import com.statestringservice.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserDAO userDAO;

    @Override
    public User addUser(User user) {
        return userDAO.save(user);
    }

    @Override
    public User getStateString(String userId) {
        List<User> userObject = userDAO.findByUserId(userId);
        return userObject.get(0);
    }
}

