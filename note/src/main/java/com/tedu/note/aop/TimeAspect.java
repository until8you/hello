package com.tedu.note.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 
 * �����ҵ��������ܲ���
 *
 */
@Component
@Aspect
public class TimeAspect {

	@Around("bean(*Service)")
	public Object time(ProceedingJoinPoint jp) throws Throwable{
		
		long t1 = System.currentTimeMillis();
		Object val = jp.proceed();
		long t2 = System.currentTimeMillis();
		//JointPoint ������Ի�ȡĿ��ҵ�񷽷�����ϸ��Ϣ ��������ǩ�� ���ò�����
		//Signature:ǩ�� ����ǩ��
		Signature m = jp.getSignature();
		System.out.println(m+"��ʱ��"+(t2-t1));
		return val;
		
	}
	
}
