package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {
	//private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check if user is logged
		if(request.getSession().getAttribute("user")!=null) {
			//update session
			request.getSession().invalidate();
			//redirect to main page OR PREVIOUS??
			
		}
		request.getRequestDispatcher("index.jsp").forward(request, response);
		//update session
		//redirect to main page OR PREVIOUS??
	}

}
