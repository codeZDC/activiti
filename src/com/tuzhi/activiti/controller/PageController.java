package com.tuzhi.activiti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author codeZ
 * @date 2018年4月23日 上午11:52:42
 * 
 */

@Controller
public class PageController {

	@RequestMapping("page/{module}")
	public String page(@PathVariable("module") String module){
		return module; 
	}
	@RequestMapping("page/{module}/{param}")
	public String page(@PathVariable("module") String module,@PathVariable("param") String param
			,Model model){
		model.addAttribute("taskId", param);
		return module; 
	}
}
