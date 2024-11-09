package com.gk.user.service;

import com.gk.user.entities.User;

import java.util.List;

public interface UserService {

    //user operations

    //create
    User saveuser(User user);

    //get all user
    List<User> getAllUser();

    //get single user of given userId
    User getUser(String userId);

    //TODO: Delete
    //TODO: Update
}
