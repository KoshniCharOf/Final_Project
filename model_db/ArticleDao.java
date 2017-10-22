package model_db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import model.Article;
import model.Comment;
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

	public long addArticle(Article article) throws SQLException{
		
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
		return article.getId();
		
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
		String sql = "SELECT a.article_id, a.category_id, a.title, a.content, a.datetime, a.impressions, a.isLeading  FROM  articles as a  WHERE a.category_id=? ";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setLong(1, categoryId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			
			long articleId = rs.getLong(1);
			System.out.println("articleId "+articleId);
			String title = rs.getString(3);
			String textContent = rs.getString(4);
			LocalDateTime created = rs.getTimestamp(5).toLocalDateTime();
			long impressions = rs.getInt(6);
			boolean isLeading = rs.getInt(7)==1;
			Set<Media> mediaFiles = MediaDao.getInstance().getMediaByArticle(articleId);
			Set<Comment> comments = CommentDao.getInstance().getCommentsByArticle(articleId);
			Article a = new Article(articleId, title, textContent, categoryId, created, impressions, isLeading, mediaFiles,comments);
			
			articles.add(a);
		}
		
		return articles;
	}
	
	//get all articles by title
		public Set<Article> getArtticlesByTitle(String  title) throws SQLException{
			Set<Article> articles = new HashSet<Article>();
			Connection con  = DBManager.getInstance().getConnection();
			String sql = "SELECT a.article_id, a.category_id, a.title, a.content, a.datetime, a.impressions, a.isLeading  FROM  articles as a  WHERE a.title LIKE ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "%"+title+"%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				
				long articleId = rs.getLong(1);
				long categoryId = rs.getLong(2);
				String textContent = rs.getString(4);
				LocalDateTime created = rs.getTimestamp(5).toLocalDateTime();
				long impressions = rs.getInt(6);
				boolean isLeading = rs.getInt(7)==1;
				Set<Media> mediaFiles = MediaDao.getInstance().getMediaByArticle(articleId);
				Set<Comment> comments = CommentDao.getInstance().getCommentsByArticle(articleId);
				Article a = new Article(articleId, title, textContent, categoryId, created, impressions, isLeading, mediaFiles,comments);
				articles.add(a);
			}
			
			return articles;
		}
		
		public Article getArtticleById(long articleId) throws SQLException{
			
			Connection con  = DBManager.getInstance().getConnection();
			String sql = "SELECT a.article_id, a.category_id, a.title, a.content, a.datetime, a.impressions, a.isLeading  FROM  articles as a  WHERE a.article_id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, articleId);
			ResultSet rs = ps.executeQuery();
			rs.next();
				
			long categoryId = rs.getLong(2);
			String title = rs.getString(3);
			String textContent = rs.getString(4);
			LocalDateTime created = rs.getTimestamp(5).toLocalDateTime();
			long impressions = rs.getInt(6);
			boolean isLeading = rs.getInt(7)==1;
			Set<Media> mediaFiles = MediaDao.getInstance().getMediaByArticle(articleId);
			Set<Comment> comments = CommentDao.getInstance().getCommentsByArticle(articleId);
			Article article = new Article(articleId, title, textContent, categoryId, created, impressions, isLeading, mediaFiles, comments);
				
			return article;
		}
		
		public void incremenImpression(long articleId) throws SQLException{
			
			Connection con  = DBManager.getInstance().getConnection();
			String sql = "UPDATE articles as a SET a.impressions=a.impressions+1  WHERE a.article_id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, articleId);
			ps.executeUpdate();
			
		}
}
