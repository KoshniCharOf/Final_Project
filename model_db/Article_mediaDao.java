package model_db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Article_media;

public class Article_mediaDao {
	
	private static Article_mediaDao instance;
	private Map<Long, Set<Long>> mediaByArticle; //article_id -> set of  media_id
	
	private Article_mediaDao(){}
	
	public static synchronized Article_mediaDao getInstance(){
		if(instance == null){
			instance = new Article_mediaDao();
		}
		return instance;
	}

	public synchronized void addArticle_media(Article_media article_media) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO article_media (article_id, media_id) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, article_media.getArticle_id());
		ps.setLong(2, article_media.getMedia_id());
		ps.executeUpdate();
		
		addInMediaByArticle(article_media.getArticle_id(), article_media.getMedia_id());
		
		
	}


	private void addInMediaByArticle(long article_id, long media_id) {
		if(!this.mediaByArticle.containsKey(article_id)) {
			this.mediaByArticle.put(article_id, new HashSet<>());
		}
		this.mediaByArticle.get(article_id).add(media_id);
	}

	public synchronized boolean removeArticle_media(Article_media article_media) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("DELETE FROM article_media am WHERE am.article_id = ? AND am.media_id = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, article_media.getArticle_id());
		ps.setLong(2, article_media.getMedia_id());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		removeFromMediaByArticle(article_media);
		return rs.next();
	}
	
	 private void removeFromMediaByArticle(Article_media article_media) {
		this.mediaByArticle.get(article_media.getArticle_id()).remove(article_media.getMedia_id());
	}

	public synchronized Set<Long> getMediaByArticle(long article_id) throws SQLException{
			Set<Long> media = this.mediaByArticle.get(article_id);
			
			return Collections.unmodifiableSet(media);
		}
	
	
}
