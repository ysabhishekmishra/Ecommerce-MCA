package com.app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBC connection banane ka kaam sirf yahin hota hai.
 * Database ka user/password badalna ho to sirf yahan badlo.
 */
public class DBUtil {

    // ====== APNE MySQL KE HISAAB SE BADLO ======
    private static final String URL =
            "jdbc:mysql://localhost:3306/prod?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    // ===========================================

    // Step 1 of JDBC: driver class load karo (class load hote hi wo khud ko register kar leti hai)
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(
                    "MySQL driver nahi mila. mysql-connector-j jar ko WEB-INF/lib me daalo.");
        }
    }

    // Step 2: connection kholo. Har jagah try-with-resources me use karo, taaki apne aap band ho jaye.
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
