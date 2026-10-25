package com.tap.dao;

import java.util.List;
import com.tap.model.User;

public interface UserDao {

    int addUser(User user);

    User getUser(int userId);

    User getUserByEmail(String email); // <-- Add this line

    List<User> getAllUsers();

    int updateUser(User user);

    int deleteUser(int userId);
}