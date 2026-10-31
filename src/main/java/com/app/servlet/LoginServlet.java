package com.app.servlet;

import com.app.dao.UserDao;
import com.app.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller: login.
 *   GET  /login -> login.jsp dikhao
 *   POST /login -> DB se user dhoondo, password match ho to session banao aur /products par bhejo
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String LOGIN_PAGE = "/WEB-INF/views/login.jsp";
    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Pehle se login hai to dobara login page dikhane ki zaroorat nahi
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }
        req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user;
        try {
            user = (username == null) ? null : userDao.findByUsername(username.trim());
        } catch (SQLException e) {
            throw new ServletException("Login ke time database error", e);
        }

        if (user == null || password == null || !user.getPassword().equals(password)) {
            // Galat hai: wapas login.jsp, error ke saath (form me username bhara rahega)
            req.setAttribute("error", "Username ya password galat hai");
            req.setAttribute("enteredUsername", username);
            req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
            return;
        }

        // Purana session (agar ho) khatam karo, naya banao (session fixation se bachav)
        HttpSession old = req.getSession(false);
        if (old != null) {
            old.invalidate();
        }
        HttpSession session = req.getSession(true); // yahin JSESSIONID cookie browser ko jaati hai
        session.setAttribute("username", user.getUsername());
        session.setMaxInactiveInterval(30 * 60); // 30 minute

        resp.sendRedirect(req.getContextPath() + "/products");
    }
}
