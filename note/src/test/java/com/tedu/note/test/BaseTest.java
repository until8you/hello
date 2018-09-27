package com.tedu.note.test;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/*
 * һ��ѹ����ķ��������ԣ���ȡ�������࣬����Ҫʵ��������
 */
public abstract class BaseTest {

	protected ClassPathXmlApplicationContext cpxac = null;


	@Before
	public void testUserDao() {
		cpxac = new ClassPathXmlApplicationContext( "conf/spring-mvc.xml","conf/spring-service.xml","conf/spring-mybatis.xml");
	}

	@After
	public void closeAC() {
		cpxac.close();
	}

}