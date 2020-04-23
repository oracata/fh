package com.fh.controller.system.createcode;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fh.controller.base.BaseController;
import com.fh.util.DelAllFile;
import com.fh.util.FileDownload;
import com.fh.util.FileZip;
import com.fh.util.Freemarker;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

/** 
 * �����ƣ�FreemarkerController
 * �����ˣ�FH 
 * ����ʱ�䣺2015��1��12��
 * @version
 */
@Controller
@RequestMapping(value="/createCode")
public class CreateCodeController extends BaseController {
	
	/**
	 * ���ɴ���
	 */
	@RequestMapping(value="/proCode")
	public void proCode(HttpServletResponse response) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		
		/* ============================================================================================= */
		String packageName = pd.getString("packageName");  			//����				========1
		String objectName = pd.getString("objectName");	   			//����				========2
		String tabletop = pd.getString("tabletop");	   				//��ǰ׺				========3
		tabletop = null == tabletop?"":tabletop.toUpperCase();		//��ǰ׺ת��д
		String zindext = pd.getString("zindex");	   	   			//��������
		int zindex = 0;
		if(null != zindext && !"".equals(zindext)){
			zindex = Integer.parseInt(zindext);
		}
		List<String[]> fieldList = new ArrayList<String[]>();   	//���Լ���			========4
		for(int i=0; i< zindex; i++){
			fieldList.add(pd.getString("field"+i).split(",fh,"));	//���Էŵ���������
		}
		
		Map<String,Object> root = new HashMap<String,Object>();		//��������ģ��
		root.put("fieldList", fieldList);
		root.put("packageName", packageName);						//����
		root.put("objectName", objectName);							//����
		root.put("objectNameLower", objectName.toLowerCase());		//����(ȫСд)
		root.put("objectNameUpper", objectName.toUpperCase());		//����(ȫ��д)
		root.put("tabletop", tabletop);								//��ǰ׺	
		root.put("nowDate", new Date());							//��ǰ����
		
		DelAllFile.delFolder(PathUtil.getClasspath()+"admin/ftl"); //���ɴ���ǰ,�����֮ǰ���ɵĴ���
		/* ============================================================================================= */
		
		String filePath = "admin/ftl/code/";						//���·��
		String ftlPath = "createCode";								//ftl·��
		
		/*����controller*/
		Freemarker.printFile("controllerTemplate.ftl", root, "controller/"+packageName+"/"+objectName.toLowerCase()+"/"+objectName+"Controller.java", filePath, ftlPath);
		
		/*����service*/
		Freemarker.printFile("serviceTemplate.ftl", root, "service/"+packageName+"/"+objectName.toLowerCase()+"/"+objectName+"Service.java", filePath, ftlPath);

		/*����mybatis xml*/
		Freemarker.printFile("mapperMysqlTemplate.ftl", root, "mybatis_mysql/"+packageName+"/"+objectName+"Mapper.xml", filePath, ftlPath);
		Freemarker.printFile("mapperOracleTemplate.ftl", root, "mybatis_oracle/"+packageName+"/"+objectName+"Mapper.xml", filePath, ftlPath);
		
		/*����SQL�ű�*/
		Freemarker.printFile("mysql_SQL_Template.ftl", root, "mysql���ݿ�ű�/"+tabletop+objectName.toUpperCase()+".sql", filePath, ftlPath);
		Freemarker.printFile("oracle_SQL_Template.ftl", root, "oracle���ݿ�ű�/"+tabletop+objectName.toUpperCase()+".sql", filePath, ftlPath);
		
		/*����jspҳ��*/
		Freemarker.printFile("jsp_list_Template.ftl", root, "jsp/"+packageName+"/"+objectName.toLowerCase()+"/"+objectName.toLowerCase()+"_list.jsp", filePath, ftlPath);
		Freemarker.printFile("jsp_edit_Template.ftl", root, "jsp/"+packageName+"/"+objectName.toLowerCase()+"/"+objectName.toLowerCase()+"_edit.jsp", filePath, ftlPath);
		
		/*����˵���ĵ�*/
		Freemarker.printFile("docTemplate.ftl", root, "˵��.doc", filePath, ftlPath);
		
		//this.print("oracle_SQL_Template.ftl", root);  ����̨��ӡ
		
		/*���ɵ�ȫ������ѹ����zip�ļ�*/
		FileZip.zip(PathUtil.getClasspath()+"admin/ftl/code", PathUtil.getClasspath()+"admin/ftl/code.zip");
		
		/*���ش���*/
		FileDownload.fileDownload(response, PathUtil.getClasspath()+"admin/ftl/code.zip", "code.zip");
		
	}
	
}
