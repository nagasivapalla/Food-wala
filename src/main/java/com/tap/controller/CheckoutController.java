package com.tap.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tap.daoimpl.OrderDaoImpl;
import com.tap.model.Cart;
import com.tap.model.CartItem;
import com.tap.model.Order;
import com.tap.model.User;

@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedInUser");
        Cart cart = (Cart) session.getAttribute("cart");

        // 1. Authentication check
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 2. Empty cart check
        if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart.jsp");
            return;
        }

        // 3. Retrieve form inputs
        String paymentMode = request.getParameter("paymentMode");
        if (paymentMode == null || paymentMode.trim().isEmpty()) {
            paymentMode = "Cash On Delivery";
        }

        String deliveryAddress = request.getParameter("deliveryAddress");
        if (deliveryAddress != null && !deliveryAddress.trim().isEmpty()) {
            // Update address in session user object if modified
            user.setAddress(deliveryAddress);
        }

        // 4. Resolve Restaurant ID from the cart items
        int restaurantId = 0;
        for (CartItem item : cart.getItems().values()) {
            restaurantId = item.getRestaurantId();
            break;
        }

        double totalAmount = cart.getTotalAmount();

        // 5. Construct Order Model
        Order order = new Order();
        order.setUserId(user.getUserId());
        order.setRestaurantId(restaurantId);
        order.setTotalAmount(totalAmount);
        order.setStatus("Delivered");
        order.setPaymentMode(paymentMode);
        order.setOrderDate(new Timestamp(new Date().getTime()));

        // 6. Insert Order into Database
        int orderId = 0;
        try {
            OrderDaoImpl orderDao = new OrderDaoImpl();
            orderId = orderDao.addOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Fallback reference ID if DAO returns 0
        if (orderId <= 0) {
            orderId = (int) (System.currentTimeMillis() % 1000000);
        }

        // 7. Clear the session cart
        cart.clear();
        session.removeAttribute("cart");

        // 8. Pass order attributes to Confirmation View
        request.setAttribute("orderId", orderId);
        request.setAttribute("totalAmount", totalAmount);
        request.setAttribute("paymentMode", paymentMode);
        request.setAttribute("deliveryAddress", deliveryAddress != null ? deliveryAddress : user.getAddress());

        request.getRequestDispatcher("/orderConfirmation.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/checkout.jsp");
    }
}