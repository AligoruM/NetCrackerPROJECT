package com.sample;

import com.sample.model.Type;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SelectItemsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemType = req.getParameter("Type");

        ItemService itemService = new ItemService();
        Type l = Type.valueOf(itemType);
        log(itemType);
        List itemsByType = itemService.getAvailableItems(l);

        req.setAttribute("items", itemsByType);
        RequestDispatcher view = req.getRequestDispatcher("result.jsp");
        view.forward(req, resp);
    }
}
