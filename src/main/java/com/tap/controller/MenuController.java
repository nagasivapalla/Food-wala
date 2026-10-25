package com.tap.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tap.dao.MenuDao;
import com.tap.daoimpl.MenuDaoImpl;
import com.tap.model.Menu;

@WebServlet("/menu")
public class MenuController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Retrieve the restaurantId parameter from the request
        String restaurantIdParam = req.getParameter("restaurantId");

        if (restaurantIdParam != null && !restaurantIdParam.trim().isEmpty()) {
            int restaurantId = Integer.parseInt(restaurantIdParam);

            // 2. Fetch the list of menu items from the DAO layer
            MenuDao menuDao = new MenuDaoImpl();
            List<Menu> menuList = menuDao.getMenusByRestaurantId(restaurantId);

            // 3. Attach the list to the request scope
            req.setAttribute("menuList", menuList);
        }

        // 4. Forward the request to menu.jsp
        RequestDispatcher dispatcher = req.getRequestDispatcher("menu.jsp");
        dispatcher.forward(req, resp);
    }
}