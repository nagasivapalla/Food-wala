
package com.tap.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tap.daoimpl.RestaurantDaoImpl;
import com.tap.model.Restaurant;

@WebServlet("/restaurant")
public class RestaurantController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        try {
            // Fetch all restaurants from the database
            RestaurantDaoImpl restaurantDao = new RestaurantDaoImpl();
            List<Restaurant> restaurantList = restaurantDao.getAllRestaurants();

            // Set the list as a request attribute
            request.setAttribute("restaurantList", restaurantList);

            // Forward to restaurant.jsp
            request.getRequestDispatcher("/restaurant.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to fetch restaurants.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}