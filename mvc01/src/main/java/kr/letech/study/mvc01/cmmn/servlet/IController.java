package kr.letech.study.mvc01.cmmn.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IController {
	public String service(HttpServletRequest req, HttpServletResponse resp)
		throws Exception;
}
