<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Foodwala | Register</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/navbar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/auth.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body>

    <jsp:include page="includes/navbar.jsp" />

    <div class="main-content auth-wrapper">
        <div class="neo-card auth-card" style="max-width: 480px;">
            <span class="auth-badge">Join the Food Revolution</span>
            <h1 class="auth-title">Create Account</h1>
            <p class="auth-subtitle">Get fresh dishes delivered directly to your door</p>

            <% if (request.getParameter("error") != null) { %>
                <div class="auth-alert-error">Registration failed. Please verify fields and try again.</div>
            <% } %>

            <form class="auth-form" action="<%= request.getContextPath() %>/signup" method="post">
                <div class="field-group">
                    <label for="name">Full Name</label>
                    <input class="neo-input" type="text" id="name" name="name" placeholder="John Doe" required>
                </div>

                <div class="field-group">
                    <label for="username">Username</label>
                    <input class="neo-input" type="text" id="username" name="username" placeholder="johndoe22" required>
                </div>

                <div class="field-group">
                    <label for="email">Email Address</label>
                    <input class="neo-input" type="email" id="email" name="email" placeholder="john@domain.com" required>
                </div>

                <div class="field-group">
                    <label for="phone">Phone Number</label>
                    <input class="neo-input" type="tel" id="phone" name="phoneNumber" placeholder="9876543210" required>
                </div>

                <div class="field-group">
                    <label for="password">Password</label>
                    <input class="neo-input" type="password" id="password" name="password" placeholder="Create strong password" required>
                </div>

                <div class="field-group">
                    <label for="address">Delivery Address</label>
                    <textarea class="neo-input" id="address" name="address" rows="2" style="resize: vertical; height: auto;" placeholder="Street, Flat No., City" required></textarea>
                </div>

                <button type="submit" class="neo-btn auth-btn-full">Sign Up</button>
            </form>

            <div class="auth-footer">
                Already registered? <a href="<%= request.getContextPath() %>/login.jsp">Sign In</a>
            </div>
        </div>
    </div>

    <jsp:include page="includes/footer.jsp" />

</body>
</html>