package model_db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import model.Article;

public final class ArticleDao {

	private static ArticleDao instance;
	private ConcurrentHashMap<Long, Article> articles;
	
	private ArticleDao(){
		this.articles = new ConcurrentHashMap<>();
	}
	
	public static synchronized ArticleDao getInstance(){
		if(instance == null){
			instance = new ArticleDao();
		}
		return instance;
	}

	public void addArticle(Article article) throws SQLException{
		
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO articles (category_id, title, content, datetime, impressions, isLeading) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, article.getTitle());
		ps.setString(2, article.getTextContent());
		ps.setTimestamp(3, Timestamp.valueOf(article.getCreated()));//https://www.youtube.com/watch?v=CEjU9KVABao
		ps.setLong(4, article.getImpressions());
		ps.setBoolean(5, article.isLeading());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		long article_id = rs.getLong(1);
		article.setId(article_id);
		articles.put(article_id, article);
	}
	
	public  boolean removeArticle(long article_id) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("DELETE FROM articles a WHERE a.article_id = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, article_id);
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		this.articles.remove(article_id);
		return rs.next();
	}
	
	
	public Article getArticleById(long article_id) throws SQLException{
		
		String title = articles.get(article_id).getTitle();
		String textContent = articles.get(article_id).getTextContent();
		long category_id = articles.get(article_id).getCategory_id();
		boolean isLeading = articles.get(article_id).isLeading();
		Set mediaFiles = articles.get(article_id).getMediaFiles();
		return new Article(article_id, title, textContent, category_id, isLeading, mediaFiles);
	}
	
}
