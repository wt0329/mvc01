package kr.letech.study.mvc01.cmmn.servlet;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet extends HttpServlet{
	
	private Properties prop;
	private Map<String, IController> commandHandlerMap = new HashMap<>();
	
	
	@Override
	public void init() throws ServletException {
			prop = new Properties();
			String configFile = getInitParameter("configFile");
			String Path = getServletContext().getRealPath(configFile);
			System.out.println("======"+configFile);
			// properties 읽기
			try {
				FileReader fis = new FileReader(Path);
				prop.load(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
			Iterator keyIter = prop.keySet().iterator();
			while(keyIter.hasNext()) {
				String command = (String) keyIter.next();
				String handlerClassName = prop.getProperty(command);
				try {
					Class<?> cls = Class.forName(handlerClassName);
					IController handlerInstance = (IController) cls.newInstance();
					
					commandHandlerMap.put(command, handlerInstance);
					
					System.out.println("command=>" + commandHandlerMap);
				} catch(ClassNotFoundException | InstantiationException | IllegalAccessException e) {
					throw new ServletException(e);
				}
			}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String viewName = null;
		String command1 = req.getRequestURI();
		IController handler = commandHandlerMap.get(command1);
		System.out.println("command1"+command1);
		try {
			viewName = handler.service(req, resp);
		} catch(Throwable e) {
			throw new ServletException(e);
		}
			if(viewName !=null) {
				RequestDispatcher dispatcher = req.getRequestDispatcher(viewName);
				dispatcher.forward(req, resp);
			}
	}
}
