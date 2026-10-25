
package com.tap.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.MenuDao;
import com.tap.model.Menu;
import com.tap.util.DBConnection;

public class MenuDaoImpl implements MenuDao {

    private static final String INSERT_MENU = 
        "INSERT INTO `menu` (`restaurantId`, `itemName`, `description`, `price`, `isAvailable`, `ratings`, `imagePath`) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_MENU_BY_ID = 
        "SELECT * FROM `menu` WHERE `menuId` = ?";

    private static final String SELECT_ALL_MENUS = 
        "SELECT * FROM `menu`";

    private static final String SELECT_MENUS_BY_RESTAURANT_ID = 
        "SELECT * FROM `menu` WHERE `restaurantId` = ?";

    private static final String UPDATE_MENU = 
        "UPDATE `menu` SET `restaurantId` = ?, `itemName` = ?, `description` = ?, `price` = ?, `isAvailable` = ?, `ratings` = ?, `imagePath` = ? WHERE `menuId` = ?";

    private static final String DELETE_MENU = 
        "DELETE FROM `menu` WHERE `menuId` = ?";

    @Override
    public int addMenu(Menu menu) {
        int status = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_MENU)) {

            ps.setInt(1, menu.getRestaurantId());
            ps.setString(2, menu.getItemName());
            ps.setString(3, menu.getDescription());
            ps.setDouble(4, menu.getPrice());
            ps.setBoolean(5, menu.isAvailable());
            ps.setDouble(6, menu.getRatings());
            ps.setString(7, menu.getImagePath());

            status = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public Menu getMenu(int menuId) {
        Menu menu = null;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_MENU_BY_ID)) {

            ps.setInt(1, menuId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    menu = extractMenuFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menu;
    }

    @Override
    public List<Menu> getAllMenus() {
        List<Menu> menuList = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_MENUS)) {

            while (rs.next()) {
                menuList.add(extractMenuFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuList;
    }

    @Override
    public List<Menu> getMenusByRestaurantId(int restaurantId) {
        List<Menu> menuList = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_MENUS_BY_RESTAURANT_ID)) {

            ps.setInt(1, restaurantId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    menuList.add(extractMenuFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuList;
    }

    @Override
    public int updateMenu(Menu menu) {
        int status = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_MENU)) {

            ps.setInt(1, menu.getRestaurantId());
            ps.setString(2, menu.getItemName());
            ps.setString(3, menu.getDescription());
            ps.setDouble(4, menu.getPrice());
            ps.setBoolean(5, menu.isAvailable());
            ps.setDouble(6, menu.getRatings());
            ps.setString(7, menu.getImagePath());
            ps.setInt(8, menu.getMenuId());

            status = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public int deleteMenu(int menuId) {
        int status = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_MENU)) {

            ps.setInt(1, menuId);
            status = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    private Menu extractMenuFromResultSet(ResultSet rs) throws SQLException {
        return new Menu(
            rs.getInt("menuId"),
            rs.getInt("restaurantId"),
            rs.getString("itemName"),
            rs.getString("description"),
            rs.getDouble("price"),
            rs.getBoolean("isAvailable"),
            rs.getDouble("ratings"),
            rs.getString("imagePath")
        );
    }
}