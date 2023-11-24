package kr.letech.study.mvc01.cmmn.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexController implements IController{
	@Override
	public String service(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		
		return "/WEB-INF/view/hello.jsp";
	}



}
