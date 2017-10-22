package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Article;
import model_db.ArticleDao;


@WebServlet("/pickArticle")
public class PickArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//review article content and all the media related to the article
		String Id = request.getParameter("articleId");
		long articleId = Integer.parseInt(Id);
		System.out.println("articleId "+articleId);
		//show date and time of article creation
		//show date and time of last edit
		//show article sub category
		Article article = null;
		try {
			article = ArticleDao.getInstance().getArtticleById(articleId);
			
			//increment immpressions
			ArticleDao.getInstance().incremenImpression(articleId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO THINK ABOUT SCOPE AGAIN
		request.getSession().setAttribute("article", article);
		request.getRequestDispatcher("article.jsp").forward(request, response);
		//BELOW offers list with links to the last few (3) articles related to the article tags
		//AT RIGHT offers a column with the last articles belonging to the sub category
		//list comments related to the article
		//show buttons to sort comments by (1: Date/Time DESC; 2: Date/Time ASC; 3: Most liked DESC; 4: Most disliked DESC; 5: Absolute rating (Likes-Dislikes))
		//shows number of previews(impressions)
		//FB comment plug in??
	}



}
