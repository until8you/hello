package com.tedu.note.service;

import com.tedu.note.entity.User;

/*
 * ҵ���ӿ�
 */
public interface UserService {
	/*
	 * ��½���ܣ���½�ɹ������û���Ϣ����½ʧ�ܣ��׳��쳣
	 * @param name �û���
	 * @param password ����
	 * @return �����½�ɹ��ͷ��ص�½�û���Ϣ
	 * һ��������ʵ�ֵ�¼���ܣ�����ɹ��������� ���ʧ�ܣ����׳��Զ����쳣 �쳣����Ҫ���κβ�����ֻ��ʵ��RuntimeException�ӿ�
	 * @throws UserNotFoundException �û�������
	 * @throws PassWordException �������
	 */
	User login(String name,String password) throws UserNotFondException,PassWordException;
	
	/*
	 * ע�Ṧ��
	 * @param name �û���
	 * @param password ����
	 * @param nick �ǳ�
	 * @param confirm ��֤����
	 * @return ע��ɹ����û���Ϣ
	 * @throws UserNotFoundException �û����쳣
	 * @throws PassWordException �������
	 */
	User regist(String name,String nick,String password,String confirm) throws 
		UserNameException,PassWordException;
}
