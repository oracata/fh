package com.fh.controller.weixin.textmsg;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.service.weixin.command.CommandService;
import com.fh.service.weixin.imgmsg.ImgmsgService;
import com.fh.service.weixin.textmsg.TextmsgService;

/** 
 * �����ƣ�TextmsgController
 * �����ˣ�FH 
 * ����ʱ�䣺2015-05-05
 */
@Controller
@RequestMapping(value="/textmsg")
public class TextmsgController extends BaseController {
	
	String menuUrl = "textmsg/list.do"; //�˵���ַ(Ȩ����)
	@Resource(name="textmsgService")
	private TextmsgService textmsgService;
	@Resource(name="commandService")
	private CommandService commandService;
	@Resource(name="imgmsgService")
	private ImgmsgService imgmsgService;
	
	/**
	 * ����
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "����Textmsg");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("TEXTMSG_ID", this.get32UUID());	//����
		pd.put("CREATETIME", Tools.date2Str(new Date())); //����ʱ��
		textmsgService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * ɾ��
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "ɾ��Textmsg");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			textmsgService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * �޸�
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "�޸�Textmsg");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		textmsgService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * �б�
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "�б�Textmsg");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String KEYWORD = pd.getString("KEYWORD");
			if(null != KEYWORD && !"".equals(KEYWORD)){
				pd.put("KEYWORD", KEYWORD.trim());
			}
			page.setPd(pd);
			List<PageData>	varList = textmsgService.list(page);	//�г�Textmsg�б�
			mv.setViewName("weixin/textmsg/textmsg_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//��ťȨ��
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * ȥ����ҳ��
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "ȥ����Textmsgҳ��");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("weixin/textmsg/textmsg_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * ȥ��ע�ظ�ҳ��
	 */
	@RequestMapping(value="/goSubscribe")
	public ModelAndView goSubscribe(){
		logBefore(logger, "ȥ��ע�ظ�ҳ��");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd.put("KEYWORD", "��ע");
			PageData msgpd = textmsgService.findByKw(pd);
			if(null != msgpd){
				mv.addObject("msg", "�ı���Ϣ");
				mv.addObject("content", msgpd.getString("CONTENT"));
			}else{
				msgpd = imgmsgService.findByKw(pd);
				if(null != msgpd){
					mv.addObject("msg", "ͼ����Ϣ");
					mv.addObject("content", "���⣺"+msgpd.getString("TITLE1"));
				}else{
					msgpd = commandService.findByKw(pd);
					if(null != msgpd){
						mv.addObject("msg", "����");
						mv.addObject("content", "ִ�����"+msgpd.getString("COMMANDCODE"));
					}else{
						mv.addObject("msg", "�޻ظ�");
					}
				}
			}
			mv.setViewName("weixin/subscribe");
			mv.addObject("pd", msgpd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * ȥ�޸�ҳ��
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "ȥ�޸�Textmsgҳ��");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = textmsgService.findById(pd);	//����ID��ȡ
			mv.setViewName("weixin/textmsg/textmsg_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * ����ɾ��
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "����ɾ��Textmsg");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				textmsgService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * �жϹؼ����Ƿ����
	 */
	@RequestMapping(value="/hasK")
	@ResponseBody
	public Object hasK(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("STATUS", "3");
			if(textmsgService.findByKw(pd) != null || commandService.findByKw(pd) != null || imgmsgService.findByKw(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//���ؽ��
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/*
	 * ������excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "����Textmsg��excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("�ؼ���");	//1
			titles.add("����");	//2
			titles.add("����ʱ��");	//3
			titles.add("״̬");	//4
			titles.add("��ע");	//5
			dataMap.put("titles", titles);
			List<PageData> varOList = textmsgService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("KEYWORD"));	//1
				vpd.put("var2", varOList.get(i).getString("CONTENT"));	//2
				vpd.put("var3", varOList.get(i).getString("CREATETIME"));	//3
				vpd.put("var4", varOList.get(i).get("STATUS").toString());	//4
				vpd.put("var5", varOList.get(i).getString("BZ"));	//5
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/* ===============================Ȩ��================================== */
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro�����session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================Ȩ��================================== */
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
