package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/VoteForComment")
public class VoteServlet extends HttpServlet {
	//private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check if user is logged in for the buttons "thumbs up" and "thumbs down" to be visible 
		//check if user exists not in the comment votes (each comment has HashSet of users
		//add user to the comment voted users set
		//update comment like/dislikes and redirect to the URL where the article is
	}

}
