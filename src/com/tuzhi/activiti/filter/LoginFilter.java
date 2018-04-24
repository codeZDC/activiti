package com.tuzhi.activiti.filter;

import java.awt.image.RescaleOp;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.tuzhi.activiti.domain.User;
import com.tuzhi.activiti.util.Contants;

/**
 * 登录过滤器
 * @author codeZ
 * @date 2018年4月20日 下午4:12:06
 * 
 */
public class LoginFilter implements Filter {

	private FilterConfig config;
	
	@Override
	public void destroy() {
		System.out.println("loginFilter 即将销毁!");
	}

	@Override
	public void doFilter(ServletRequest req , ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		//不需要过滤的内容
		String[] excludes = config.getInitParameter("excludeUrl").split(",");
		if(isExcludes(excludes,request.getRequestURI())){
			chain.doFilter(request, response);
			return;
		}
		
		User user =
				(User)request.getSession().getAttribute(Contants.SESSION_USER);
		if(user==null)
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		else
			chain.doFilter(request, response);
	}
	
	//是否是过滤url
	private boolean isExcludes(String[] excludes, String url) {
		for (String string : excludes) {
			if(url.contains(string))
			return true;
		}
		return false;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		config = arg0;
		System.out.println("loginFilter 初始化成功!");
	}

}
