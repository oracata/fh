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
  //  String menuUrl = "ai/jupiter.do"; //�˵���ַ(Ȩ����)



    @RequestMapping("/jupiter")
    public ModelAndView getSearch(){
        //����ط�����Ȩ��

        ModelAndView mav = new ModelAndView("ai/jupiter");


        return mav;
    }

}
