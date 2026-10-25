<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.tap.model.Order, com.tap.model.User" %>
<%
    User user = (User) session.getAttribute("loggedInUser");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    List<Order> orderList = (List<Order>) request.getAttribute("orderList");
    if (orderList == null) {
        response.sendRedirect(request.getContextPath() + "/orders");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Foodwala | My Orders</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/navbar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/orders.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body>

    <jsp:include page="/includes/navbar.jsp" />

    <main class="main-content orders-container">
        <h1 class="orders-heading">Your Past Orders</h1>

        <% if (orderList != null && !orderList.isEmpty()) { %>
            <% for (Order order : orderList) { %>
                <div class="neo-card order-history-card">
                    <div class="history-card-top">
                        <h3>Order #<%= order.getOrderId() %></h3>
                        <span class="status-badge"><%= order.getStatus() != null ? order.getStatus() : "Placed" %></span>
                    </div>

                    <div class="history-details-grid">
                        <div class="history-data-cell">
                            <span>Placed On</span>
                            <strong><%= order.getOrderDate() %></strong>
                        </div>
                        <div class="history-data-cell">
                            <span>Payment Method</span>
                            <strong><%= order.getPaymentMode() != null ? order.getPaymentMode() : "COD" %></strong>
                        </div>
                        <div class="history-data-cell">
                            <span>Total Paid</span>
                            <strong style="color: var(--accent-amber);">₹<%= order.getTotalAmount() %></strong>
                        </div>
                    </div>
                </div>
            <% } %>
        <% } else { %>
            <div class="neo-card" style="padding: 60px 20px; text-align: center;">
                <p style="color: var(--text-muted); font-size: 16px; margin-bottom: 20px;">You haven't placed any orders yet.</p>
                <a href="<%= request.getContextPath() %>/restaurant" class="neo-btn">Find Good Food</a>
            </div>
        <% } %>
    </main>

    <jsp:include page="/includes/footer.jsp" />

</body>
</html>