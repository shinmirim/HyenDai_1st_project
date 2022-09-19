package com.tohome.controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tohome.dao.BasketDAO;
import com.tohome.dao.LikeDAO;
import com.tohome.dto.BasketDTO;
import com.tohome.dto.LikeDTO;

public class LikePageAction implements Action{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url = "member/like_page.jsp";
		
		HttpSession session =request.getSession();
		
		if (session.getAttribute("UserNo")==null) {
			url="member/login_form.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		}
		
		int userNo = (int)session.getAttribute("UserNo");
		LikeDAO likeDAO = LikeDAO.getInstance();
		
		ArrayList<LikeDTO> likeList = likeDAO.ImportUsersLike(userNo);
		
		// 여기부터 다시
		request.setAttribute("likeList", likeList);

		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
