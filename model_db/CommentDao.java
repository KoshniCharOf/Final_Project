package model_db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import model.Comment;

public final class CommentDao {
	
	private static CommentDao instance;
	private Map<Long, Set<Comment>> commentsByArticle; //article_id -> comments
	
	private CommentDao(){
		commentsByArticle = new ConcurrentHashMap<>();
	}
	
	public static synchronized CommentDao getInstance(){
		if(instance == null){
			instance = new CommentDao();
		}
		return instance;
	}

	public synchronized void addComment(Comment comment) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO comments (user_id, article_id, content, date_time,isApproved ) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, comment.getUser_id());
		ps.setLong(2, comment.getArticle_id());
		ps.setString(3, comment.getContent());
		ps.setTimestamp(4, Timestamp.valueOf(comment.getTimeCreated()));
		ps.setBoolean(5, true);
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		long id = rs.getLong(1);
		comment.setId(id);
		addInCommentsByArticle(comment.getArticle_id(),comment);
		
	}
	
	private void addInCommentsByArticle(long article_id, Comment comment) {
		if(!this.commentsByArticle.containsKey(article_id)) {
			this.commentsByArticle.put(article_id, new HashSet<>());
		}
		this.commentsByArticle.get(article_id).add(comment);
	}

	public synchronized boolean removeComment(Comment comment) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("DELETE FROM comments WHERE c.comment_id = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, comment.getId());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		removeFromCommentsByArticle(comment);
		return rs.next();
	}
	
	private void removeFromCommentsByArticle(Comment comment) {
		this.commentsByArticle.get(comment.getArticle_id()).remove(comment);
	}

	public boolean updateComment(long comment_id, String content) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("UPDATE comments c SET c.content = content WHERE c.comment_id = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, comment_id);
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		return rs.next();
	}
	
	public synchronized void disaproveComments() throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		String[] forbiddenWords = "fuck, maika ti, balo si mamata".split(", ");
		for (String word : forbiddenWords) {
			PreparedStatement ps = con.prepareStatement("UPDATE comments c SET c.isApproved = false WHERE c.content like %?%", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, word.trim());
			ps.executeUpdate();
		}
		// Just for fun
	}
	
	
    public synchronized Set<Comment> getCommentsByArticle(long article_id) throws SQLException{
		Set<Comment> comments = this.commentsByArticle.get(article_id);
		
		return Collections.unmodifiableSet(comments);
	}
	
	//likes
    public synchronized void likeComment(long comment_id) throws SQLException {
    	Connection con = DBManager.getInstance().getConnection();
    	
    	
		PreparedStatement ps = con.prepareStatement("UPDATE comments c SET c.likes = likes+1 WHERE c.comment_id = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, comment_id);
		ps.executeUpdate();
    }
    //dislikes
    public synchronized void dislikeComment(long comment_id) throws SQLException {
    	Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("UPDATE comments c SET c.dislikes = dislikes+1 WHERE c.comment_id = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, comment_id);
		ps.executeUpdate();
    }
	
	
	
}
