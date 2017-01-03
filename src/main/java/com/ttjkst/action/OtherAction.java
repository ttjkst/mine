package com.ttjkst.action;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ttjkst.bean.Users;
import com.ttjkst.service.IUserService;
@Controller
@SessionAttributes("user")
public class OtherAction{
	@Autowired
	public IUserService userService;

	@ModelAttribute("user")
	public Users initUser(){
		System.out.println("init user");
		return new Users();
	}
	@ResponseBody
	@RequestMapping("/isLogin")
	public Boolean isLogin(@ModelAttribute("user")Users user,HttpServletRequest request,Model model){
		return user.equals(null)?false:true;
	}
	@ResponseBody
	@RequestMapping("/loginAndNotJump")
	public Boolean login(@RequestParam("username")String username,@RequestParam("password")String password,Model model){
		Users user = this.userService.login(username, password);
		model.addAttribute("user", user);
		return user.equals(null)?false:true;
	}
	@ResponseBody
	@RequestMapping("/logOut")
	public Boolean logOut(Model model){
		model.addAttribute("user", new Users());
		return true;
	}
	
}
