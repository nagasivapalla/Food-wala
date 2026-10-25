<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map, com.tap.model.Cart, com.tap.model.CartItem" %>
<%
    Cart cart = (Cart) session.getAttribute("cart");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Foodwala | My Cart</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/navbar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cart.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body>

    <jsp:include page="includes/navbar.jsp" />

    <main class="main-content cart-container">
        <h1 class="cart-title">Your Food Cart</h1>

        <% if (cart != null && cart.getItems() != null && !cart.getItems().isEmpty()) { %>
            <div class="cart-grid">
                <!-- Items Column -->
                <div class="neo-card cart-items-card">
                    <%
                        Map<Integer, CartItem> items = cart.getItems();
                        int totalItems = 0;
                        for (CartItem item : items.values()) {
                            totalItems += item.getQuantity();
                            double subTotal = item.getPrice() * item.getQuantity();
                    %>
                        <div class="cart-item-row">
                            <div class="cart-item-info">
                                <img class="cart-item-img" 
                                     src="<%= (item.getImagePath() != null && !item.getImagePath().isEmpty()) ? item.getImagePath() : "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=500" %>" 
                                     alt="<%= item.getName() %>"
                                     onerror="this.src='https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=500';">
                                <div>
                                    <div class="cart-item-name"><%= item.getName() %></div>
                                    <div class="cart-item-price">₹<%= item.getPrice() %></div>
                                </div>
                            </div>

                            <div style="display: flex; align-items: center;">
                                <div class="qty-control">
                                    <form action="<%= request.getContextPath() %>/cart" method="post" style="display:inline;">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
                                        <input type="hidden" name="quantity" value="<%= item.getQuantity() - 1 %>">
                                        <button type="submit" class="qty-btn">−</button>
                                    </form>

                                    <span class="qty-value"><%= item.getQuantity() %></span>

                                    <form action="<%= request.getContextPath() %>/cart" method="post" style="display:inline;">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
                                        <input type="hidden" name="quantity" value="<%= item.getQuantity() + 1 %>">
                                        <button type="submit" class="qty-btn">+</button>
                                    </form>
                                </div>

                                <form action="<%= request.getContextPath() %>/cart" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
                                    <button type="submit" class="btn-remove">Remove</button>
                                </form>
                            </div>
                        </div>
                    <% } %>
                </div>

                <!-- Summary Column -->
                <div class="neo-card cart-summary-card">
                    <h2 class="summary-heading">Order Summary</h2>

                    <div class="summary-line">
                        <span>Total Items</span>
                        <span><%= totalItems %></span>
                    </div>
                    <div class="summary-line">
                        <span>Subtotal</span>
                        <span>₹<%= cart.getTotalAmount() %></span>
                    </div>
                    <div class="summary-line">
                        <span>Delivery Fee</span>
                        <span style="color: var(--accent-green); font-weight: 700;">FREE</span>
                    </div>

                    <div class="summary-divider"></div>

                    <div class="summary-total">
                        <span>Grand Total</span>
                        <span>₹<%= cart.getTotalAmount() %></span>
                    </div>

                    <a href="<%= request.getContextPath() %>/checkout.jsp" class="neo-btn btn-checkout-full">
                        Proceed to Checkout →
                    </a>
                </div>
            </div>
        <% } else { %>
            <div class="neo-card empty-cart-card">
                <p>Your cart is empty. Hungry for something delicious?</p>
                <a href="<%= request.getContextPath() %>/restaurant" class="neo-btn">Browse Restaurants</a>
            </div>
        <% } %>
    </main>

    <jsp:include page="includes/footer.jsp" />

</body>
</html>