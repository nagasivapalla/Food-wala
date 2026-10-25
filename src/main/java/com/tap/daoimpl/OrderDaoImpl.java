package com.tap.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.OrderDao;
import com.tap.model.Order;
import com.tap.util.DBConnection;

public class OrderDaoImpl implements OrderDao {

    private static final String INSERT_ORDER = 
        "INSERT INTO `order` (`restaurantId`, `userId`, `totalAmount`, `status`, `paymentMode`) VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_ORDER_BY_ID = 
        "SELECT * FROM `order` WHERE `orderId` = ?";

    private static final String SELECT_ALL_ORDERS = 
        "SELECT * FROM `order` ORDER BY `orderDate` DESC";

    private static final String SELECT_ORDERS_BY_USER_ID = 
        "SELECT * FROM `order` WHERE `userId` = ? ORDER BY `orderDate` DESC";

    private static final String UPDATE_ORDER = 
        "UPDATE `order` SET `status` = ?, `paymentMode` = ? WHERE `orderId` = ?";

    private static final String DELETE_ORDER = 
        "DELETE FROM `order` WHERE `orderId` = ?";

    @Override
    public int addOrder(Order order) {
        int generatedOrderId = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getRestaurantId());
            ps.setInt(2, order.getUserId());
            ps.setDouble(3, order.getTotalAmount());
            ps.setString(4, order.getStatus());
            ps.setString(5, order.getPaymentMode());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedOrderId = generatedKeys.getInt(1);
                        order.setOrderId(generatedOrderId); // Attaches ID directly back to the object
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedOrderId;
    }

    @Override
    public Order getOrder(int orderId) {
        Order order = null;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ORDER_BY_ID)) {

            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    order = extractOrderFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_ORDERS)) {

            while (rs.next()) {
                orderList.add(extractOrderFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orderList = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ORDERS_BY_USER_ID)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orderList.add(extractOrderFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    @Override
    public int updateOrder(Order order) {
        int status = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_ORDER)) {

            ps.setString(1, order.getStatus());
            ps.setString(2, order.getPaymentMode());
            ps.setInt(3, order.getOrderId());

            status = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public int deleteOrder(int orderId) {
        int status = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_ORDER)) {

            ps.setInt(1, orderId);
            status = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    private Order extractOrderFromResultSet(ResultSet rs) throws SQLException {
        return new Order(
            rs.getInt("orderId"),
            rs.getInt("restaurantId"),
            rs.getInt("userId"),
            rs.getTimestamp("orderDate"),
            rs.getDouble("totalAmount"),
            rs.getString("status"),
            rs.getString("paymentMode")
        );
    }
}