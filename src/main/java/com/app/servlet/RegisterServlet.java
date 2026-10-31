package com.app.servlet;

import com.app.dao.UserDao;
import com.app.model.User;
import com.app.util.FormUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller: register.
 *   GET  /register -> register.jsp dikhao
 *   POST /register -> user ko database (users table) me save karo, phir /login par bhejo
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final String REGISTER_PAGE = "/WEB-INF/views/register.jsp";
    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        String error = null;
        if (FormUtil.isBlank(username) || FormUtil.isBlank(email) || FormUtil.isBlank(password)) {
            error = "Saari fields bharna zaroori hai";
        } else if (username.trim().length() > 50 || email.trim().length() > 100 || password.length() > 100) {
            error = "Username 50, email aur password 100 characters se zyada nahi ho sakte";
        } else {
            try {
                if (!userDao.save(new User(username.trim(), email.trim(), password))) {
                    error = "Ye username pehle se hai";
                }
            } catch (SQLException e) {
                throw new ServletException("Register ke time database error", e);
            }
        }

        if (error != null) {
            // Galat hai: wapas register.jsp, error ke saath (form me purani values bhari rahengi)
            req.setAttribute("error", error);
            req.setAttribute("enteredUsername", username);
            req.setAttribute("enteredEmail", email);
            req.getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
            return;
        }

        // Register ho gaya -> login page par bhejo
        resp.sendRedirect(req.getContextPath() + "/login?registered=1");
    }
}
