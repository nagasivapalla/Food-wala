package com.tap.dao;

import java.util.List;
import com.tap.model.Order;

public interface OrderDao {

    int addOrder(Order order);

    Order getOrder(int orderId);

    List<Order> getAllOrders();

    List<Order> getOrdersByUserId(int userId);

    int updateOrder(Order order);

    int deleteOrder(int orderId);
}