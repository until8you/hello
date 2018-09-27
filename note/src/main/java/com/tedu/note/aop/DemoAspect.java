package com.tedu.note.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.tedu.note.service.UserNotFondException;
import com.tedu.note.util.JsonResult;

/**
 * 
 * ����һ���������������һ����ͨ��Javabean
 *
 */
//@Component
//@Aspect
public class DemoAspect {
	//����ʱaspectj���µ� before��֪ͨ ��ָ���淽����ִ��ʱ����Ŀ�귽��֮ǰִ��
	//����test����������userService�����з���֮ǰִ��
	@Before("bean(userService)")
	public void test() {
		System.out.println("AOP : hello aspectj");
	}
	
	//ִ��ʱ������Ŀ�귽��֮��ִ��
	@After("bean(userService)")
	public void test2() {
		System.out.println("After");
	}
	
	@AfterReturning("bean(userService)")
	public void test3() {
		System.out.println("afterReturning");
	}
	
	@AfterThrowing("bean(userService)")
	public void test4() {
		System.out.println("AfterThrowing");
	}
	
	/**
	 * ����֪ͨ
	 * 1.�����з���ֵ
	 * 2.�����в���
	 * 3.�������쳣
	 * 4.
	 */
	//@Around("bean(userService)")
	public Object test5(ProceedingJoinPoint pj) throws Throwable{
		Object val = pj.proceed();
		System.out.println("ҵ������"+val);
		return new UserNotFondException("���ǲ��õ�½");
	}
}
