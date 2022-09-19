package com.tohome.controller.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import com.tohome.dao.BasketDAO;
import com.tohome.dao.MemberDAO;
import com.tohome.dto.MemberDTO;

//Written  by 여명
public class LoginAction implements Action{
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url="member/login_fail.jsp";
        HttpSession session =request.getSession();//jsp에서는 바로 session쓸 수 있는데 java에서는 생성하고 써야함

        // 로그인 폼으로부터 받은 아이디와 패스워드
        String userId = request.getParameter("user_id");
        String userPwd = request.getParameter("user_pw");

        // 회원 테이블 DAO를 통해 회원 정보 DTO 획득
        MemberDAO dao = MemberDAO.getInstance();//static으로 선언해서 바로 가져오기 가능
        MemberDTO memberDTO = dao.getLoginMemberDTO(userId, userPwd);
        
        BasketDAO bdao =  BasketDAO.getInstance();
        int basketCount = bdao.CountBasketItem(memberDTO.getUser_no());
        bdao.close();
        // 로그인 성공 여부에 따른 처리
        if (memberDTO.getUser_id() != null) {
            // 로그인 성공
            session.setAttribute("UserId", memberDTO.getUser_id());
            session.setAttribute("UserName", memberDTO.getUser_name());
            session.setAttribute("UserNo", memberDTO.getUser_no());
            System.out.println(memberDTO.getUser_no());
//            response.sendRedirect("login_form.jsp");
            session.setAttribute("basketCount", basketCount);
            url = "TohomeServlet?command=main";
        }
        else {
            // 로그인 실패
            request.setAttribute("LoginErrMsg", "로그인 오류입니다.");
        }
        System.out.println(url);
        request.getRequestDispatcher(url).forward(request, response);
    }
}
