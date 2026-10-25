package com.tap.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.RestaurantDao;
import com.tap.model.Restaurant;
import com.tap.util.DBConnection;

public class RestaurantDaoImpl implements RestaurantDao {

    private static final String INSERT_RESTAURANT = 
        "INSERT INTO `restaurant` (`name`, `address`, `phoneNumber`, `cuisineType`, `deliveryTime`, `adminUserId`, `rating`, `isActive`, `imagePath`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_RESTAURANT_BY_ID = 
        "SELECT * FROM `restaurant` WHERE `restaurantId` = ?";

    private static final String SELECT_ALL_RESTAURANTS = 
        "SELECT * FROM `restaurant`";

    private static final String UPDATE_RESTAURANT = 
        "UPDATE `restaurant` SET `name` = ?, `address` = ?, `phoneNumber` = ?, `cuisineType` = ?, `deliveryTime` = ?, `rating` = ?, `isActive` = ?, `imagePath` = ? WHERE `restaurantId` = ?";

    private static final String DELETE_RESTAURANT = 
        "DELETE FROM `restaurant` WHERE `restaurantId` = ?";

    @Override
    public int addRestaurant(Restaurant restaurant) {
        int status = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_RESTAURANT)) {

            ps.setString(1, restaurant.getName());
            ps.setString(2, restaurant.getAddress());
            ps.setString(3, restaurant.getPhoneNumber());
            ps.setString(4, restaurant.getCuisineType());
            ps.setInt(5, restaurant.getDeliveryTime());
            ps.setInt(6, restaurant.getAdminUserId());
            ps.setDouble(7, restaurant.getRating());
            ps.setBoolean(8, restaurant.isActive());
            ps.setString(9, restaurant.getImagePath());

            status = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public Restaurant getRestaurant(int restaurantId) {
        Restaurant restaurant = null;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_RESTAURANT_BY_ID)) {

            ps.setInt(1, restaurantId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    restaurant = extractRestaurantFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurant;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurantList = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_RESTAURANTS)) {

            while (rs.next()) {
                restaurantList.add(extractRestaurantFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurantList;
    }

    @Override
    public int updateRestaurant(Restaurant restaurant) {
        int status = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_RESTAURANT)) {

            ps.setString(1, restaurant.getName());
            ps.setString(2, restaurant.getAddress());
            ps.setString(3, restaurant.getPhoneNumber());
            ps.setString(4, restaurant.getCuisineType());
            ps.setInt(5, restaurant.getDeliveryTime());
            ps.setDouble(6, restaurant.getRating());
            ps.setBoolean(7, restaurant.isActive());
            ps.setString(8, restaurant.getImagePath());
            ps.setInt(9, restaurant.getRestaurantId());

            status = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public int deleteRestaurant(int restaurantId) {
        int status = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_RESTAURANT)) {

            ps.setInt(1, restaurantId);
            status = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    private Restaurant extractRestaurantFromResultSet(ResultSet rs) throws SQLException {
        return new Restaurant(
            rs.getInt("restaurantId"),
            rs.getString("name"),
            rs.getString("address"),
            rs.getString("phoneNumber"),
            rs.getString("cuisineType"),
            rs.getInt("deliveryTime"),
            rs.getInt("adminUserId"),
            rs.getDouble("rating"),
            rs.getBoolean("isActive"),
            rs.getString("imagePath")
        );
    }
}