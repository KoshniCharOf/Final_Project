package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Article;
import model.Comment;
import model.Media;
import model.User;
import model_db.ArticleDao;
import model_db.CommentDao;
import model_db.MediaDao;

@WebServlet("/Comment")
public class CommentServlet extends HttpServlet {
	//private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check if user is logged in
		//collect data from request
		User user = (User) request.getSession().getAttribute("user");
		long userId = user.getId();
		Article article = (Article) request.getSession().getAttribute("article");
		long articleId = article.getId();
		
		String content = request.getParameter("comment");
		
		Comment comment = new Comment(userId, articleId, content, 0, 0, LocalDateTime.now(), true, new HashSet<>());
	
		try {
			//insert new comment related to the article where the user is (URL??)
			CommentDao.getInstance().addComment(comment);
		} catch (SQLException e) {
			System.out.println("op");
		}
		//refresh article comments and forward to the article URL
		request.getRequestDispatcher("article.jsp").forward(request, response);
		
		
	}

}