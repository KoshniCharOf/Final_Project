package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
	//private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//get user from session
		//update user

		Map<Long, Category> categories = CategoryDao.getInstance().getCategories();
		request.getSession().setAttribute("categories", categories);
		for (Long i : categories.keySet()) {
			System.out.println(categories.get(i).getName());
		}
		//forward to user page
		request.getRequestDispatcher("user.jsp").forward(request, response);
	}


}
