package com.fh.controller.report;

import com.fh.controller.base.BaseController;
import com.fh.entity.report.ReportDay;
import com.fh.entity.report.Search;
import com.fh.entity.system.User;
import com.fh.service.report.ReportService;
import com.fh.service.system.user.UserService;
import com.fh.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/search")
public class ReportSearchController  extends BaseController {
    @Resource(name="reportService")
    private ReportService reportService;
    String menuUrl = "search/listsearchno.do"; //�˵���ַ(Ȩ����)



    @RequestMapping("/searchno")
    public ModelAndView getSearch(Model model, @ModelAttribute Search v_search){
        //����ط�����Ȩ��

        //��ʼ����ѯ����
        if(v_search.getBegin_date()==null && v_search.getEnd_date()==null) {
            v_search.setBegin_date(DateUtil.getTimeDay(0));
            v_search.setEnd_date(DateUtil.getTimeDay(0));

        }
        ModelAndView mav = new ModelAndView("report/searchno");
        mav.addObject("search_con", v_search);

        return mav;
    }

    @ResponseBody
    @RequestMapping(value="/listsearchno")
    public DataGridView selectSearchno(Search search) throws Exception{


        //     mv.addObject(Const.SESSION_QX,this.getHC());	//��ťȨ��

        return      reportService.listSearchno(search);

    }



    /*
     * �����û���Ϣ��EXCEL
     * @return
     */
    @RequestMapping(value="/excel")
    public ModelAndView exportExcel(Search search){
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try{
            if(Jurisdiction.buttonJurisdiction(menuUrl, "cha")){
                //��������===
                String USERNAME = pd.getString("USERNAME");
                if(null != USERNAME && !"".equals(USERNAME)){
                    USERNAME = USERNAME.trim();
                    pd.put("USERNAME", USERNAME);
                    System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXX"+USERNAME);
                }
                String lastLoginStart = pd.getString("lastLoginStart");
                String lastLoginEnd = pd.getString("lastLoginEnd");
                if(lastLoginStart != null && !"".equals(lastLoginStart)){
                    lastLoginStart = lastLoginStart+" 00:00:00";
                    pd.put("lastLoginStart", lastLoginStart);
                }
                if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
                    lastLoginEnd = lastLoginEnd+" 00:00:00";
                    pd.put("lastLoginEnd", lastLoginEnd);
                }
                //��������===

                Map<String,Object> dataMap = new HashMap<String,Object>();
                List<String> titles = new ArrayList<String>();

                titles.add("�ͻ�"); 		//1
                titles.add("������");  		//2
                titles.add("��������");			//3


                dataMap.put("titles", titles);

                DataGridView listsearchno_grid=  reportService.listSearchno(search);
                List<Search> listsearchno=( List<Search>)listsearchno_grid.getRows();
                List<PageData> varList = new ArrayList<PageData>();
                for(int i=0;i<listsearchno.size();i++){
                    PageData vpd = new PageData();


                    vpd.put("var1", listsearchno.get(i).getWldwname());		//1
                    vpd.put("var2", listsearchno.get(i).getSearch_key());		//2
                    vpd.put("var3", listsearchno.get(i).getSearch_num() );			//3

                    varList.add(vpd);
                }
                dataMap.put("varList", varList);
                ObjectExcelView erv = new ObjectExcelView();					//ִ��excel����
                mv = new ModelAndView(erv,dataMap);
            }
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

}
