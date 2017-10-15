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


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check for register credentials
		//TODO validate email in 3 layer java db html
		
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//validations
		if(!isValidEmailAddress(email)) {
			response.getWriter().println("Not valid email");
			return;
		}
		
		//	TODO STRONGER PASS
		try {
			if(UserDao.getInstance().existsUser(username, password)) {
				response.getWriter().println("redirecting to login page");
				response.sendRedirect("login");
			}
		} catch (SQLException e1) {
			response.sendRedirect("error page");
		}
		
		User user = new User(username, password, email);
		
		try {
			//check if username and e-mail exists
			if(UserDao.getInstance().checkEmail(email)) {
				response.getWriter().append("email already used");
				return;
			}
			if(UserDao.getInstance().checkUsername(username)) {
				response.getWriter().append("username already used");
				return;
			}
			//TODO HASH PASSWORD
			//insert user in db
			UserDao.getInstance().insertUser(user);
			//update session
			request.getSession().setAttribute("logged", user);
			//redirect to main html
			response.sendRedirect("http://localhost:8080/FinalProject/");
			
		} catch (SQLException e) {
			response.sendRedirect("error page"+e.getMessage());
		}
		
	}
	
	public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        email.matches(ePattern);
        return m.matches();
 }

}
