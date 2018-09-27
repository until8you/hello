package com.tedu.note.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImageServlet
 */
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//����ͼƬ
		byte[] png = createPng();
		//������Ӧ����
		response.setContentType("image/png");
		response.setContentLength(png.length);
		//�����ݷ��͵�body�� getOutputStream()�����ֽ�����
		response.getOutputStream().write(png);
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
	
}
