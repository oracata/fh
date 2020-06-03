package com.fh.controller.ai;

import com.fh.entity.report.Search;
import com.fh.service.report.ReportService;
import com.fh.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
@Controller
@RequestMapping(value="/ai")
public class JupiterController {

  //  @Resource(name="aiService")
 //   private AiService aiService;
  //  String menuUrl = "ai/jupiter.do"; //菜单地址(权限用)



    @RequestMapping("/jupiter")
    public ModelAndView getSearch(){
        //这个地方设置权限

        ModelAndView mav = new ModelAndView("ai/jupiter");


        return mav;
    }

}
