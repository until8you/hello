package com.tedu.note.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tedu.note.dao.NoteBookDao;
import com.tedu.note.dao.UserDao;
import com.tedu.note.entity.NoteBook;
import com.tedu.note.entity.User;

@Service("noteBookService")
@Transactional //������ӣ����еķ������ᱻ�������
public class NoteBookServiceImpl implements NoteBookService{

	@Resource
	private NoteBookDao noteBookDao;
	@Resource
	private UserDao userDao;
	//���õ��ļ��У�����д��
	@Value("#{db.pageSize}")
	private int pageSize;
	public List<Map<String, Object>> listNoteBooks(String userId) throws UserNotFondException {
		if(userId==null||userId.trim().isEmpty()) {
			throw new UserNotFondException("Id����Ϊ��");
		}
		User user = userDao.findUserById(userId);
		if(user==null) {
			throw new UserNotFondException("�û�������");
		}
		return noteBookDao.findNoteBooksByUesrId(userId);
	}

	//����һ���ʼǱ�
	public NoteBook add(String name, String userId) throws UserNotFondException, NoteBookNameNullException {
		//�ж�����
		if(name==null||name.trim().isEmpty()) {
			throw new NoteBookNameNullException("NoteBookNameNullException����Ϊ��");
		}
		if(userId==null||userId.trim().isEmpty()) {
			throw new UserNotFondException("userId����Ϊ��");
		}
		User user = userDao.findUserById(userId);
		if(user==null) {
			throw new UserNotFondException("userIdû�ж�Ӧ���û�");
		}
		String noteBookId = UUID.randomUUID().toString();
		String typeId = "5";
		String desc = "";
		String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		NoteBook noteBook = new NoteBook(noteBookId, userId, typeId, name, desc, createTime);
		int n = noteBookDao.addNoteBook(noteBook);
		if(n!=1) {
			throw new NoteBookNameNullException("noteBook����ʧ��");
		}
		return noteBook;
	}

	public List<Map<String, Object>> listNoteBooks(String userId, Integer page) throws UserNotFondException {
		if(userId==null||userId.trim().isEmpty()) {
			throw new UserNotFondException("Id����Ϊ��");
		}
		User user = userDao.findUserById(userId);
		if(user==null) {
			throw new UserNotFondException("�û�������");
		}
		if(page==null) {
			page = 0;
		}
		int start = page*pageSize;
		String table = "cn_notebook";
		return noteBookDao.findNoteBooksByPage(userId, start, pageSize, table);
	}

	
}
