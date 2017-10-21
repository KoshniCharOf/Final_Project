package model_db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;
import model.Media;

public final class MediaDao {

	private static MediaDao instance;
	private ConcurrentHashMap<Long, Media> media;//id - media
	
	private MediaDao(){
		this.media = new ConcurrentHashMap<>();
	}
	
	public static synchronized MediaDao getInstance(){
		if(instance == null){
			instance = new MediaDao();
		}
		return instance;
	}
	

	
	public void addMedia(Media media) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO media (name, content_url) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, media.getName());
		ps.setString(2, media.getUrl());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		media.setId(rs.getLong(1));
		this.media.put(media.getId(), media);
	}
	
	public boolean removeMedia(long media_id) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("DELETE FROM media m WHERE m.media_id = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, media_id);
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		this.media.remove(media_id);
		return rs.next();
	}
	
	public boolean existsMediaId(Long media_id) throws SQLException{
		return this.media.containsKey(media_id);
	}
	
	public synchronized Media getMediaByName(String name) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT media_id, name, content_url FROM media m WHERE m.name = ?");
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		if(!rs.next()){
			return null;
		}
		return new Media(rs.getLong("media_id"), name, rs.getString("content_url"));
	}
	
	public synchronized Media getMediaById(long media_id) throws SQLException{
		
		String name = this.media.get(media_id).getName();
		String url = this.media.get(media_id).getUrl();
		
		return new Media(media_id, name, url);
	}
	
	
}
