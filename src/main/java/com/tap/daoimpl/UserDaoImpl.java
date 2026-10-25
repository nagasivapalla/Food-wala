package com.tap.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.UserDao;
import com.tap.model.User;
import com.tap.util.DBConnection;

public class UserDaoImpl implements UserDao {

    private static final String INSERT_USER = 
        "INSERT INTO `user` (`name`, `username`, `password`, `email`, `phoneNumber`, `address`, `role`) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_USER_BY_ID = 
        "SELECT * FROM `user` WHERE `userId` = ?";
    
    private static final String SELECT_USER_BY_EMAIL = 
        "SELECT * FROM `user` WHERE `email` = ?";
    
    private static final String SELECT_ALL_USERS = 
        "SELECT * FROM `user`";
    
    private static final String UPDATE_USER = 
        "UPDATE `user` SET `name` = ?, `phoneNumber` = ?, `address` = ? WHERE `userId` = ?";
    
    private static final String DELETE_USER = 
        "DELETE FROM `user` WHERE `userId` = ?";

    @Override
    public int addUser(User user) {
        int status = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_USER)) {
            
            ps.setString(1, user.getName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhoneNumber());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getRole());

            status = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public User getUser(int userId) {
        User user = null;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_USER_BY_ID)) {
            
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = null;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_USER_BY_EMAIL)) {
            
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_USERS)) {
            
            while (rs.next()) {
                userList.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public int updateUser(User user) {
        int status = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_USER)) {
            
            ps.setString(1, user.getName());
            ps.setString(2, user.getPhoneNumber());
            ps.setString(3, user.getAddress());
            ps.setInt(4, user.getUserId());

            status = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public int deleteUser(int userId) {
        int status = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_USER)) {
            
            ps.setInt(1, userId);
            status = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    // Helper method to keep mapping clean and reusable
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("userId"),
            rs.getString("name"),
            rs.getString("username"),
            rs.getString("password"),
            rs.getString("email"),
            rs.getString("phoneNumber"),
            rs.getString("address"),
            rs.getString("role"),
            rs.getTimestamp("createDate"),
            rs.getTimestamp("lastLoginDate")
        );
    }
}