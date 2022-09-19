package com.tohome.controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tohome.dao.ProductDAO;
import com.tohome.dao.ReviewDAO;
import com.tohome.dto.ProductDTO;
import com.tohome.dto.ReviewDTO;
//Written  by 미림
public class ProductDetailAction implements Action {

  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String url="product/product_details.jsp";
    
    int prod_no= Integer.parseInt(request.getParameter("prod_no").trim());
    
    
    ProductDAO productDAO=ProductDAO.getInstance();
    ProductDTO productDTO= productDAO.getProduct(prod_no);
    
    ReviewDAO reviewDAO=ReviewDAO.getInstance();
	ArrayList<ReviewDTO> productReviewList= reviewDAO.getProductReviewList(prod_no);
	
    
    request.setAttribute("productDTO", productDTO);
    request.setAttribute("reviewList", productReviewList);
    //request.setAttribute("memberDTO", memberDTO);
    
    RequestDispatcher dispatcher = request
        .getRequestDispatcher(url);
    dispatcher.forward(request, response);
  }
}
