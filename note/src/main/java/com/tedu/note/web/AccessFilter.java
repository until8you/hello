package com.tedu.note.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tedu.note.entity.User;

public class AccessFilter implements Filter {

	public void destroy() {
	}

	private String login = "log_in.html";
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//�Ź�login.html
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		String path = req.getRequestURI();
//		System.out.println("access"+path);
//		System.out.println(req.getContextPath());
		if(path.endsWith(login)) {
			
			chain.doFilter(request, response);
			return;
		}
		//�Ź�������ʾҳ��
		if(path.endsWith("alert_error.html")) {
			chain.doFilter(request, response);
			return;
		}
		if(path.endsWith("demoImg.html")) {
			chain.doFilter(request, response);
			return;
		}
		//У���½״̬��û��½���ض��򵽵�½ҳ��
		User user = (User) session.getAttribute("loginUser");
		if(user==null) {
			//û�е�½ �ɶ��򵽵���½ҳ ��óɶ��򵽾���·���� �������
			res.sendRedirect(req.getContextPath()+"/"+login);
			return;
		}
		//��½�ɹ�,����
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
