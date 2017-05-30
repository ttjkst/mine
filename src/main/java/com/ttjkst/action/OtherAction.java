package com.ttjkst.action;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ttjkst.bean.Users;
import com.ttjkst.service.IUserService;
@Controller
public class OtherAction { 
	@Autowired
	public IUserService userService;

	private HttpHeaders responseHeaders = new HttpHeaders();
	
	@RequestMapping(value = "/login")
	public ModelAndView toHome(){
		ModelAndView andView = new ModelAndView("login");
		andView.addObject("go", "/essay/manager");
		return andView;
	}
	
	@ResponseBody
	@RequestMapping("/isLogin")
	public Boolean isLogin(@ModelAttribute("user")Users user,HttpServletRequest request,Model model){
		return user.equals(null)?false:true;
	}
	@ResponseBody
	@RequestMapping("/loginAndNotJump")
	public ResponseEntity<Boolean> login(@RequestParam("username")String username,@RequestParam("password")String password,HttpSession session,
			HttpServletRequest request){
		Users user = this.userService.login(username, password);
		session.setAttribute("user", user);
		return new ResponseEntity<Boolean>(user.equals(null)?false:true, responseHeaders, user.equals(null)?HttpStatus.BAD_GATEWAY:HttpStatus.OK);
	}
	@ResponseBody
	@RequestMapping("/logOut")
	public Boolean logOut(HttpSession session){
		if(session.getAttribute("user").equals("")){
			return false;
		}
		session.setAttribute("user", "");
		return true;
	}
	
	private String getIpAddr(HttpServletRequest request) {   
	    String ip = request.getHeader("x-forwarded-for");   
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	        ip = request.getHeader("Proxy-Client-IP");   
	    }   
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	        ip = request.getHeader("WL-Proxy-Client-IP");   
	    }   
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	        ip = request.getRemoteAddr();   
	    }   
	    return ip;   
	}  
	
	
}
