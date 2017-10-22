package model_db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import model.Article;
import model.Media;

public final class ArticleDao {

	private static ArticleDao instance;
	
	private ArticleDao(){
		
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
		ps.setLong(1, article.getCategory_id());
		ps.setString(2, article.getTitle());
		ps.setString(3, article.getTextContent());
		ps.setTimestamp(4, Timestamp.valueOf(article.getCreated()));//https://www.youtube.com/watch?v=CEjU9KVABao
		ps.setLong(5, article.getImpressions());
		ps.setBoolean(6, article.isLeading());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		long article_id = rs.getLong(1);
		article.setId(article_id);
		
	}
	
	public boolean removeArticle(String title) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("delete from articles  where articles.title = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, title);
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		//this.articles.remove(article_id);
		return rs.next();
	}
	
	
//	public Article getArticleById(long article_id) throws SQLException{
//		
//		String title = articles.get(article_id).getTitle();
//		String textContent = articles.get(article_id).getTextContent();
//		long category_id = articles.get(article_id).getCategory_id();
//		boolean isLeading = articles.get(article_id).isLeading();
//		Set mediaFiles = articles.get(article_id).getMediaFiles();
//		return new Article(article_id, title, textContent, category_id, isLeading, mediaFiles);
//	}
	
	//get all artticles by category_id
	public Set<Article> getArtticlesByCategory(long categoryId) throws SQLException{
		Set<Article> articles = new HashSet<Article>();
		Connection con  = DBManager.getInstance().getConnection();
		//String sql = "SELECT a.article_id, a.category_id, a.title, a.content, a.datetime, a.impressions, a.isLeading , m.media_id, am.article_id FROM  articles as a  JOIN article_media as am ON a.article_id  JOIN media as m ON m.media_id WHERE a.category_id = ? and am.media_id = m.media_id";
		PreparedStatement ps = con.prepareStatement("SELECT a.article_id, a.category_id, a.title, a.content, a.datetime, a.impressions, a.isLeading , m.media_id, am.article_id FROM  articles as a  JOIN article_media as am ON a.article_id  JOIN media as m ON m.media_id WHERE a.category_id= ? and am.media_id = m.media_id", Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, categoryId);
		ps.executeQuery();
		ResultSet rs = ps.getGeneratedKeys();
System.out.println(rs.next()+" cat "+categoryId);
		while (rs.next()) {
			
			long articleId = rs.getLong(1);
			//System.out.println("articleId "+articleId);
			String title = rs.getString(2);
			String textContent = rs.getString(3);
			boolean isLeading = rs.getInt(4)==1;
			Set<Media> mediaFiles = MediaDao.getInstance().getMediaByArticle(articleId);
			Article a = new Article(articleId, title, textContent, categoryId, isLeading, mediaFiles);
			articles.add(a);
		}
		
		return articles;
	}
	
	
}
