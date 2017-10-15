package model;

import java.time.LocalDateTime;

public class Comment {
	
	private long id;
	private long user_id;
	private long article_id;
	private String content;
	private int likes;
	private int dislikes;
	private LocalDateTime timeCreated;
	private boolean isAproved = true;
	
	public Comment(long user_id, long article_id, String content) {
		this.user_id = user_id;
		this.article_id = article_id;
		this.content = content;
		this.timeCreated = LocalDateTime.now();
	}
	
	
	
	
	//TODO methods
}
