package com.tohome.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tohome.dao.LikeDAO;

public class LikeAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session =request.getSession();
		
		int userNo = (int)session.getAttribute("UserNo");
		int prodNo =  Integer.parseInt(request.getParameter("prod_no"));
		
		LikeDAO likeDAO = LikeDAO.getInstance();
		likeDAO.InsertLike(userNo, prodNo);
	}

}
