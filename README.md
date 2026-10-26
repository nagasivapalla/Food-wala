# 🍔 Food Wala – Full-Stack Food Delivery Web Application

**Food Wala** is a full-stack Java web application built using the **MVC** and **DAO** design patterns. It allows users to browse local restaurants, explore categorized food menus, manage their shopping cart, and place food orders seamlessly.

---

## 🛠️ Tech Stack
* **Backend:** Java (JDK), JEE (Servlets & JSP), JDBC
* **Frontend:** HTML5, CSS3, JSP
* **Database:** MySQL
* **Web Server:** Apache Tomcat
* **IDE:** Eclipse Enterprise Java and Web Developers

---

## ✨ Key Features
* **User Authentication & Profile:** Secure user sign-up, login, password handling, and user profile management (`login.jsp`, `profile.jsp`).
* **Restaurant Discovery:** Browse restaurants with ratings, cuisine types, and delivery details (`restaurant.jsp`).
* **Dynamic Menu Catalog:** View restaurant-specific dishes, pricing, and descriptions (`menu.jsp`).
* **Interactive Shopping Cart:** Add food items, adjust item quantities, remove items, and calculate dynamic totals (`cart.jsp`).
* **Order Processing & History:** Smooth checkout workflow, order confirmation summary, and past order tracking (`checkout.jsp`, `orderConfirmation.jsp`, `orders.jsp`).

---

## 📂 Project Architecture

```text
Food-wala/
├── src/main/java/
│   ├── com.tap.controller/   # Servlets handling HTTP requests & routing
│   ├── com.tap.dao/          # Data Access Object (DAO) interfaces
│   ├── com.tap.daoimpl/      # JDBC implementation classes for CRUD operations
│   ├── com.tap.model/        # Java Bean / POJO entity classes
│   └── com.tap.util/         # Database connection & utility helpers
└── src/main/webapp/
    ├── css/                  # Custom stylesheets
    ├── includes/             # Reusable JSP components
    ├── WEB-INF/              # Deployment descriptor (web.xml) & libraries
    ├── index.html            # Application entry point
    ├── login.jsp             # User login & authentication view
    ├── restaurant.jsp        # Restaurant listing page
    ├── menu.jsp              # Restaurant menu items page
    ├── cart.jsp              # Shopping cart page
    ├── checkout.jsp          # Order checkout page
    ├── orderConfirmation.jsp # Order success screen
    ├── orders.jsp            # User order history
    └── profile.jsp           # User account profile
