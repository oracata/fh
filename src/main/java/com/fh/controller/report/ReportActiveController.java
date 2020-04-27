package com.fh.controller.report;

import com.fh.controller.base.BaseController;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;

@Controller
@RequestMapping(value="/report")
public class ReportActiveController  extends BaseController {


    @RequestMapping(value="/test1")
    public ModelAndView selectTest1(PrintWriter out) throws Exception{

        ModelAndView mv = this.getModelAndView();
        mv.setViewName("report/test4");
        return mv;
    }

}
