package com.tedu.note.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class PointCutAspect {

//	@Before("bean(*Service)")
//	public void test1() {
//		System.out.println("�������ԣ�");
//	}
	
//	@Before("within(com.tedu.*.service.*erviceImpl)")
//	public void test2() {
//		System.out.println("�������");
//	}
	
	/**
	 * * �����������δ�
	 * ���� �����������
	 */
//	@Before("execution(* com.tedu.note.service.UserService.login(..))")
//	public void test3() {
//		System.out.println("���������");
//	}
	@Before("execution(* com.tedu.note.*.*Service.list*(..))")
	public void test3() {
		System.out.println("��������� һ���й���ķ�������");
	}
}
