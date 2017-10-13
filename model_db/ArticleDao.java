package model_db;

import model.Article;
import model.Media;

public final class ArticleDao {

	private static ArticleDao instance;
	
	private ArticleDao(){}
	
	public static synchronized ArticleDao getInstance(){
		if(instance == null){
			instance = new ArticleDao();
		}
		return instance;
	}
	
	public void addMedia(Article article, Media media) {
		article.addMedia(media);
		
	}
	
	
	
	
	
	
}
