package com.tuzhi.activiti.controller;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuzhi.activiti.base.BaseController;
import com.tuzhi.activiti.domain.User;
import com.tuzhi.activiti.mapper.IUserMapper;
import com.tuzhi.activiti.util.Contants;

/**
 * @author codeZ
 * @date 2018年4月20日 下午5:00:33
 *  登录控制器
 */

@Controller
public class LoginController extends BaseController {

	@Autowired
	private IUserMapper userMapper;
	
	@RequestMapping("login")
	public String login(User user,Model model){
		user = userMapper.login(user);
		if(user==null){
			model.addAttribute("msg","用户名或密码错误");
			return "redirect:/login.jsp";
		}
		session.setAttribute(Contants.SESSION_USER, user);
		return "redirect:/index.jsp";
	}
	
}
