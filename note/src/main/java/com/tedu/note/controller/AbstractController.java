package com.tedu.note.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.note.util.JsonResult;

public abstract class AbstractController {
	
	//����һ���쳣�������ȴ�����͵��쳣���ٴ���������쳣
	@ExceptionHandler(Exception.class)//��ʾ���������쳣
	@ResponseBody
	public JsonResult handleException(Throwable e) {
		e.printStackTrace();
		return new JsonResult(e);
	}
}