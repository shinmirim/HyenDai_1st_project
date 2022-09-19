package com.tohome.controller.action;

import com.tohome.dao.MemberDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IdCheckFormAction implements Action{
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "member/idcheck.jsp";

        String id = request.getParameter("id").trim();

        MemberDAO dao = MemberDAO.getInstance();
        int message = dao.checkID(id);

        request.setAttribute("message", message);
        request.setAttribute("id", id);
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
