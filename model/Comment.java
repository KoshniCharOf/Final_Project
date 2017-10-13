package model;

import java.time.LocalDateTime;

public class Comment {
	
	private User author;
	private Article article;
	private String content;
	private Comment answer; // shall we leave it like this
	private int likes = 0;
	private int dislikes = 0;
	private LocalDateTime timeCreated = LocalDateTime.now();
	private boolean isAproved = true;
	
	public Comment(User author, Article article, String content) {
		this.author = author;
		this.article = article;
		this.content = content;
	}
	
	
	
	
	//TODO methods
}
