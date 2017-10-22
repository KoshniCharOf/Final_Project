package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Article;
import model_db.ArticleDao;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/Search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String search = request.getParameter("search");
		
		String category = request.getParameter("category");
		
		Set<Article> result = new HashSet<>();
		
		try {
			result.addAll(ArticleDao.getInstance().getArtticlesByTitle(search));
			if(category!=null){
				long categoryId = Integer.parseInt(category);
				Set<Article> byCategory = ArticleDao.getInstance().getArtticlesByCategory(categoryId);
				result.retainAll(byCategory);
			}
		} catch (SQLException e) {
			System.out.println("op");
		}
		//TODO LOW SCOPE
		request.getSession().setAttribute("search", result);
		request.getRequestDispatcher("searchResult.jsp").forward(request, response);
	}

	

}
