package controller;


import dao.ProductDao;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/search")
public class ProductSearchServlet extends HttpServlet {
    private ProductDao productDao;

    @Override public void init() throws ServletException { productDao = new ProductDao(); }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String q = req.getParameter("q");
        System.out.println("[ProductSearchServlet] q=" + q); // 偵錯
        List<Product> results = productDao.searchByName(q);
        System.out.println("[ProductSearchServlet] size=" + results.size()); // 偵錯
        req.setAttribute("results", results);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
