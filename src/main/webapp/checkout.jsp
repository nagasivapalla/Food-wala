<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.User, com.tap.model.Cart, com.tap.model.CartItem" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
        return;
    }

    double subtotal = cart.getTotalAmount();
    double deliveryFee = 0.0;
    double grandTotal = subtotal + deliveryFee;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Foodwala | Checkout</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/navbar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cart.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">
    <style>
        .checkout-grid {
            display: grid;
            grid-template-columns: 1.4fr 1fr;
            gap: 28px;
            max-width: 1080px;
            margin: 30px auto;
            padding: 0 20px;
        }
        @media (max-width: 768px) {
            .checkout-grid {
                grid-template-columns: 1fr;
            }
        }
        .payment-options {
            display: flex;
            flex-direction: column;
            gap: 14px;
            margin-top: 15px;
        }
        .payment-label {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 14px 18px;
            border-radius: 12px;
            background: var(--bg-main, #24272c);
            box-shadow: 4px 4px 10px #1a1c20, -4px -4px 10px #2e3238;
            cursor: pointer;
            transition: all 0.2s ease;
            color: var(--text-primary, #ffffff);
            font-size: 15px;
        }
        .payment-label:hover {
            box-shadow: inset 2px 2px 5px #1a1c20, inset -2px -2px 5px #2e3238;
        }
        .payment-label input[type="radio"] {
            accent-color: #ff5722;
            transform: scale(1.2);
            cursor: pointer;
        }
        .checkout-item-preview {
            display: flex;
            align-items: center;
            gap: 14px;
            margin-bottom: 12px;
            padding-bottom: 12px;
            border-bottom: 1px solid rgba(255, 255, 255, 0.05);
        }
        .checkout-thumb {
            width: 50px;
            height: 50px;
            border-radius: 8px;
            object-fit: cover;
        }
    </style>
</head>
<body>

    <jsp:include page="/includes/navbar.jsp" />

    <main class="main-content">
        <form action="<%= request.getContextPath() %>/checkout" method="post" class="checkout-grid">
            
            <!-- Left Column: Delivery & Payment Options -->
            <div style="display: flex; flex-direction: column; gap: 24px;">
                
                <!-- Delivery Details -->
                <div class="neo-card" style="padding: 24px;">
                    <h2 style="font-size: 18px; margin-bottom: 16px;">📍 Delivery Address</h2>
                    <p style="color: var(--text-muted, #9ba1a6); font-size: 14px; margin-bottom: 8px;">Delivering to:</p>
                    <input type="text" name="deliveryAddress" class="neo-input" 
                           value="<%= (loggedInUser.getAddress() != null) ? loggedInUser.getAddress() : "Flat 4B, Green Glen Layout, Bangalore" %>" 
                           style="width: 100%; padding: 12px; border-radius: 10px;" required>
                </div>

                <!-- Payment Selection -->
                <div class="neo-card" style="padding: 24px;">
                    <h2 style="font-size: 18px; margin-bottom: 8px;">💳 Select Payment Method</h2>
                    <p style="color: var(--text-muted, #9ba1a6); font-size: 13px; margin-bottom: 16px;">Choose how you want to pay</p>
                    
                    <div class="payment-options">
                        <label class="payment-label">
                            <input type="radio" name="paymentMode" value="Cash On Delivery" checked>
                            <span>💵 <strong>Cash on Delivery (COD)</strong></span>
                        </label>

                        <label class="payment-label">
                            <input type="radio" name="paymentMode" value="UPI">
                            <span>📱 <strong>UPI (GPay / PhonePe / Paytm)</strong></span>
                        </label>

                        <label class="payment-label">
                            <input type="radio" name="paymentMode" value="Credit/Debit Card">
                            <span>💳 <strong>Credit / Debit Card</strong></span>
                        </label>

                        <label class="payment-label">
                            <input type="radio" name="paymentMode" value="Net Banking">
                            <span>🏛 <strong>Net Banking</strong></span>
                        </label>
                    </div>
                </div>
            </div>

            <!-- Right Column: Order Summary -->
            <div class="neo-card" style="padding: 24px; height: fit-content;">
                <h2 style="font-size: 18px; margin-bottom: 16px;">Order Summary</h2>
                
                <!-- Items list -->
                <div style="margin-bottom: 16px;">
                    <% for (CartItem item : cart.getItems().values()) { %>
                        <div class="checkout-item-preview">
                            <img src="<%= (item.getImagePath() != null && !item.getImagePath().isEmpty()) ? item.getImagePath() : "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=100" %>" 
                                 class="checkout-thumb" alt="<%= item.getName() %>">
                            <div style="flex: 1;">
                                <div style="font-size: 14px; font-weight: 600;"><%= item.getName() %></div>
                                <div style="font-size: 12px; color: var(--text-muted, #9ba1a6);">₹<%= item.getPrice() %> × <%= item.getQuantity() %></div>
                            </div>
                            <div style="font-weight: bold; font-size: 14px;">₹<%= item.getTotalPrice() %></div>
                        </div>
                    <% } %>
                </div>

                <div style="display: flex; justify-content: space-between; margin-bottom: 8px; font-size: 14px; color: var(--text-muted, #9ba1a6);">
                    <span>Total Quantity</span>
                    <span><%= cart.getTotalQuantity() %> items</span>
                </div>
                <div style="display: flex; justify-content: space-between; margin-bottom: 8px; font-size: 14px; color: var(--text-muted, #9ba1a6);">
                    <span>Subtotal</span>
                    <span>₹<%= subtotal %></span>
                </div>
                <div style="display: flex; justify-content: space-between; margin-bottom: 16px; font-size: 14px; color: #4ade80;">
                    <span>Delivery Fee</span>
                    <span>FREE</span>
                </div>
                <hr style="border: 0; border-top: 1px solid rgba(255,255,255,0.08); margin-bottom: 16px;">
                <div style="display: flex; justify-content: space-between; margin-bottom: 24px; font-size: 18px; font-weight: bold;">
                    <span>Grand Total</span>
                    <span style="color: #ff8c00;">₹<%= grandTotal %></span>
                </div>

                <button type="submit" class="neo-btn" style="width: 100%; padding: 14px; font-size: 15px; font-weight: bold; background: linear-gradient(135deg, #ff7a00, #ff5000); color: #fff; border: none; border-radius: 12px; cursor: pointer;">
                    Confirm Order →
                </button>
            </div>

        </form>
    </main>

    <jsp:include page="/includes/footer.jsp" />

</body>
</html>