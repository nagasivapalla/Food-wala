<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Foodwala | Order Placed</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/navbar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/confirmation.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body>

    <jsp:include page="includes/navbar.jsp" />

    <main class="main-content confirm-wrapper">
        <div class="neo-card confirm-card">
            <div class="neo-icon-circle">✓</div>
            <h1 class="confirm-title">Order Confirmed!</h1>
            <p class="confirm-desc">Sit back and relax. Your hot meal is being prepared.</p>

            <div class="order-box">
                <div class="order-line">
                    <span>Order Reference</span>
                    <span class="order-highlight">#<%= request.getAttribute("orderId") %></span>
                </div>
                <div class="order-line">
                    <span>Payment Method</span>
                    <span>Cash On Delivery</span>
                </div>
                <div class="order-line">
                    <span>Total Amount</span>
                    <span class="order-highlight">₹<%= request.getAttribute("totalAmount") %></span>
                </div>
            </div>

            <a href="<%= request.getContextPath() %>/restaurant" class="neo-btn" style="width: 100%; padding: 14px;">
                Explore More Food
            </a>
        </div>
    </main>

    <jsp:include page="includes/footer.jsp" />

</body>
</html>