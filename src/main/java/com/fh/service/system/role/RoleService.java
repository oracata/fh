package com.fh.service.system.role;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.Role;
import com.fh.util.PageData;

@Service("roleService")
public class RoleService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public List<Role> listAllERRoles() throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllERRoles", null);
		
	}
	
	
	public List<Role> listAllappERRoles() throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllappERRoles", null);
		
	}
	
	
	public List<Role> listAllRoles() throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllRoles", null);
		
	}
	
	//ͨ����ǰ��¼�õĽ�ɫid��ȡ����Ȩ������
	public PageData findGLbyrid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RoleMapper.findGLbyrid", pd);
	}
	
	//ͨ����ǰ��¼�õĽ�ɫid��ȡ�û�Ȩ������
	public PageData findYHbyrid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RoleMapper.findYHbyrid", pd);
	}
	
	//�г��˽�ɫ�µ������û�
	public List<PageData> listAllUByRid(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RoleMapper.listAllUByRid", pd);
		
	}
	
	//�г��˽�ɫ�µ����л�Ա
	public List<PageData> listAllAppUByRid(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RoleMapper.listAllAppUByRid", pd);
		
	}
	
	/**
	 * �г��˲��ŵ������¼�
	 */
	public List<Role> listAllRolesByPId(PageData pd) throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllRolesByPId", pd);
		
	}
	
	//�г�KȨ�ޱ�������� 
	public List<PageData> listAllkefu(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RoleMapper.listAllkefu", pd);
	}
	
	//�г�GȨ�ޱ�������� 
	public List<PageData> listAllGysQX(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RoleMapper.listAllGysQX", pd);
	}
	
	//ɾ��KȨ�ޱ����Ӧ������
	public void deleteKeFuById(String ROLE_ID) throws Exception {
		dao.delete("RoleMapper.deleteKeFuById", ROLE_ID);
	}
	
	//ɾ��GȨ�ޱ����Ӧ������
	public void deleteGById(String ROLE_ID) throws Exception {
		dao.delete("RoleMapper.deleteGById", ROLE_ID);
	}
	
	public void deleteRoleById(String ROLE_ID) throws Exception {
		dao.delete("RoleMapper.deleteRoleById", ROLE_ID);
		
	}

	public Role getRoleById(String roleId) throws Exception {
		return (Role) dao.findForObject("RoleMapper.getRoleById", roleId);
		
	}

	public void updateRoleRights(Role role) throws Exception {
		dao.update("RoleMapper.updateRoleRights", role);
	}
	
	/**
	 * Ȩ��(��ɾ�Ĳ�)
	 */
	public void updateQx(String msg,PageData pd) throws Exception {
		dao.update("RoleMapper."+msg, pd);
	}
	
	/**
	 * �ͷ�Ȩ��
	 */
	public void updateKFQx(String msg,PageData pd) throws Exception {
		dao.update("RoleMapper."+msg, pd);
	}
	
	/**
	 * GcȨ��
	 */
	public void gysqxc(String msg,PageData pd) throws Exception {
		dao.update("RoleMapper."+msg, pd);
	}
	
	/**
	 * ��ȫ����ְλ�Ӳ˵�Ȩ��
	 */
	public void setAllRights(PageData pd) throws Exception {
		dao.update("RoleMapper.setAllRights", pd);
		
	}
	
	/**
	 * ���
	 */
	public void add(PageData pd) throws Exception {
		dao.findForList("RoleMapper.insert", pd);
	}
	
	/**
	 * ����ͷ�Ȩ��
	 */
	public void saveKeFu(PageData pd) throws Exception {
		dao.findForList("RoleMapper.saveKeFu", pd);
	}
	
	/**
	 * ����GȨ��
	 */
	public void saveGYSQX(PageData pd) throws Exception {
		dao.findForList("RoleMapper.saveGYSQX", pd);
	}
	
	/**
	 * ͨ��id����
	 */
	public PageData findObjectById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("RoleMapper.findObjectById", pd);
	}
	
	/**
	 * �༭��ɫ
	 */
	public PageData edit(PageData pd) throws Exception {
		return (PageData)dao.findForObject("RoleMapper.edit", pd);
	}

}
