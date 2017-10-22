package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Article;
import model_db.ArticleDao;

@WebServlet("/categoryArticles")
public class PickCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//list all articles related to the category
		String Id = request.getParameter("category");
		long categoryId = Integer.parseInt(Id);
		
		if(categoryId > 0){
			Set<Article> articles;
			try {
				articles = ArticleDao.getInstance().getArtticlesByCategory(categoryId);
				request.getSession().setAttribute("articles", articles);
				System.out.println("articles.size()"+articles.size());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//reveals submenu with all the subcategories belonging to the category
		request.getRequestDispatcher("categoryArticles.jsp").forward(request, response);
	}


}
