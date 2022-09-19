package com.tohome.controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tohome.dao.ProductDAO;
import com.tohome.dto.ProductDTO;

public class AllProductListAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url = "product/best.jsp";

		int filter = Integer.parseInt(request.getParameter("filter"));
		int topnum = Integer.parseInt(request.getParameter("top_num"));
		ProductDAO productDAO = ProductDAO.getInstance();
		ArrayList<ProductDTO> bestProductList = productDAO.getAllProductList(topnum, filter);

		request.setAttribute("bestProductList", bestProductList);
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
	
}

