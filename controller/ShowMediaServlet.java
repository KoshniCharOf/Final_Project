package controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Media;
import model_db.MediaDao;

/**
 * Servlet implementation class ShowMediaServlet
 */
@WebServlet("/ShowMedia")
public class ShowMediaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("mediaId");
		long mediaId = Integer.parseInt(id);
		Media media = null;
		try {
			media = MediaDao.getInstance().getMediaById(mediaId);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String mediaUrl = null;
		
		if(media.getUrl() == null){
			mediaUrl = "default.jpg";
		}else{
			mediaUrl = media.getUrl();
		}
		File myFile = new File(mediaUrl);
		
		try (OutputStream out = response.getOutputStream()) {
		    Path path = myFile.toPath();
		    Files.copy(path, out);
		    out.flush();
		} catch (IOException e) {
		   System.out.println("ops");
		}
	}
	

	
}
