package com.app.servlet;

import com.app.dao.ProductDao;
import com.app.util.FormUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller: products page (ek hi page par list bhi aur add form bhi).
 *   GET  /products -> DB se saare products laao aur products.jsp dikhao
 *   POST /products -> naya product DB me add karo, phir wapas /products
 */
@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private static final String PRODUCTS_PAGE = "/WEB-INF/views/products.jsp";
    private final ProductDao productDao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showPage(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String priceText = req.getParameter("price");
        String quantityText = req.getParameter("quantity");

        String error = FormUtil.validateProduct(name, priceText, quantityText);

        if (error != null) {
            // Galat input: list ke saath wahi page, error aur purani values ke saath
            req.setAttribute("error", error);
            req.setAttribute("enteredName", name);
            req.setAttribute("enteredPrice", priceText);
            req.setAttribute("enteredQuantity", quantityText);
            showPage(req, resp);
            return;
        }

        try {
            productDao.add(name.trim(), Double.parseDouble(priceText.trim()), Integer.parseInt(quantityText.trim()));
        } catch (SQLException e) {
            throw new ServletException("Product add karte time database error", e);
        }

        // Redirect, taaki refresh (F5) karne par form dobara submit na ho
        resp.sendRedirect(req.getContextPath() + "/products?msg=added");
    }

    /** DB se list laao, request me rakho (JSP me ${products}) aur JSP par forward karo. */
    private void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("products", productDao.getAll());
        } catch (SQLException e) {
            throw new ServletException("Products laate time database error", e);
        }
        req.getRequestDispatcher(PRODUCTS_PAGE).forward(req, resp);
    }
}
