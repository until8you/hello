package com.tedu.note.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tedu.note.entity.Note;

public interface NoteDao {

	List<Map<String,Object>> findNotesByNotebookId(String notebookId);
	
	Note findNoteById(String noteId);
	
	int addNote(Note note);
	
	int updateNote(Note note);
	
	List<Map<String,Object>> findNotesByStatusId();
	
	int deleteNote(String noteId);
	
	//���������Ҫ���ע�� ע���Ǹ��������Ӧ@Param("userId")
	List<Map<String,Object>> findNotes(@Param("userId") String userId,
			                           @Param("notebookId") String notebookId,
			                           @Param("statusId") String statusId);
	
	int deleteNotes(@Param("ids") String... ids);
}
