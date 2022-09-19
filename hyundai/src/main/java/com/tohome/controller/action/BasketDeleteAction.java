package com.tohome.controller.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tohome.dao.*;

public class BasketDeleteAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = "TohomeServlet?command=basket";
        HttpSession session =request.getSession();
        
        int userNo = (int)session.getAttribute("UserNo");
        // 장바구니에서 없앨 prod_no을 하나의 문자열로 가져옴
        String pNums = request.getParameter("pNums").trim();
        
        BasketDAO basketDAO = BasketDAO.getInstance();
        
        session.setAttribute("basketCount",basketDAO.basketDelete(userNo, pNums));
       
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}

}
