package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model_db.UserDao;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	//private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check for login credentials
		String username = request.getParameter("username");
		String password = request.getParameter("password");//TODO  hash password
		//check if user exists in db
		if(!request.isRequestedSessionIdValid()) {
			try {
				if(UserDao.getInstance().existsUser(username, password)) {
					User user = UserDao.getInstance().getUser(username);
					//update session
					request.getSession().setAttribute("logged", user);
					//request.getSession().getAttribute("logged"); get user back
					//redirect to main page OR PREVIOUS??
					//TODO redirect to previous: -> request.getSession().setAttribute("last article", user);
					response.sendRedirect("home");
					
				}else {
					//redirect to PREVIOUS??
					response.getWriter().println("invalid username or password");
					response.sendRedirect("login");
				}
			} catch (SQLException e) {
				response.sendRedirect("Error");
			}
		}
		//update session
		//redirect to main page OR PREVIOUS??
	}

}
