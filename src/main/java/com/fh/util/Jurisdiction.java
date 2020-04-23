package com.fh.util;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.fh.entity.system.Menu;

/**
 * Ȩ�޴���
 * @author:fh
*/
public class Jurisdiction {

	/**
	 * ����Ȩ�޼���ʼ����ťȨ��(���ư�ť����ʾ)
	 * @param menuUrl  �˵�·��
	 * @return
	 */
	public static boolean hasJurisdiction(String menuUrl){
		//�ж��Ƿ�ӵ�е�ǰ����˵���Ȩ�ޣ��ڲ�����,��ֹͨ��url���������˵�Ȩ�ޣ�
		/**
		 * ���ݵ���Ĳ˵���xxx.doȥ�˵��е�URLȥƥ�䣬��ƥ�䵽�˴˲˵����ж��Ƿ��д˲˵���Ȩ�ޣ�û�еĻ���ת��404ҳ��
		 * ���ݰ�ťȨ�ޣ���Ȩ��ť(��ǰ��Ĳ˵��ͽ�ɫ�и���ť��Ȩ��ƥ��)
		 */
		//shiro�����session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		Boolean b = true;
		List<Menu> menuList = (List)session.getAttribute(Const.SESSION_allmenuList); //��ȡ�˵��б�
		
		for(int i=0;i<menuList.size();i++){
			for(int j=0;j<menuList.get(i).getSubMenu().size();j++){
				if(menuList.get(i).getSubMenu().get(j).getMENU_URL().split(".do")[0].equals(menuUrl.split(".do")[0])){
					if(!menuList.get(i).getSubMenu().get(j).isHasMenu()){				//�ж����޴˲˵�Ȩ��
						return false;
					}else{																//��ť�ж�
						Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_QX);//��ťȨ��
						map.remove("add");
						map.remove("del");
						map.remove("edit");
						map.remove("cha");
						String MENU_ID =  menuList.get(i).getSubMenu().get(j).getMENU_ID();
						String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();	//��ȡ��ǰ��¼��loginname
						Boolean isAdmin = "admin".equals(USERNAME);
						map.put("add", (RightsHelper.testRights(map.get("adds"), MENU_ID)) || isAdmin?"1":"0");
						map.put("del", RightsHelper.testRights(map.get("dels"), MENU_ID) || isAdmin?"1":"0");
						map.put("edit", RightsHelper.testRights(map.get("edits"), MENU_ID) || isAdmin?"1":"0");
						map.put("cha", RightsHelper.testRights(map.get("chas"), MENU_ID) || isAdmin?"1":"0");
						session.removeAttribute(Const.SESSION_QX);
						session.setAttribute(Const.SESSION_QX, map);	//���·��䰴ťȨ��
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * ��ťȨ��(������У��)
	 * @param menuUrl  �˵�·��
	 * @param type  ����(add��del��edit��cha)
	 * @return
	 */
	public static boolean buttonJurisdiction(String menuUrl, String type){
		//�ж��Ƿ�ӵ�е�ǰ����˵���Ȩ�ޣ��ڲ�����,��ֹͨ��url���������˵�Ȩ�ޣ�
		/**
		 * ���ݵ���Ĳ˵���xxx.doȥ�˵��е�URLȥƥ�䣬��ƥ�䵽�˴˲˵����ж��Ƿ��д˲˵���Ȩ�ޣ�û�еĻ���ת��404ҳ��
		 * ���ݰ�ťȨ�ޣ���Ȩ��ť(��ǰ��Ĳ˵��ͽ�ɫ�и���ť��Ȩ��ƥ��)
		 */
		//shiro�����session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		Boolean b = true;
		List<Menu> menuList = (List)session.getAttribute(Const.SESSION_allmenuList); //��ȡ�˵��б�
		
		for(int i=0;i<menuList.size();i++){
			for(int j=0;j<menuList.get(i).getSubMenu().size();j++){
				if(menuList.get(i).getSubMenu().get(j).getMENU_URL().split(".do")[0].equals(menuUrl.split(".do")[0])){
					if(!menuList.get(i).getSubMenu().get(j).isHasMenu()){				//�ж����޴˲˵�Ȩ��
						return false;
					}else{																//��ť�ж�
						Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_QX);//��ťȨ��
						String MENU_ID =  menuList.get(i).getSubMenu().get(j).getMENU_ID();
						String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();	//��ȡ��ǰ��¼��loginname
						Boolean isAdmin = "admin".equals(USERNAME);
						if("add".equals(type)){
							return ((RightsHelper.testRights(map.get("adds"), MENU_ID)) || isAdmin);
						}else if("del".equals(type)){
							return ((RightsHelper.testRights(map.get("dels"), MENU_ID)) || isAdmin);
						}else if("edit".equals(type)){
							return ((RightsHelper.testRights(map.get("edits"), MENU_ID)) || isAdmin);
						}else if("cha".equals(type)){
							return ((RightsHelper.testRights(map.get("chas"), MENU_ID)) || isAdmin);
						}
					}
				}
			}
		}
		return true;
	}
	
}
