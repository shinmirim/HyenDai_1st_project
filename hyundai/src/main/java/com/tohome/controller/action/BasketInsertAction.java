package com.tohome.controller.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tohome.dao.*;
import com.tohome.dto.MemberDTO;
import com.tohome.dto.ProductDTO;

public class BasketInsertAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        String url = "TohomeServlet?command=product_detail&prod_no=";
        
        HttpSession session =request.getSession();
        
        // 제품아이디랑 수량을 받아서 장바구니 detail table에 저장해야해
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		int prodQuant = Integer.parseInt(request.getParameter("prodQuant"));
		
		System.out.println(session.getAttribute("UserNo"));
		System.out.println(userNo+" "+prodNo+" "+prodQuant);
		url += prodNo;

		//session.getAttribute("UserNo");

		BasketDAO basketDAO = BasketDAO.getInstance();
        int result = basketDAO.InsertBasket(userNo, prodNo, prodQuant);
        basketDAO.close();
        
        request.setAttribute("basketInsertYN", result);
        session.setAttribute("basketCount", result);
//        request.setAttribute("productDTO", productDTO);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
	}

}
