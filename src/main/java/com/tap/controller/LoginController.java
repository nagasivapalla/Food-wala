package com.tap.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.tap.dao.UserDao;
import com.tap.daoimpl.UserDaoImpl;
import com.tap.model.User;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserDao userDao = new UserDaoImpl();
        User user = userDao.getUserByEmail(email);

        // Line 44 from your screen:
        String en_password = BCrypt.hashpw(password, BCrypt.gensalt());

        // Password verification check
        if (user != null && (user.getPassword().equals(password) || BCrypt.checkpw(password, user.getPassword()))) {
            HttpSession session = req.getSession();
            session.setAttribute("loggedInUser", user);

            resp.sendRedirect(req.getContextPath() + "/restaurant");
        } else {
            req.setAttribute("loginError", "Invalid email or password");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }
}