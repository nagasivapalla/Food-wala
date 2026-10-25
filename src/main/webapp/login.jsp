<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Foodwala | Login</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/navbar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/auth.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body>

    <jsp:include page="includes/navbar.jsp" />

    <div class="main-content auth-wrapper">
        <div class="neo-card auth-card">
            <span class="auth-badge">Foodwala Express</span>
            <h1 class="auth-title">Welcome Back</h1>
            <p class="auth-subtitle">Login to order your favorite cravings</p>

            <% if ("invalid".equals(request.getParameter("error"))) { %>
                <div class="auth-alert-error">Invalid Email or Password!</div>
            <% } else if ("registered".equals(request.getParameter("status"))) { %>
                <div class="auth-alert-success">Account created! Please sign in.</div>
            <% } else if ("loggedout".equals(request.getParameter("status"))) { %>
                <div class="auth-alert-success">Logged out successfully!</div>
            <% } %>

            <form class="auth-form" action="<%= request.getContextPath() %>/login" method="post">
                <div class="field-group">
                    <label for="email">Email Address</label>
                    <input class="neo-input" type="email" id="email" name="email" placeholder="name@domain.com" required>
                </div>

                <div class="field-group">
                    <label for="password">Password</label>
                    <input class="neo-input" type="password" id="password" name="password" placeholder="••••••••" required>
                </div>

                <button type="submit" class="neo-btn auth-btn-full">Sign In</button>
            </form>

            <div class="auth-footer">
                Don't have an account? <a href="<%= request.getContextPath() %>/signup.jsp">Create Account</a>
            </div>
        </div>
    </div>

    <jsp:include page="includes/footer.jsp" />

</body>
</html>