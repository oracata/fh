package com.fh.controller.system.role;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.entity.system.Role;
import com.fh.service.system.menu.MenuService;
import com.fh.service.system.role.RoleService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;
/** 
 * �����ƣ�RoleController
 * �����ˣ�FH 
 * ����ʱ�䣺2014��6��30��
 * @version
 */
@Controller
@RequestMapping(value="/role")
public class RoleController extends BaseController {
	
	String menuUrl = "role.do"; //�˵���ַ(Ȩ����)
	@Resource(name="menuService")
	private MenuService menuService;
	@Resource(name="roleService")
	private RoleService roleService;
	
	/**
	 * Ȩ��(��ɾ�Ĳ�)
	 */
	@RequestMapping(value="/qx")
	public ModelAndView qx()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String msg = pd.getString("msg");
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){roleService.updateQx(msg,pd);}
			mv.setViewName("save_result");
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * KȨ��
	 */
	@RequestMapping(value="/kfqx")
	public ModelAndView kfqx()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String msg = pd.getString("msg");
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){roleService.updateKFQx(msg,pd);}
			mv.setViewName("save_result");
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * cȨ��
	 */
	@RequestMapping(value="/gysqxc")
	public ModelAndView gysqxc()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String msg = pd.getString("msg");
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){roleService.gysqxc(msg,pd);}
			mv.setViewName("save_result");
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * �б�
	 */
	@RequestMapping
	public ModelAndView list(Page page)throws Exception{
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			
			String roleId = pd.getString("ROLE_ID");
			if(roleId == null || "".equals(roleId)){
				pd.put("ROLE_ID", "1");
			}
			List<Role> roleList = roleService.listAllRoles();				//�г����в���
			List<Role> roleList_z = roleService.listAllRolesByPId(pd);		//�г��˲��ŵ������¼�
			
			List<PageData> kefuqxlist = roleService.listAllkefu(pd);		//����Ȩ���б�
			List<PageData> gysqxlist = roleService.listAllGysQX(pd);		//�û�Ȩ���б�
			pd = roleService.findObjectById(pd);							//ȡ�õ������
			mv.addObject("pd", pd);
			mv.addObject("kefuqxlist", kefuqxlist);
			mv.addObject("gysqxlist", gysqxlist);
			mv.addObject("roleList", roleList);
			mv.addObject("roleList_z", roleList_z);
			mv.setViewName("system/role/role_list");
			mv.addObject(Const.SESSION_QX,this.getHC());	//��ťȨ��
		
		return mv;
	}
	
	/**
	 * ����ҳ��
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd(Page page){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			mv.setViewName("system/role/role_add");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * ����������Ϣ
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView add()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			
			String parent_id = pd.getString("PARENT_ID");		//�����ɫid
			pd.put("ROLE_ID", parent_id);			
			if("0".equals(parent_id)){
				pd.put("RIGHTS", "");
			}else{
				String rights = roleService.findObjectById(pd).getString("RIGHTS");
				pd.put("RIGHTS", (null == rights)?"":rights);
			}

			pd.put("QX_ID", "");
			
			String UUID = this.get32UUID();
			
				pd.put("GL_ID", UUID);
				pd.put("FX_QX", 0);				//����Ȩ��
				pd.put("FW_QX", 0);				//����Ȩ��
				pd.put("QX1", 0);				//����Ȩ��
				pd.put("QX2", 0);				//��ƷȨ��
				pd.put("QX3", 0);				//Ԥ��Ȩ��
				pd.put("QX4", 0);				//Ԥ��Ȩ��
				if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){roleService.saveKeFu(pd);}//���浽KȨ�ޱ�
				
				pd.put("U_ID", UUID);
				pd.put("C1", 0);				//ÿ�շ�������
				pd.put("C2", 0);
				pd.put("C3", 0);
				pd.put("C4", 0);
				pd.put("Q1", 0);				//Ȩ��1
				pd.put("Q2", 0);				//Ȩ��2
				pd.put("Q3", 0);
				pd.put("Q4", 0);
				if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){roleService.saveGYSQX(pd);}//���浽GȨ�ޱ�
				pd.put("QX_ID", UUID);
			
			pd.put("ROLE_ID", UUID);
			pd.put("ADD_QX", "0");
			pd.put("DEL_QX", "0");
			pd.put("EDIT_QX", "0");
			pd.put("CHA_QX", "0");
			if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){roleService.add(pd);}
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * ����༭
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit( String ROLE_ID )throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("ROLE_ID", ROLE_ID);
			pd = roleService.findObjectById(pd);
			mv.setViewName("system/role/role_edit");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * �༭
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){pd = roleService.edit(pd);}
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * �����ɫ�˵���Ȩҳ��
	 */
	@RequestMapping(value="/auth")
	public String auth(@RequestParam String ROLE_ID,Model model)throws Exception{
		
		try{
			List<Menu> menuList = menuService.listAllMenu();
			Role role = roleService.getRoleById(ROLE_ID);
			String roleRights = role.getRIGHTS();
			if(Tools.notEmpty(roleRights)){
				for(Menu menu : menuList){
					menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMENU_ID()));
					if(menu.isHasMenu()){
						List<Menu> subMenuList = menu.getSubMenu();
						for(Menu sub : subMenuList){
							sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
						}
					}
				}
			}
			JSONArray arr = JSONArray.fromObject(menuList);
			String json = arr.toString();
			json = json.replaceAll("MENU_ID", "id").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodes", json);
			model.addAttribute("roleId", ROLE_ID);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return "authorization";
	}
	
	/**
	 * �����ɫ��ť��Ȩҳ��
	 */
	@RequestMapping(value="/button")
	public ModelAndView button(@RequestParam String ROLE_ID,@RequestParam String msg,Model model)throws Exception{
		ModelAndView mv = this.getModelAndView();
		try{
			List<Menu> menuList = menuService.listAllMenu();
			Role role = roleService.getRoleById(ROLE_ID);
			
			String roleRights = "";
			if("add_qx".equals(msg)){
				roleRights = role.getADD_QX();
			}else if("del_qx".equals(msg)){
				roleRights = role.getDEL_QX();
			}else if("edit_qx".equals(msg)){
				roleRights = role.getEDIT_QX();
			}else if("cha_qx".equals(msg)){
				roleRights = role.getCHA_QX();
			}
			
			if(Tools.notEmpty(roleRights)){
				for(Menu menu : menuList){
					menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMENU_ID()));
					if(menu.isHasMenu()){
						List<Menu> subMenuList = menu.getSubMenu();
						for(Menu sub : subMenuList){
							sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
						}
					}
				}
			}
			JSONArray arr = JSONArray.fromObject(menuList);
			String json = arr.toString();
			//System.out.println(json);
			json = json.replaceAll("MENU_ID", "id").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
			mv.addObject("zTreeNodes", json);
			mv.addObject("roleId", ROLE_ID);
			mv.addObject("msg", msg);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.setViewName("system/role/role_button");
		return mv;
	}
	
	/**
	 * �����ɫ�˵�Ȩ��
	 */
	@RequestMapping(value="/auth/save")
	public void saveAuth(@RequestParam String ROLE_ID,@RequestParam String menuIds,PrintWriter out)throws Exception{
		PageData pd = new PageData();
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
				if(null != menuIds && !"".equals(menuIds.trim())){
					BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
					Role role = roleService.getRoleById(ROLE_ID);
					role.setRIGHTS(rights.toString());
					roleService.updateRoleRights(role);
					pd.put("rights",rights.toString());
				}else{
					Role role = new Role();
					role.setRIGHTS("");
					role.setROLE_ID(ROLE_ID);
					roleService.updateRoleRights(role);
					pd.put("rights","");
				}
					
					pd.put("roleId", ROLE_ID);
					roleService.setAllRights(pd);
			}
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	/**
	 * �����ɫ��ťȨ��
	 */
	@RequestMapping(value="/roleButton/save")
	public void orleButton(@RequestParam String ROLE_ID,@RequestParam String menuIds,@RequestParam String msg,PrintWriter out)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
				if(null != menuIds && !"".equals(menuIds.trim())){
					BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
					pd.put("value",rights.toString());
				}else{
					pd.put("value","");
				}
				pd.put("ROLE_ID", ROLE_ID);
				roleService.updateQx(msg,pd);
			}
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	/**
	 * ɾ��
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object deleteRole(@RequestParam String ROLE_ID)throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		String errInfo = "";
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
				pd.put("ROLE_ID", ROLE_ID);
				List<Role> roleList_z = roleService.listAllRolesByPId(pd);		//�г��˲��ŵ������¼�
				if(roleList_z.size() > 0){
					errInfo = "false";
				}else{
					
					List<PageData> userlist = roleService.listAllUByRid(pd);
					List<PageData> appuserlist = roleService.listAllAppUByRid(pd);
					if(userlist.size() > 0 || appuserlist.size() > 0){
						errInfo = "false2";
					}else{
					roleService.deleteRoleById(ROLE_ID);
					roleService.deleteKeFuById(ROLE_ID);
					roleService.deleteGById(ROLE_ID);
					errInfo = "success";
					}
				}
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/* ===============================Ȩ��================================== */
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro�����session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================Ȩ��================================== */
	

}
