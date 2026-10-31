package com.app.servlet;

import com.app.dao.ProductDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/** POST /deleteProduct (id) -> product DB se hatao, phir /products. Delete hamesha POST se (link se nahi). */
@WebServlet("/deleteProduct")
public class DeleteProductServlet extends HttpServlet {

    private final ProductDao productDao = new ProductDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg;
        try {
            int id = Integer.parseInt(req.getParameter("id").trim());
            msg = productDao.delete(id) ? "deleted" : "notfound";
        } catch (NullPointerException | NumberFormatException e) {
            msg = "notfound";
        } catch (SQLException e) {
            throw new ServletException("Product delete karte time database error", e);
        }

        resp.sendRedirect(req.getContextPath() + "/products?msg=" + msg);
    }
}
