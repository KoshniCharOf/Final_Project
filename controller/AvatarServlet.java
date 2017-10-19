package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.User;
import model_db.UserDao;
import src.controller.RegisterServlet;

/**
 * Servlet implementation class AvatarServlet
 */
@WebServlet("/avatarUpload")
@MultipartConfig
public class AvatarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	  
	    Part avatarPart = req.getPart("avatar");
	    User user = (User) req.getSession().getAttribute("user");
		InputStream fis = avatarPart.getInputStream();
		String avatar = User.AVATAR_URL+user.getUsername()+".jpg";
		File myFile = new File(avatar);
		if(!myFile.exists()){
			myFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(myFile);
		int b = fis.read();
		while(b != -1){
			fos.write(b);
			b = fis.read();
		}
		fis.close();
		fos.close();
		// UPDATE IN DB
		try {
			UserDao.getInstance().updateAvatar(avatar, user.getUsername());
		} catch (SQLException e) {
			System.out.println("opa");
		}
	}
   
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = (User)request.getSession().getAttribute("user");
		//String avatar = u.getAvatarUrl();
		if(avatar == null){
			avatar = "default.jpg";
		}
		File myFile = new File(RegisterServlet.AVATAR_URL+avatar);
		
		try (OutputStream out = response.getOutputStream()) {
		    Path path = myFile.toPath();
		    Files.copy(path, out);
		    out.flush();
		} catch (IOException e) {
		   System.out.println("ops");
		}
	}
}
