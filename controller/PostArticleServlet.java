package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import model.Comment;
import model.Media;
import model_db.ArticleDao;
import model_db.MediaDao;


@WebServlet("/postArticle")
@MultipartConfig
public class PostArticleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//collect data from request
		String title = request.getParameter("title");
		String textContent = request.getParameter("textContent");
		
		String leading = request.getParameter("isLeading");
		
		boolean isLeading = leading!=null;
		
		String category = request.getParameter("category");
		long category_id = Integer.parseInt(category);
		
		Set<Media> mediaFiles = new HashSet<>();
		String url = getUrl(request);
		Media media = new Media(title, url);
		mediaFiles.add(media);
		long mediaId = 0;
		try {
			if(MediaDao.getInstance().getMediaByName(title)==null){
				mediaId = MediaDao.getInstance().addMedia(media);
			}else{
				//if exists in another article
				Media exists = MediaDao.getInstance().getMediaByName(title);
				mediaId = exists.getMedia_id();
			}
			Set<Comment> comments = new HashSet<>();
			// create article 
			Article article = new Article(title, textContent, category_id, LocalDateTime.now(), 0, isLeading, mediaFiles,comments);
			// publishArticle(article)
			long articleId = ArticleDao.getInstance().addArticle(article);
			MediaDao.getInstance().addInArticleMedia(articleId, mediaId);
		} catch (SQLException e) {
			System.out.println("op");
		}
		request.getRequestDispatcher("user.jsp").forward(request, response);
		
	}
	
	private String getUrl(HttpServletRequest req) throws IllegalStateException, IOException, ServletException{
		    Part avatarPart = req.getPart("image");
		    String title = req.getParameter("title");
			InputStream fis = avatarPart.getInputStream();
			String image = Media.IMAGE_URL+title+".jpg";
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
