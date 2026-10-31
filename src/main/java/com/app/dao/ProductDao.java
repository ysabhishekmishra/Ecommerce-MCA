package com.app.dao;

import com.app.model.Product;
import com.app.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** products table ka saara JDBC code: Create, Read, Update, Delete (CRUD). */
public class ProductDao {

    /** Saare products (id ke order me). */
    public List<Product> getAll() throws SQLException {
        String sql = "SELECT id, name, price, quantity FROM products ORDER BY id";
        List<Product> list = new ArrayList<>();

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {           // har row ke liye ek Product object
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    /** Ek product id se. Nahi mila to null. */
    public Product getById(int id) throws SQLException {
        String sql = "SELECT id, name, price, quantity FROM products WHERE id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public void add(String name, double price, int quantity) throws SQLException {
        String sql = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, quantity);
            ps.executeUpdate();
        }
    }

    /** true = update hua, false = us id ka product hai hi nahi. */
    public boolean update(int id, String name, double price, int quantity) throws SQLException {
        String sql = "UPDATE products SET name = ?, price = ?, quantity = ? WHERE id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, quantity);
            ps.setInt(4, id);
            return ps.executeUpdate() > 0;   // kitni rows badli
        }
    }

    /** true = delete hua, false = us id ka product hai hi nahi. */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /** ResultSet ki current row ko Product object me badalta hai. */
    private Product mapRow(ResultSet rs) throws SQLException {
        return new Product(rs.getInt("id"),
                           rs.getString("name"),
                           rs.getDouble("price"),
                           rs.getInt("quantity"));
    }
}
