package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;


@WebServlet("/User")
public class PickUserServlet extends HttpServlet {
	//private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//show user data (User name and if not null - date of birth, date of registration
		//and list of his last articles that he has commented)
		//get user from session
		//TODO FILL SOME DATA 
		
		//check if admin
		//forward to user page
		request.getRequestDispatcher("user.jsp").forward(request, response);
	}


}
