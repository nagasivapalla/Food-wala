<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.User, com.tap.model.Cart" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    Cart navCart = (Cart) session.getAttribute("cart");
    int cartCount = 0;
    if (navCart != null && navCart.getItems() != null) {
        cartCount = navCart.getTotalQuantity();
    }
%>
<nav class="neo-navbar">
    <a href="<%= request.getContextPath() %>/restaurant" class="nav-brand">
        Foodwala 🍔
    </a>

    <div class="nav-links">
        <a href="<%= request.getContextPath() %>/restaurant" class="nav-item">Restaurants</a>
        <a href="<%= request.getContextPath() %>/cart.jsp" class="nav-item">
            Cart <%= (cartCount > 0) ? "(" + cartCount + ")" : "" %>
        </a>

        <% if (loggedInUser != null) { %>
            <a href="<%= request.getContextPath() %>/orders" class="nav-item">Orders</a>
            <a href="<%= request.getContextPath() %>/profile.jsp" class="nav-item">Profile</a>
            <span class="user-badge">Hi, <%= (loggedInUser.getUsername() != null) ? loggedInUser.getUsername() : (loggedInUser.getName() != null ? loggedInUser.getName() : "Foodie") %></span>
            <a href="<%= request.getContextPath() %>/logout" class="nav-item">Logout</a>
        <% } else { %>
            <a href="<%= request.getContextPath() %>/login.jsp" class="neo-btn">Login</a>
        <% } %>
    </div>
</nav>