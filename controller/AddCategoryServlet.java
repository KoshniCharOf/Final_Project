package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Category;
import model_db.CategoryDao;

/**
 * Servlet implementation class AddCategory
 */
@WebServlet("/AddCategory")
public class AddCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("category");
		try {
			if(!CategoryDao.getInstance().existsCategory(name)){
				CategoryDao.getInstance().addCategory(new Category(name));
			}
		} catch (SQLException e) {
			System.out.println("op");
		}
		request.getRequestDispatcher("user.jsp").forward(request, response);
	}

}
