package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Article;
import model_db.ArticleDao;

/**
 * Servlet implementation class Top5Servlet
 */
@WebServlet("/Top5")
public class Top5Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	enum Criteria {IMPRESSIONS, COMMENTED, LEADING}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//list top 5 articles on selected condition
		String sort = request.getParameter("sort");
		System.out.println(sort);
		Set<Article> articles = new TreeSet<Article>((o1, o2) -> 
				(int) (o2.getImpressions() - o1.getImpressions()));
		
		if(sort.equals("impressions")){
			try {
				articles.addAll(ArticleDao.getInstance().getTop5ByImpressions());
				request.getSession().setAttribute("articles", articles);
				
			} catch (SQLException e) {
				System.out.println("op");
			}
		}
		for (Article article : articles) {
			System.out.println(article.getImpressions()+" ");
		}
		//reveals submenu with all the subcategories belonging to the category
		request.getRequestDispatcher("top5.jsp").forward(request, response);
	}

}
