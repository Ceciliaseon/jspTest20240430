package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.MemberVO;
import service.MemberService;
import service.MemberServiceImpl;

@WebServlet("/memb/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	private RequestDispatcher rdp;
	private String destPage;
	private int isOk;
	private MemberService msv;

    public MemberController() {
    	msv = new MemberServiceImpl();
    }


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String uri = request.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/")+1);
		
		Boolean bl = false;
		
		switch (path) {
		case "join":
			destPage = "/member/join.jsp";
			break;
			
		case "register" :
			try {
				String id = request.getParameter("id");
				String pwd = request.getParameter("pwd");
				String email = request.getParameter("email");
				int age = Integer.parseInt(request.getParameter("age"));
				String phone = request.getParameter("phone");
				
				MemberVO mvo = new MemberVO(id, pwd, email, age, phone);
				log.info("join mvo >>> {}", mvo);
				isOk = msv.register(mvo);
				log.info("join >> {}", (isOk>0)?"ok":"fail");
				destPage = "/index.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "login" :
			try {
				String id = request.getParameter("id");
				String pwd = request.getParameter("pwd");
				
				MemberVO mvo = new MemberVO(id, pwd);
				MemberVO loginMvo = msv.login(mvo);
				
				if (loginMvo !=null) {
					HttpSession ses = request.getSession();
					ses.setAttribute("ses", loginMvo);
					ses.setMaxInactiveInterval(10*60);
				} else {
					request.setAttribute("msg_login", "fail");
				}
				
				bl = true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		
		case "logout" :
			try {
				HttpSession ses = request.getSession();
				
				MemberVO mvo = (MemberVO)ses.getAttribute("ses");
				log.info("logout mvo >>> {}", mvo);
				int isOk = msv.lasgLogin(mvo.getId());
				log.info("logout >> {}", (isOk>0)?"ok":"fail");
				ses.invalidate(); //세션 무효화
				
				bl = true;

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
			
		case "list" :
			try {
				List<MemberVO> list = msv.getList();
				request.setAttribute("list", list);
				destPage = "/member/list.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "modify" :
			destPage ="/member/modify.jsp";
			break;
			
		case "update" :
			try {
				String id = request.getParameter("id");
				String pwd = request.getParameter("pwd");
				String email = request.getParameter("email");
				int age = Integer.parseInt(request.getParameter("age"));
				String phone = request.getParameter("phone");
				
				MemberVO mvo = new MemberVO(id, pwd, email, age, phone);
				int isOk = msv.update(mvo);
				log.info("update >> {}", (isOk>0)?"ok":"fail");
				
				if (isOk > 0) {
					request.setAttribute("msg_update", "ok");
					HttpSession ses = request.getSession();
					ses.invalidate();					
					destPage = "/index.jsp";
				} else {
					request.setAttribute("msg_update", "fail");
					destPage = "/memb/modify.jsp";
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "delete" :
			try {
				String id = request.getParameter("id");
				int isOk = msv.delete(id);
				log.info("delete >> {}", (isOk>0)?"ok":"fail");
				if (isOk > 0) {
					request.setAttribute("msg_delete", "ok");
					HttpSession ses = request.getSession();
					ses.invalidate();
					destPage = "/index.jsp";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		log.info(destPage);
		if (bl == true) {
			response.sendRedirect("/index.jsp");
		} else {			
			rdp = request.getRequestDispatcher(destPage);
			rdp.forward(request, response);
		}
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		service(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		service(request, response);
	}

}
