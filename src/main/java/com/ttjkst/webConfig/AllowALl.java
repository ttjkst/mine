package com.ttjkst.webConfig;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AllowALl implements HandlerInterceptor  {

	private List<Method> methods = new ArrayList<>();
	{
	
	}
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		arg1.setHeader("Access-Control-Allow-Origin", "*");
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		System.out.println("sssss");
		arg1.setHeader("Access-Control-Allow-Origin", "*");
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		String[] methodNames = {"public org.springframework.http.ResponseEntity com.ttjkst.action.EssayAction.delete(java.lang.String)",
								"public org.springframework.http.ResponseEntity com.ttjkst.action.EssayAction.saveEassy(java.lang.String,java.lang.String,java.lang.String,java.lang.String)",
								"public org.springframework.web.servlet.ModelAndView com.ttjkst.action.EssayAction.gotoManger()"			
				};
		
		System.out.println(arg2.getClass());
		if(arg2 instanceof HandlerMethod){
			HandlerMethod handlerMethod = (HandlerMethod)arg2;
			String string = handlerMethod.getMethod().toString();
			if(Arrays.asList(methodNames).contains(string)){
				HttpSession session = arg0.getSession();
				if(session.getAttribute("user")==null||session.getAttribute("user")==""){
				arg1.sendRedirect("/blog/login");
				return false;
				}
			}
			
		}
		return true;
	}
	
}

//public org.springframework.web.servlet.ModelAndView com.ttjkst.action.EssayAction.gotoManger()
//public org.springframework.http.ResponseEntity com.ttjkst.action.EssayAction.saveEassy(java.lang.String,java.lang.String,java.lang.String,java.lang.String)
//public org.springframework.http.ResponseEntity com.ttjkst.action.EssayAction.delete(java.lang.String)