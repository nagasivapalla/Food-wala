package com.tap.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.OrderItemDao;
import com.tap.model.OrderItem;
import com.tap.util.DBConnection;

public class OrderItemDaoImpl implements OrderItemDao {

    private static final String INSERT_ORDER_ITEM = 
        "INSERT INTO `orderitem` (`orderId`, `menuId`, `quantity`, `totalAmount`) VALUES (?, ?, ?, ?)";

    private static final String SELECT_ORDER_ITEM_BY_ID = 
        "SELECT * FROM `orderitem` WHERE `orderItemId` = ?";

    private static final String SELECT_ALL_ORDER_ITEMS = 
        "SELECT * FROM `orderitem`";

    private static final String SELECT_ORDER_ITEMS_BY_ORDER_ID = 
        "SELECT * FROM `orderitem` WHERE `orderId` = ?";

    private static final String UPDATE_ORDER_ITEM = 
        "UPDATE `orderitem` SET `orderId` = ?, `menuId` = ?, `quantity` = ?, `totalAmount` = ? WHERE `orderItemId` = ?";

    private static final String DELETE_ORDER_ITEM = 
        "DELETE FROM `orderitem` WHERE `orderItemId` = ?";

    @Override
    public int addOrderItem(OrderItem orderItem) {
        int status = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_ORDER_ITEM)) {

            ps.setInt(1, orderItem.getOrderId());
            ps.setInt(2, orderItem.getMenuId());
            ps.setInt(3, orderItem.getQuantity());
            ps.setDouble(4, orderItem.getTotalAmount());

            status = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public OrderItem getOrderItem(int orderItemId) {
        OrderItem item = null;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ORDER_ITEM_BY_ID)) {

            ps.setInt(1, orderItemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    item = extractOrderItemFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        List<OrderItem> itemList = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_ORDER_ITEMS)) {

            while (rs.next()) {
                itemList.add(extractOrderItemFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> itemList = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ORDER_ITEMS_BY_ORDER_ID)) {

            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    itemList.add(extractOrderItemFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    @Override
    public int updateOrderItem(OrderItem orderItem) {
        int status = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_ORDER_ITEM)) {

            ps.setInt(1, orderItem.getOrderId());
            ps.setInt(2, orderItem.getMenuId());
            ps.setInt(3, orderItem.getQuantity());
            ps.setDouble(4, orderItem.getTotalAmount());
            ps.setInt(5, orderItem.getOrderItemId());

            status = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public int deleteOrderItem(int orderItemId) {
        int status = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_ORDER_ITEM)) {

            ps.setInt(1, orderItemId);
            status = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    private OrderItem extractOrderItemFromResultSet(ResultSet rs) throws SQLException {
        return new OrderItem(
            rs.getInt("orderItemId"),
            rs.getInt("orderId"),
            rs.getInt("menuId"),
            rs.getInt("quantity"),
            rs.getDouble("totalAmount")
        );
    }
}