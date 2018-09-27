package com.tedu.note.util;

import java.io.Serializable;
/*
 * ���ڷ�װ��������ͻ��˷��ص����ݣ�����������Ϣ��user����
 */
public class JsonResult implements Serializable{
	
	public static final int SUCCESS = 0;//��½�ɹ�
	public static final int ERROR = 1;//��½ʧ��
	
	private int status;//��½״̬
	private String message;//������Ϣ
	private Object data;//������ȷʱ ������
	
	
	public JsonResult() {
		
	}

	public JsonResult(String error) {
		status = ERROR;
		message = error;
	}
	//����ʱ�Ĺ�����
	public JsonResult(Throwable e) {
		status = ERROR;
		message = e.getMessage();
	}
	
	//����ͬ״̬�µ��쳣������
	public JsonResult(int status,Throwable e) {
		this.status = status;
		message = e.getMessage();
	}
	//��ȷʱ�Ĺ�����
	public JsonResult(Object data) {
		status = SUCCESS;
		this.data = data;
	}
	
	public int getStatus() {
		return status;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
