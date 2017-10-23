package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model_db.CommentDao;


@WebServlet("/Vote")
public class VoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check if user is logged in for the buttons "thumbs up" and "thumbs down" to be visible 
		//check if user exists not in the comment votes (each comment has HashSet of users
		//add user to the comment voted users set
		//update comment like/dislikes and redirect to the URL where the article is
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String vote = req.getParameter("vote");
		String id = req.getParameter("commentId");
		long commentId = Integer.parseInt(id);
		
		
		try {
			if(vote.equals("like")){
				CommentDao.getInstance().likeComment(commentId);
			}else{
				CommentDao.getInstance().dislikeComment(commentId);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(req.getSession().getAttribute("article"));
		req.getRequestDispatcher("article.jsp").forward(req, resp);
		
	}

}
