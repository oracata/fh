package com.fh.controller.report;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.system.user.UserService;
import com.fh.util.Const;
import com.fh.util.DataGridView;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/report")
public class ReportActiveController  extends BaseController {
    @Resource(name="userService")
    private UserService userService;



    @RequestMapping("/test1")
    public ModelAndView getKhjTask(){
        //这个地方设置权限

        ModelAndView mav = new ModelAndView("report/test1");

        return mav;
    }

    @ResponseBody
    @RequestMapping(value="/listtest1")
    public DataGridView selectTest1(User user) throws Exception{

   //     mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        return      userService.listUser(user);

    }





    @RequestMapping(value="/test2")
    public ModelAndView selectTest2(User user) throws Exception{
        ModelAndView mv = this.getModelAndView();



        mv.setViewName("report/test2");

        return mv;

    }

    /* ===============================权限================================== */
    public Map<String, String> getHC(){
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
    }
    /* ===============================权限================================== */

}
