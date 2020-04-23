package com.fh.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;


public class ImageAnd64Binary {
    public static void main(String[] args){
    	
		String imgSrcPath 	 = "d:/abc/123.jpg";     //����64�����ͼƬ��·��
		String imgCreatePath = "E:\\apache-tomcat-6.0.37\\webapps/pro/ueditor2/jsp/upload1/20140318/480ace2bfc6e44608595bd4adbdeb067.jpg";     //��64��������ͼƬ��·��
		imgCreatePath=imgCreatePath.replaceAll("\\\\", "/");
		System.out.println(imgCreatePath);
    	String strImg = getImageStr(imgSrcPath);
    	System.out.println(strImg);
        generateImage(strImg, imgCreatePath);
    }
    
   /**
     * ��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��
     * @param imgSrcPath ����64�����ͼƬ��·��
     * @return
     */
    public static String getImageStr(String imgSrcPath){
        InputStream in = null;
        byte[] data = null;
        
        //��ȡͼƬ�ֽ�����
        try {
            in = new FileInputStream(imgSrcPath);        
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //���ֽ�����Base64����
        BASE64Encoder encoder = new BASE64Encoder();
        
        return encoder.encode(data);//����Base64��������ֽ������ַ���
    }
    
    /**
     * ���ֽ������ַ�������Base64���벢����ͼƬ
     * @param imgStr            ת��ΪͼƬ���ַ���
     * @param imgCreatePath     ��64��������ͼƬ��·��
     * @return
     */
    public static boolean generateImage(String imgStr, String imgCreatePath){
        if (imgStr == null) //ͼ������Ϊ��
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64����
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i) {
                if(b[i]<0) {//�����쳣����
                    b[i]+=256;
                }
            }
            
            OutputStream out = new FileOutputStream(imgCreatePath);    
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
}