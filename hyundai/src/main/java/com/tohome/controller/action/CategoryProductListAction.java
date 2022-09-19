package com.tohome.controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tohome.dao.ProductDAO;
import com.tohome.dto.ProductDTO;

public class CategoryProductListAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url="product/category_page.jsp";
		
		
		
		ProductDAO productDAO=ProductDAO.getInstance();
		int cid = Integer.parseInt(request.getParameter("category_id"));
		int topnum = Integer.parseInt(request.getParameter("top_num"));
		int filter = Integer.parseInt(request.getParameter("filter"));
		ArrayList<ProductDTO> categoryProductList= productDAO.getCategoryProductList(cid, topnum, filter);
		
		request.setAttribute("categoryProductList", categoryProductList);
		request.setAttribute("categoryId", cid);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
