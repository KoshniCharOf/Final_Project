package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.Article;
import model.Media;
import model.User;
import model_db.ArticleDao;
import model_db.MediaDao;
import model_db.UserDao;


@WebServlet("/postArticle")
@MultipartConfig
public class PostArticleServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//collect data from request
		String title = request.getParameter("title");
		String textContent = request.getParameter("textContent");
		
		String leading = request.getParameter("isLeading");
		System.out.println(leading);
		boolean isLeading = leading!=null;
		
		long category_id = 123;
		
		Set<Media> mediaFiles = new HashSet<>();
		String url = getUrl(request);
		Media media = new Media(title, url);
		mediaFiles.add(media);
		try {
			if(MediaDao.getInstance().getMediaByName(title)==null){
				MediaDao.getInstance().addMedia(media);
			}
			
			// create article 
			Article article = new Article(title, textContent, category_id, isLeading, mediaFiles);
			// publishArticle(article)
			ArticleDao.getInstance().addArticle(article);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private String getUrl(HttpServletRequest req) throws IllegalStateException, IOException, ServletException{
		  Part avatarPart = req.getPart("image");

			InputStream fis = avatarPart.getInputStream();
			String image = Media.IMAGE_URL+".jpg";
			File myFile = new File(image);
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
			return image;
	}
	

}
