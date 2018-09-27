package com.tedu.note.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tedu.note.entity.User;
import com.tedu.note.service.PassWordException;
import com.tedu.note.service.UserNameException;
import com.tedu.note.service.UserNotFondException;
import com.tedu.note.service.UserService;
import com.tedu.note.util.JsonResult;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController{

	@Resource
	private UserService userService;
	
	//��½����
	@RequestMapping("/login.do")
	@ResponseBody
	public Object login(String name, String password,HttpSession session) {
			User user = userService.login(name, password);
			//��½�ɹ�������½״̬��ӵ�session�У�������������У���½
			session.setAttribute("loginUser", user);
			return new JsonResult(user);
	}
	
	//ע�Ṧ��
	@RequestMapping("/regist.do")
	@ResponseBody
	public Object regist(String name,String nick,String password,String confirm) {
		User user = userService.regist(name, nick, password, confirm);
		return new JsonResult(user);
	}
	
	//ע�Ṧ��
	@RequestMapping("/hart.do")
	@ResponseBody
	public Object hart() {
		return new JsonResult("ok");
	}
	
	/*
	 * @ResponseBody ���Զ�������ƽ��
	 * 1.�����Javabean(���ϣ�����)������json
	 * 2.�����byte���飬��byte����ֱ��װ����Ӧ��Ϣ��body��
	 * 3.produces ������Ӧcontenttype
	 */
	@RequestMapping(value="/image.do",produces="image/png")
	@ResponseBody
	public byte[] image() throws Exception {
		return createPng();
	}
	
	//����ͼƬ
	@RequestMapping(value="/downloading.do",produces=" application/octet-stream")
	@ResponseBody
	public byte[] downLoad(HttpServletResponse res) throws Exception {
		res.setHeader("Content-Disposition", "attachment; filename=\"demo.png\"");
		return createPng();
	}
	
	//����Excel
	@RequestMapping(value="/excel.do",produces=" application/octet-stream")
	@ResponseBody
	public byte[] excel(HttpServletResponse res) throws Exception {
		res.setHeader("Content-Disposition", "attachment; filename=\"demo.xls\"");
		return createExcel();
	}
	
	//���쳣���͵�״̬����status
	@ExceptionHandler(UserNotFondException.class)//��ʾ���������쳣
	@ResponseBody
	public Object handlerUserNotFond(UserNotFondException e) {
		e.printStackTrace();
		return new JsonResult(2,e);
	}
	
	@ExceptionHandler(UserNameException.class)
	@ResponseBody
	public Object handleUserName(UserNameException e) {
		e.printStackTrace();
		//status==4�����½�û���Ϊ��
		return new JsonResult(4,e);
	}
	
	@ExceptionHandler(PassWordException.class)//��ʾ���������쳣
	@ResponseBody
	public Object handlerPassWord(PassWordException e) {
		e.printStackTrace();
		return new JsonResult(3,e);
	}
	
	
	/*
	 * ����һ��ͼƬ������ΪPNG�����ر����Ժ������
	 * 
	 */
	private byte[] createPng() throws IOException {
		BufferedImage img = new BufferedImage(200, 80, BufferedImage.TYPE_3BYTE_BGR);
		//ͼƬ����
		img.setRGB(100, 40, 0xffffff);
		//ͼƬ�����png ByteArrayOutputStream�ڴ����� ���ļ�������
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(img, "png", out);
		//�ر���
		out.close();
		//ת����bytes
		byte[] png = out.toByteArray();
		return png;
	}
	
	//����Excel 
	private byte[] createExcel() throws IOException{
		//��Ҫ��������excel�İ�org.apache.poi 3.13
		//���ɹ�����
		HSSFWorkbook workbook = new HSSFWorkbook();
		//���ɹ�����
		HSSFSheet sheet = workbook.createSheet();
		//������
		HSSFRow row = sheet.createRow(0);
		//����cell
		HSSFCell cell = row.createCell(0);
		//��������
		cell.setCellValue("Hello Word!");
		
		//�����ڴ�������
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		//����������������
		workbook.write(out);
		out.close();
		
		return out.toByteArray();
		
	}
	
	
	//�����ϴ���ͨ��springmvc�� ��Ҫ���ý����� CommonsmultipatResolver
	//�ĵ��ϴ�
	@RequestMapping("/upload.do")
	@ResponseBody
	public JsonResult upload(MultipartFile userfile1,MultipartFile userfile2) throws Exception {
		//��ȡԴ�ļ���
		String name1 = userfile1.getOriginalFilename();
		String name2 = userfile2.getOriginalFilename();
		
		System.out.println(name1);
		System.out.println(name2);
		
		//1.��һ�ֱ���
		File dir = new File("D:/demo");
		dir.mkdir();
		File f1 = new File(dir,name1);
		File f2 = new File(dir,name2);
//		userfile1.transferTo(f1);
		
		//3.��������������
		InputStream in1 = userfile1.getInputStream();
		FileOutputStream out1 = new FileOutputStream(f1);
		int b;
		while((b=in1.read())!=-1) {
			out1.write(b);
		}
		in1.close();
		out1.close();
		InputStream in2 = userfile2.getInputStream();
		FileOutputStream out2= new FileOutputStream(f2);
		byte[] buf = new byte[8*1024];
		int n;
		while((n=in2.read(buf))!=-1) {
			out2.write(buf,0,n);
		}
		in2.close();
		out2.close();
		
		return new JsonResult(true);
		
	}
}
