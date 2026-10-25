package com.tap.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tap.model.Cart;
import com.tap.model.CartItem;

@WebServlet("/cart")
public class CartController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        String action = request.getParameter("action");
        String restaurantIdStr = request.getParameter("restaurantId");
        int restaurantId = (restaurantIdStr != null && !restaurantIdStr.isEmpty()) 
                           ? Integer.parseInt(restaurantIdStr) : 0;

        try {
            if ("add".equalsIgnoreCase(action)) {
                int itemId = Integer.parseInt(request.getParameter("itemId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                String name = request.getParameter("name");
                String priceStr = request.getParameter("price");
                String imagePath = request.getParameter("imagePath");

                double price = (priceStr != null && !priceStr.isEmpty()) ? Double.parseDouble(priceStr) : 0.0;

                CartItem cartItem = new CartItem(itemId, restaurantId, name, price, quantity, imagePath);
                cart.addItem(cartItem);

            } else if ("update".equalsIgnoreCase(action)) {
                int itemId = Integer.parseInt(request.getParameter("itemId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                if (quantity <= 0) {
                    cart.removeItem(itemId);
                } else {
                    cart.updateItem(itemId, quantity);
                }

            } else if ("delete".equalsIgnoreCase(action)) {
                int itemId = Integer.parseInt(request.getParameter("itemId"));
                cart.removeItem(itemId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // If modified from the menu view, redirect back to menu
        if (restaurantId > 0) {
            response.sendRedirect(request.getContextPath() + "/menu?restaurantId=" + restaurantId);
        } else {
            response.sendRedirect(request.getContextPath() + "/cart.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doPost(request, response);
    }
}