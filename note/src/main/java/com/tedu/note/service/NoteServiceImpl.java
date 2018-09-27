package com.tedu.note.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import com.tedu.note.dao.NoteBookDao;
import com.tedu.note.dao.NoteDao;
import com.tedu.note.dao.StarsDao;
import com.tedu.note.dao.UserDao;
import com.tedu.note.entity.Note;
import com.tedu.note.entity.Stars;
import com.tedu.note.entity.User;

@Service("noteService")
//@Transactional //������ӣ����еķ������ᱻ�������
public class NoteServiceImpl implements NoteService{

	@Resource
	private NoteDao noteDao;
	@Resource
	private NoteBookDao noteBookDao;
	@Resource
	private UserDao userDao;
	public List<Map<String, Object>> listNotes(String notebookId) throws NotebookIdNotFindException{
		//�Դ���Ĳ��������ж�
		if(notebookId==null||notebookId.trim().isEmpty()) {
			throw new NotebookIdNotFindException("notebookIdΪ��");
		}
		//����id�ܷ��ҵ��ʼǱ� ���ַ�ʽ��ʡ����
		int n = noteBookDao.countNotebookById(notebookId);
		if(n!=1) {
			throw new NotebookIdNotFindException("û��id��Ӧ�ıʼǱ�");
		}
		
		//List<Map<String, Object>> Notes = noteDao.findNotesByNotebookId(notebookId);
		List<Map<String, Object>> Notes = noteDao.findNotes(null, notebookId, "1");
		return Notes;
	}
	
	public Note findNote(String noteId) throws NoteIdNotFoundException {
		if(noteId==null||noteId.trim().isEmpty()) {
			throw new NoteIdNotFoundException("noteIdΪ��");
		}
		Note note = noteDao.findNoteById(noteId);
		if(note==null) {
			throw new NoteIdNotFoundException("noteIdû�ж�Ӧ�ıʼ�");
		}
		return note;
	}
	//��ӱʼ�ҵ��
	//@Transactional
	public Note addNote(String title, String notebookId, String userId) throws TitleIsNullException,NotebookIdNotFindException,UserIdNotFoundException{
		//�жϴ����Ĳ���
		if(notebookId==null||notebookId.trim().isEmpty()) {
			throw new NotebookIdNotFindException("notebookIdΪ��");
		}
		if(userId==null||userId.trim().isEmpty()) {
			throw new UserIdNotFoundException("userIdΪ��");
		}
		int n = noteBookDao.countNotebookById(notebookId);
		if(n!=1) {
			throw new NotebookIdNotFindException("notebookIdû�ж�Ӧ�ıʼǱ�");
		}
		User user = userDao.findUserById(userId);
		if(user==null) {
			throw new UserNotFondException("�������û�");
		}
		
		String noteId = UUID.randomUUID().toString();
		String statusId = "1";
		String typeId = "1";
		String body = "";
		long createTime = System.currentTimeMillis();
		long lastModifyTime = System.currentTimeMillis();
		Note note = new Note(noteId,notebookId,userId,statusId,typeId,title,body,createTime,lastModifyTime);
		int i = noteDao.addNote(note);
		if(i!=1) {
			throw new TitleIsNullException("����ʧ��");
		}
		//��ǰ����ᴫ����addStars�����У����ϳ�һ������
		addStars(userId,-11);
		return note;
	}

	public boolean update(String noteId, String title, String body) throws NoteIdNotFoundException{
		if(noteId==null||noteId.trim().isEmpty()) {
			throw new NoteIdNotFoundException("noteId����Ϊ��");
		}
		Note note = noteDao.findNoteById(noteId);
		if(note==null) {
			throw new NoteIdNotFoundException("û�ж�Ӧ�ıʼǶ���");
		}
		Note data = new Note();
		if(title!=null&&!title.equals(note.getTitle())) {
			data.setTitle(title);
		}
		if(body!=null&&!body.equals(note.getBody())) {
			data.setBody(body);
		}
		data.setNoteId(noteId);
		System.out.println(data);
		data.setLastModifyTime(System.currentTimeMillis());
		System.out.println(data);
		int n = noteDao.updateNote(data);
		if(n!=1) {
			throw new NoteIdNotFoundException("����ʧ��");
		}
		return n==1;
	}
	
	public boolean moveNote(String noteId, String notebookId) throws NoteIdNotFoundException,NotebookIdNotFindException {
		if(notebookId==null||notebookId.trim().isEmpty()) {
			throw new NotebookIdNotFindException("notebookIdΪ��");
		}
		if(noteId==null||noteId.trim().isEmpty()) {
			throw new NoteIdNotFoundException("noteId����Ϊ��");
		}
		Note note = noteDao.findNoteById(noteId);
		if(note==null) {
			throw new NoteIdNotFoundException("û�ж�Ӧ�ıʼǶ���");
		}
		note.setNoteId(noteId);
		note.setNotebookId(notebookId);
		note.setLastModifyTime(System.currentTimeMillis());
		int n = noteDao.updateNote(note);
		return n==1;
	}

	public boolean deleteNote(String noteId) throws NoteIdNotFoundException {
		if(noteId==null||noteId.trim().isEmpty()) {
			throw new NoteIdNotFoundException("noteIdΪ��");
		}
		Note note = noteDao.findNoteById(noteId);
		if(note==null) {
			throw new NoteIdNotFoundException("noteIdû�ж�Ӧ�ıʼ�");
		}
		note.setStatusId("0");
		note.setLastModifyTime(System.currentTimeMillis());
		int n = noteDao.updateNote(note);
		return n==1;
	}
	
	public List<Map<String,Object>> listTrashNotes(String userId)throws UserNotFondException{
		if(userId==null||userId.trim().isEmpty()) {
			throw new UserNotFondException("userIdΪ��");
		}
		User user = userDao.findUserById(userId);
		if(user==null) {
			throw new UserNotFondException("û�ж�Ӧ�ıʼ�");
		}
		//List<Map<String,Object>> notes = noteDao.findNotesByStatusId();
		return noteDao.findNotes(userId, null, "0");
	}

	public boolean replayNote(String noteId, String notebookId)
			throws NoteIdNotFoundException, NotebookIdNotFindException {
		if(notebookId==null||notebookId.trim().isEmpty()) {
			throw new NotebookIdNotFindException("notebookIdΪ��");
		}
		if(noteId==null||noteId.trim().isEmpty()) {
			throw new NoteIdNotFoundException("noteId����Ϊ��");
		}
		Note note = noteDao.findNoteById(noteId);
		if(note==null) {
			throw new NoteIdNotFoundException("û�ж�Ӧ�ıʼǶ���");
		}
		
		note.setNoteId(noteId);
		note.setNotebookId(notebookId);
		note.setStatusId("1");
		note.setLastModifyTime(System.currentTimeMillis());
		
		int n = noteDao.updateNote(note);
		return n==1;
	}

	public boolean rollbackNote(String noteId, String userId) throws UserNotFondException, NoteIdNotFoundException {
		if(userId==null||userId.trim().isEmpty()) {
			throw new UserNotFondException("userIdΪ��");
		}
		User user = userDao.findUserById(userId);
		if(user==null) {
			throw new UserNotFondException("û�ж�Ӧ�ıʼ�");
		}
		if(noteId==null||noteId.trim().isEmpty()) {
			throw new NoteIdNotFoundException("noteId����Ϊ��");
		}
		Note note = noteDao.findNoteById(noteId);
		if(note==null) {
			throw new NoteIdNotFoundException("û�ж�Ӧ�ıʼǶ���");
		}
		
		int n = noteDao.deleteNote(noteId);
		
		return n==1;
	}

	//���ڲ�������ɾ�������� String...�൱��String[]  String...����ʱ�Ƚ�ʡ��  ...�䳤����
	//@Transactional
	public int deleteNotes(String... noteIds) throws NoteIdNotFoundException {
//		for (String id : noteIds) {
//			int n = noteDao.deleteNote(id);
//			if(n!=1) {
//				throw new NoteIdNotFoundException("id����");
//			}
//			
//		}
		//һ��sql���������Ի��һ��һ��ɾ��Ч������
		int n = noteDao.deleteNotes(noteIds);
		if(n!=noteIds.length) {
			throw new NoteIdNotFoundException("id����");
		}
		return n;
	}

	
	@Resource
	private StarsDao starsDao;
	
	//@Transactional
	public boolean addStars(String userId, int stars) throws UserNotFondException {
		if(userId==null||userId.trim().isEmpty()) {
			throw new UserNotFondException("userIdΪ��");
		}
		User user = userDao.findUserById(userId);
		if(user==null) {
			throw new UserNotFondException("û�ж�Ӧ�ıʼ�");
		}
		
		//����Ƿ�����
		Stars st = starsDao.findStarsByUserId(userId);
		System.out.println(st);
		if(st==null) {
			//���û������
			String id = UUID.randomUUID().toString();
			st = new Stars(id,userId,stars);
			int n = starsDao.insertStars(st);
			if(n!=1) {
				throw new RuntimeException("����ʧ��");
			}
		}else {//���������
			int n = st.getStars()+stars;
			//starsΪ����ʱ�����ܻᷢ���۷ֵ����������
			if(n<0) {
				throw new RuntimeException("�۷�̫��");
			}
			st.setStars(st.getStars()+stars);
			n = starsDao.updateStars(st);
			if(n!=1) {
				throw new RuntimeException("����ʧ��");
			}
		}
		
		return true;
	}
}
