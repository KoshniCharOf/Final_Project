package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Comment;
import model.Media;
import model_db.CommentDao;
import model_db.MediaDao;

/**
 * Servlet implementation class ShowCommentServlet
 */
@WebServlet("/ShowComment")
public class ShowCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("commentId");
		long commentId = Integer.parseInt(id);
		Comment comment = null;
		try {
			comment = CommentDao.getInstance().getCommentById(commentId);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		request.setAttribute("comment", comment);
	}


}
