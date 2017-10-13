package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	//private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check if user is logged
		if(request.getSession().getAttribute("logged")!=null) {
			//update session
			request.getSession().invalidate();
			//redirect to main page OR PREVIOUS??
			response.sendRedirect("main");
		}
		//update session
		//redirect to main page OR PREVIOUS??
	}

}
