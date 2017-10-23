package model_db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import model.Comment;
import model.User;

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
		PreparedStatement ps = con.prepareStatement("INSERT INTO comments (user_id, article_id, content, likes, dislikes, date_time, isApproved ) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, comment.getUserId());
		ps.setLong(2, comment.getArticleId());
		ps.setString(3, comment.getContent());
		ps.setInt(4, comment.getLikes());
		ps.setInt(5, comment.getDislikes());
		ps.setTimestamp(6, Timestamp.valueOf(comment.getTimeCreated()));
		ps.setBoolean(7, true);
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		long id = rs.getLong(1);
		comment.setId(id);
		addInCommentsByArticle(comment.getArticleId(),comment);
		
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
		ps.setLong(1, comment.getCommentId());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		removeFromCommentsByArticle(comment);
		return rs.next();
	}
	
	private void removeFromCommentsByArticle(Comment comment) {
		this.commentsByArticle.get(comment.getArticleId()).remove(comment);
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
	
	
//    public synchronized Set<Comment> getCommentsByArticle(long article_id) throws SQLException{
//		Set<Comment> comments = this.commentsByArticle.get(article_id);
//		
//		return Collections.unmodifiableSet(comments);
//	}
	
	//likes
    public synchronized void likeComment(long comment_id) throws SQLException {
    	Connection con = DBManager.getInstance().getConnection();
    	
    	
		PreparedStatement ps = con.prepareStatement("UPDATE comments c SET c.likes = likes+1 WHERE c.comment_id = ?");
		ps.setLong(1, comment_id);
		System.out.println("laik4e");
		ps.executeUpdate();
    }
    //dislikes
    public synchronized void dislikeComment(long comment_id) throws SQLException {
    	Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("UPDATE comments c SET c.dislikes = dislikes+1 WHERE c.comment_id = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, comment_id);
		ps.executeUpdate();
    }
    
    public  Set<Comment> getCommentsByArticle(long id) throws SQLException{
		Set<Comment> comments = new HashSet<Comment>();
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT comment_id, user_id, article_id, content, likes, dislikes, date_time, isApproved FROM sportal.comments WHERE article_id=?");
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			long commentId = rs.getLong(1);
			long userId = rs.getLong(2);
			long articleId = rs.getLong(3);
			String content = rs.getString(4);
			int likes = rs.getInt(5);
			int dislikes = rs.getInt(6);
			LocalDateTime timeCreated = rs.getTimestamp(7).toLocalDateTime();
			boolean isAproved = rs.getBoolean(8);
			//TODO 
			Set<User> voters = new HashSet<>();
			
			comments.add(new Comment(commentId, userId, articleId, content, likes, dislikes, timeCreated, isAproved, voters));
		}
		return comments;
	}
    
    public Comment getCommentById(long id) throws SQLException{
		
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT comment_id, user_id, article_id, content, likes, dislikes, date_time, isApproved FROM sportal.comments WHERE comment_id=?");
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		
		long commentId = rs.getLong(1);
		long userId = rs.getLong(2);
		long articleId = rs.getLong(3);
		String content = rs.getString(4);
		int likes = rs.getInt(5);
		int dislikes = rs.getInt(6);
		LocalDateTime timeCreated = rs.getTimestamp(7).toLocalDateTime();
		boolean isAproved = rs.getBoolean(8);
		//TODO 
		Set<User> voters = new HashSet<>();
		
		return new Comment(commentId, userId, articleId, content, likes, dislikes, timeCreated, isAproved, voters);
	}
	

	
}
