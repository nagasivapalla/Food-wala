package com.tap.util;

import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {
        Connection connection = DBConnection.getConnection();

        if (connection != null) {
            System.out.println("Connection established successfully to 'foodwala' database!");
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to establish connection. Check console for errors.");
        }
    }
}