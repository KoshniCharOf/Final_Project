package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Category;
import model.User;
import model_db.CategoryDao;
import model_db.UserDao;


@WebServlet("/User")
public class PickUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//get user from session
		User user = (User) request.getSession().getAttribute("user");
		//update user
		Set<Category> categories = null;
			try {
				
				if(user.getUsername()!=null){
					String username = user.getUsername();
					user = UserDao.getInstance().getUser(username);
					request.getSession().setAttribute("user", user);
					categories = CategoryDao.getInstance().getAllCategories();
					
				}
				
			} catch (SQLException e) {
				System.out.println("op"+e.getMessage());
			}
		request.getServletContext().setAttribute("categories", categories);
		
		//forward to user page
		request.getRequestDispatcher("user.jsp").forward(request, response);
	}


}
