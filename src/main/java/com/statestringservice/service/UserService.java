package com.statestringservice.service;

import com.statestringservice.model.User;

public interface UserService {

    public User addUser(User user);

    public User getStateString(String userId);
}
