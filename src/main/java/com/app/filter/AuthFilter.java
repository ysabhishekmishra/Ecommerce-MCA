package com.app.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Sirf logged-in user in URLs ko dekh sakta hai.
 * Session me "username" nahi mila to login page par bhej dete hain.
 */
@WebFilter(urlPatterns = { "/products", "/editProduct", "/deleteProduct", "/logout" })
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false); // naya session mat banao
        boolean loggedIn = session != null && session.getAttribute("username") != null;

        if (loggedIn) {
            // Logout ke baad back button dabane par purana page cache se na dikhe
            resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login?error=login_required");
        }
    }
}
