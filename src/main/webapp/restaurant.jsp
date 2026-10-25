<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.tap.model.Restaurant" %>
<%
    List<Restaurant> restaurantList = (List<Restaurant>) request.getAttribute("restaurantList");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Foodwala | Restaurants</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/navbar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/restaurant.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body class="restaurant-page-body">

    <jsp:include page="/includes/navbar.jsp" />

    <main class="restaurant-board">
        <div class="restaurant-grid">
            <% if (restaurantList != null && !restaurantList.isEmpty()) { %>
                <% for (Restaurant r : restaurantList) { %>
                    <div class="restaurant-card">
                        <!-- Top Picture with Fixed Aspect Ratio -->
                        <div class="restaurant-img-box">
                            <img src="<%= (r.getImagePath() != null && !r.getImagePath().isEmpty()) ? r.getImagePath() : "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=500" %>" 
                                 alt="<%= r.getName() %>"
                                 onerror="this.src='https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=500';">
                            <span class="delivery-time-pill">&#9201; <%= r.getDeliveryTime() %> mins</span>
                        </div>

                        <!-- Card Body (Fixed Heights for Uniform Rows) -->
                        <div class="restaurant-body">
                            <h2 class="restaurant-name" title="<%= r.getName() %>"><%= r.getName() %></h2>
                            <p class="restaurant-cuisine" title="<%= r.getCuisineType() %>"><%= r.getCuisineType() %></p>
                        </div>

                        <!-- Bottom Controls in a Single Horizontal Line -->
                        <div class="restaurant-bottom-bar">
                            <span class="rating-tag">&#9733; <%= r.getRating() %></span>
                            <a href="<%= request.getContextPath() %>/menu?restaurantId=<%= r.getRestaurantId() %>" class="view-menu-btn">View Menu &rarr;</a>
                        </div>
                    </div>
                <% } %>
            <% } else { %>
                <div class="no-restaurants">
                    <p>No restaurants available at the moment.</p>
                </div>
            <% } %>
        </div>
    </main>

    <jsp:include page="/includes/footer.jsp" />

</body>
</html>