package com.fh.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
/**
 * 
* �����ƣ�MyExceptionResolver.java
* �������� 
* @author FH
* ���ߵ�λ�� 
* ��ϵ��ʽ��QQ313596790
* @version 1.0
 */
public class MyExceptionResolver implements HandlerExceptionResolver{

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// TODO Auto-generated method stub
		System.out.println("==============�쳣��ʼ=============");
		ex.printStackTrace();
		System.out.println("==============�쳣����=============");
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception", ex.toString().replaceAll("\n", "<br/>"));
		return mv;
	}

}
