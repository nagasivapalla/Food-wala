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

@WebServlet({"/signup", "/register"})
public class SignupController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Retrieve submitted registration form parameters
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");

        // Handle both "phone" and "phoneNumber" input names
        String phoneNumber = request.getParameter("phoneNumber");
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            phoneNumber = request.getParameter("phone");
        }

        // Encrypt the password using BCrypt
        String en_password = BCrypt.hashpw(password, BCrypt.gensalt());

        // Construct User entity with the encrypted password
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(en_password);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);
        user.setRole("Customer");

        // Insert into database
        UserDao userDao = new UserDaoImpl();
        int status = userDao.addUser(user);

        if (status > 0) {
            // Fetch registered record
            User registeredUser = userDao.getUserByEmail(email);
            
            // Set user in session
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", (registeredUser != null) ? registeredUser : user);

            // Redirect directly to restaurants page
            response.sendRedirect(request.getContextPath() + "/restaurant");
        } else {
            request.setAttribute("errorMsg", "Registration failed. Email or Username may already be in use.");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/signup.jsp");
    }
}