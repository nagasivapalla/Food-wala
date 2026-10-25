<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.User" %>
<%
    User user = (User) session.getAttribute("loggedInUser");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Foodwala | My Profile</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/navbar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/profile.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body>

    <jsp:include page="includes/navbar.jsp" />

    <main class="main-content profile-container">
        <div class="neo-card profile-card">
            <div class="profile-header-neo">
                <div class="neo-avatar">
                    <%= (user.getName() != null && !user.getName().isEmpty()) ? user.getName().substring(0, 1) : "U" %>
                </div>
                <div>
                    <h1 style="font-size: 24px; font-weight: 800; color: var(--text-primary);"><%= user.getName() %></h1>
                    <span style="font-size: 13px; color: var(--accent-amber); font-weight: 700;">
                        <%= user.getRole() != null ? user.getRole() : "Customer" %>
                    </span>
                </div>
            </div>

            <div class="profile-grid-neo">
                <div class="profile-cell">
                    <span>Username</span>
                    <strong>@<%= user.getUsername() %></strong>
                </div>
                <div class="profile-cell">
                    <span>Email</span>
                    <strong><%= user.getEmail() %></strong>
                </div>
                <div class="profile-cell">
                    <span>Phone Number</span>
                    <strong><%= user.getPhoneNumber() != null ? user.getPhoneNumber() : "Not provided" %></strong>
                </div>
                <div class="profile-cell">
                    <span>Account ID</span>
                    <strong>#<%= user.getUserId() %></strong>
                </div>
                <div class="profile-cell" style="grid-column: 1 / -1;">
                    <span>Delivery Address</span>
                    <strong><%= user.getAddress() != null ? user.getAddress() : "No address specified" %></strong>
                </div>
            </div>

            <div class="profile-actions">
                <a href="<%= request.getContextPath() %>/restaurant" class="neo-btn" style="flex: 1;">Order Food</a>
                <a href="<%= request.getContextPath() %>/logout" class="neo-btn" style="flex: 1; background: #333742; box-shadow: var(--neo-raised);">Logout</a>
            </div>
        </div>
    </main>

    <jsp:include page="includes/footer.jsp" />

</body>
</html>