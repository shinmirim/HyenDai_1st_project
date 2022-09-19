package com.tohome.controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tohome.dao.*;
import com.tohome.dto.BasketDTO;
public class BasketPageAction implements Action{
	//Written  by 미림
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "member/basket.jsp";
		
		//장바구니에 담긴놈 다 가져오기는데 user_no(basket_detail에 user_no로 인덱스 만들어놓음)로 가저옴
		//필요한 정보 user의 할인률, 제품들의 정보(img, 제품이름), 장바구니에 담긴 갯수
		HttpSession session =request.getSession();
		
		if (session.getAttribute("UserNo")==null) {
			url="member/login_form.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		}
		int userNo = (int)session.getAttribute("UserNo");
		BasketDAO basketDAO = BasketDAO.getInstance();
		
//		System.out.println(userNo);
		ArrayList<BasketDTO> basketList = basketDAO.ImportUsersBasket(userNo);
		System.out.println(basketList.size());
		request.setAttribute("basketList", basketList);
		if(basketList.size() > 0) {
			request.setAttribute("saleRate", basketList.get(0).getSale_rate());
		}
		
//        request.getRequestDispatcher(url).forward(request, response);
		

		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
//        response.sendRedirect(url);
        
	}

}
