package com.tedu.note.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tedu.note.entity.User;
import com.tedu.note.util.JsonResult;

@Component
public class Accessinterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getRequestURI();
//		System.out.println("interceptor:"+path);
		//���û�е�½�����ش����json
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if(user==null) {
			JsonResult result = new JsonResult("��Ҫ���µ�½");
			//����response���ؽ��
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			//��javabeanת����json
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(result);
			//ͨ��response������json
			response.getWriter().println(json);
			response.flushBuffer();
			return false;
		}
		//�����½ �������
		return true;//�������
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
