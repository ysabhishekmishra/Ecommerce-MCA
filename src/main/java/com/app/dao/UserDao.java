package com.app.dao;

import com.app.model.User;
import com.app.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * DAO (Data Access Object): users table se baat karne wala saara JDBC code yahin hai.
 * Servlets me SQL nahi likhte, wo bas in methods ko call karte hain.
 */
public class UserDao {

    /** Username se user dhoondho. Nahi mila to null. */
    public User findByUsername(String username) throws SQLException {
        // ? ki jagah value PreparedStatement bharta hai (SQL injection se bachav)
        String sql = "SELECT username, email, password FROM users WHERE username = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getString("username"),
                                    rs.getString("email"),
                                    rs.getString("password"));
                }
            }
        }
        return null;
    }

    /** Naya user save karo. false = ye username pehle se hai. */
    public boolean save(User user) throws SQLException {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();
            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            // username column UNIQUE hai, duplicate par MySQL ye exception deta hai
            return false;
        }
    }
}
