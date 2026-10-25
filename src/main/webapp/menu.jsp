<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map, com.tap.model.Menu, com.tap.model.Cart, com.tap.model.CartItem" %>
<%
    List<Menu> menuList = (List<Menu>) request.getAttribute("menuList");
    Cart cart = (Cart) session.getAttribute("cart");
    Map<Integer, CartItem> cartItems = (cart != null) ? cart.getItems() : null;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Foodwala | Menu</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/navbar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/menu.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cart.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body>

    <jsp:include page="/includes/navbar.jsp" />

    <main class="main-content menu-container">
        <div class="neo-card menu-header-card">
            <div>
                <a href="<%= request.getContextPath() %>/restaurant" class="back-btn">← Back to Restaurants</a>
                <h1 style="margin-top: 10px;">Select Dishes</h1>
            </div>
            <a href="<%= request.getContextPath() %>/cart.jsp" class="neo-btn">View Cart</a>
        </div>

        <section class="menu-grid">
            <% if (menuList != null && !menuList.isEmpty()) { %>
                <% for (Menu item : menuList) { 
                    int itemQty = 0;
                    if (cartItems != null && cartItems.containsKey(item.getMenuId())) {
                        itemQty = cartItems.get(item.getMenuId()).getQuantity();
                    }
                %>
                    <div class="neo-card dish-card">
                        <div class="dish-details">
                            <h2 class="dish-name"><%= item.getItemName() %></h2>
                            <p class="dish-desc"><%= (item.getDescription() != null && !item.getDescription().isEmpty()) ? item.getDescription() : "Freshly prepared with authentic ingredients." %></p>
                            <div class="dish-price">₹<%= item.getPrice() %></div>
                        </div>

                        <div class="dish-media">
                            <img class="dish-thumb" 
                                 src="<%= (item.getImagePath() != null && !item.getImagePath().isEmpty()) ? item.getImagePath() : "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=500" %>" 
                                 alt="<%= item.getItemName() %>"
                                 onerror="this.src='https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=500';">
                            
                            <% if (itemQty == 0) { %>
                                <!-- Initial Add Button -->
                                <form action="<%= request.getContextPath() %>/cart" method="post">
                                    <input type="hidden" name="action" value="add">
                                    <input type="hidden" name="itemId" value="<%= item.getMenuId() %>">
                                    <input type="hidden" name="restaurantId" value="<%= item.getRestaurantId() %>">
                                    <input type="hidden" name="name" value="<%= item.getItemName() %>">
                                    <input type="hidden" name="price" value="<%= item.getPrice() %>">
                                    <input type="hidden" name="imagePath" value="<%= (item.getImagePath() != null) ? item.getImagePath() : "" %>">
                                    <input type="hidden" name="quantity" value="1">
                                    <button type="submit" class="neo-btn" style="padding: 7px 18px; font-size: 13px;">+ Add</button>
                                </form>
                            <% } else { %>
                                <!-- Increase / Decrease Controls -->
                                <div class="qty-control" style="background: var(--bg-main);">
                                    <form action="<%= request.getContextPath() %>/cart" method="post" style="display:inline;">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="itemId" value="<%= item.getMenuId() %>">
                                        <input type="hidden" name="restaurantId" value="<%= item.getRestaurantId() %>">
                                        <input type="hidden" name="quantity" value="<%= itemQty - 1 %>">
                                        <button type="submit" class="qty-btn">−</button>
                                    </form>

                                    <span class="qty-value"><%= itemQty %></span>

                                    <form action="<%= request.getContextPath() %>/cart" method="post" style="display:inline;">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="itemId" value="<%= item.getMenuId() %>">
                                        <input type="hidden" name="restaurantId" value="<%= item.getRestaurantId() %>">
                                        <input type="hidden" name="quantity" value="<%= itemQty + 1 %>">
                                        <button type="submit" class="qty-btn">+</button>
                                    </form>
                                </div>
                            <% } %>
                        </div>
                    </div>
                <% } %>
            <% } else { %>
                <div class="neo-card" style="grid-column: 1 / -1; padding: 48px; text-align: center;">
                    <p style="color: var(--text-muted); font-size: 16px;">No menu items listed for this restaurant yet.</p>
                </div>
            <% } %>
        </section>
    </main>

    <jsp:include page="/includes/footer.jsp" />

</body>
</html>