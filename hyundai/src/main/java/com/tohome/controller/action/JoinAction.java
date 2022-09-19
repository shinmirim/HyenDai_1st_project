package com.tohome.controller.action;

import com.tohome.dao.BasketDAO;
import com.tohome.dao.MemberDAO;
import com.tohome.dto.MemberDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//Written  by 여명
public class JoinAction implements Action{
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = "TohomeServlet?command=main";
        HttpSession session =request.getSession();
        // 회원가입 폼으로부터 받은 값들
        String userId = request.getParameter("id");
        String userPwd = request.getParameter("pwd");
        String userName = request.getParameter("name");
        String bithYMD = request.getParameter("birth_ymd");
        String mobileNum = request.getParameter("mobile_num");
        String gender = request.getParameter("gender");
        String addressMain = request.getParameter("main_address");
        String addressDetail = request.getParameter("address_detail");
        String addressName = request.getParameter("address_name");
        
        MemberDAO dao = MemberDAO.getInstance();//static으로 선언해서 바로 가져오기 가능
        MemberDTO memberDTO = dao.memberJoin(
                userId, userPwd, userName, bithYMD, mobileNum, gender, addressMain, addressDetail, addressName
        );
        
        dao.close();
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
            session.setAttribute("basketCount", basketCount);
        }
        
        request.getRequestDispatcher(url).forward(request, response);
    }
}
