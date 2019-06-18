package com.huai.shiro.web;

import com.huai.shiro.dao.IUserDAO;
import com.huai.shiro.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(name = "loginServlet", urlPatterns = "/login")
public class LoginServlet  extends HttpServlet {
	@Autowired
	private IUserDAO userDAO;
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
    }

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String username =  req.getParameter("username");
		String password =  req.getParameter("password");

		if("zhangsan".equals(username)&&"666".equals(password)){
			req.setAttribute("userName",username);
			//登陆成功
			req.getRequestDispatcher("/main").forward(req, resp);
		}else{
			if(username!=null)
				req.setAttribute("errorMsg", "账号密码有误");
			req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
		}
	}
	
}
