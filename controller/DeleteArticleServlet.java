package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model_db.ArticleDao;

/**
 * Servlet implementation class DeleteArticleServlet
 */
@WebServlet("/deleteArticle")
public class DeleteArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		try {
			ArticleDao.getInstance().removeArticle(title);
		} catch (SQLException e) {
			System.out.println("op");
		}
		request.getRequestDispatcher("user.jsp").forward(request, response);
	}

}
