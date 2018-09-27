package com.tedu.note.service;

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import com.tedu.note.dao.UserDao;
import com.tedu.note.entity.User;
/*
 * һ���Խӿ�������ĸСдΪid
 */
@Service("userService")
//@Transactional //������ӣ����еķ������ᱻ�������
public class UserServiceImpl implements UserService{
	//��ֵע�룬jdbc.properties�ж�ȡ
	@Value("#{db.salt}")
	private String salt;
	
	@Resource(name="userDao")
	private UserDao userDao;
	/*
	 * һ��������ʵ�ֵ�¼���ܣ�����ɹ��������� ���ʧ�ܣ����׳��Զ����쳣
	 * Ҫʱ��ע�⵽���ñ������Դ�ֵ��ʱ�򣬻᲻����null���ᱨ��ָ���쳣����һ���������ͱ���
	 * ֵΪnull��ʱ�򣬷������Ժͷ����Ǿͻᱨ��
	 */
	public User login(String name, String password) throws UserNotFondException, PassWordException {
//		//����쳣
//		String s = null;
//		s.charAt(0);
		
		//�鿴���淽����ִ��ʱ��
		System.out.println("login()");
		//���жϴ���Ĳ����Ƿ�Ϊ�գ���ֹ��ָ���쳣 ����password==null||password.trim().isEmpty()���ܵߵ�
		//��Ϊ���password==null��ʱ���أ�password.trim()����ֿ�ָ���쳣
		if(password==null||password.trim().isEmpty()) {
			throw new PassWordException("�����");
		}
		if(name==null||name.trim().isEmpty()) {
			throw new UserNotFondException("�û���Ϊ��");
		}
		User user = userDao.findUserByName(name);
		if(user==null) {
			throw new UserNotFondException("�û�������");
		}
		//������� �ȶ�ժҪ���� ���������ӻ�����Χ������ȫ��
		String pwd = DigestUtils.md5Hex(salt+password.trim());
		if(!pwd.equals(user.getPassword())) {
			throw new PassWordException("�������");
		}
		return user;
	}
	
	//��֤����֤
	public User regist(String name, String nick, String password, String confirm)
			throws UserNameException, PassWordException {
		//���name,�����ظ�
		if(name==null||name.trim().isEmpty()) {
			throw new UserNameException("���ܿ�");
		}
		User one = userDao.findUserByName(name);
		if(one!=null) {
			throw new UserNameException("���ظ�");
		}
		//�������
		if(password==null||password.trim().isEmpty()) {
			throw new PassWordException("���ܿ�");
		}
		if(!password.equals(confirm)) {
			throw new PassWordException("ȷ������");
		}
		//���nick
		if(nick==null||nick.trim().isEmpty()) {
			nick = name;
		}
		//����id
		String id = UUID.randomUUID().toString();
		String token = "";
		//���������
		password = DigestUtils.md5Hex(salt+password);
		//��װ����
		User user = new User(id, name, password, token, nick);
		int n = userDao.addUser(user);
		if(n!=1) {
			throw new RuntimeException("���ʧ��");
		}
		return user;
	}


}
