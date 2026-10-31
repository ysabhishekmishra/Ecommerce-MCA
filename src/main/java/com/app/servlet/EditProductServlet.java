package com.app.servlet;

import com.app.dao.ProductDao;
import com.app.model.Product;
import com.app.util.FormUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Controller: product edit.
 *   GET  /editProduct?id=3 -> us product ki details form me bharke editProduct.jsp dikhao
 *   POST /editProduct      -> product DB me update karo, phir /products
 */
@WebServlet("/editProduct")
public class EditProductServlet extends HttpServlet {

    private static final String EDIT_PAGE = "/WEB-INF/views/editProduct.jsp";
    private final ProductDao productDao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = parseId(req.getParameter("id"));
        Product p;
        try {
            p = (id == null) ? null : productDao.getById(id);
        } catch (SQLException e) {
            throw new ServletException("Product laate time database error", e);
        }

        if (p == null) {
            resp.sendRedirect(req.getContextPath() + "/products?msg=notfound");
            return;
        }

        // Form ke liye values (JSP me ${formName} wagairah)
        req.setAttribute("formId", p.getId());
        req.setAttribute("formName", p.getName());
        req.setAttribute("formPrice", BigDecimal.valueOf(p.getPrice()).toPlainString());
        req.setAttribute("formQuantity", p.getQuantity());
        req.getRequestDispatcher(EDIT_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Integer id = parseId(req.getParameter("id"));
        String name = req.getParameter("name");
        String priceText = req.getParameter("price");
        String quantityText = req.getParameter("quantity");

        if (id == null) {
            resp.sendRedirect(req.getContextPath() + "/products?msg=notfound");
            return;
        }

        String error = FormUtil.validateProduct(name, priceText, quantityText);
        if (error != null) {
            // Galat input: edit page par wapas, jo user ne likha wahi form me rahega
            req.setAttribute("error", error);
            req.setAttribute("formId", id);
            req.setAttribute("formName", name);
            req.setAttribute("formPrice", priceText);
            req.setAttribute("formQuantity", quantityText);
            req.getRequestDispatcher(EDIT_PAGE).forward(req, resp);
            return;
        }

        boolean updated;
        try {
            updated = productDao.update(id, name.trim(),
                    Double.parseDouble(priceText.trim()), Integer.parseInt(quantityText.trim()));
        } catch (SQLException e) {
            throw new ServletException("Product update karte time database error", e);
        }

        resp.sendRedirect(req.getContextPath() + "/products?msg=" + (updated ? "updated" : "notfound"));
    }

    private Integer parseId(String text) {
        try {
            return Integer.valueOf(text.trim());
        } catch (NullPointerException | NumberFormatException e) {
            return null;
        }
    }
}
