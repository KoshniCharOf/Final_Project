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
	private boolean isAproved;
	private HashSet<User> voters = new HashSet<User>();
	
	
	
	public Comment(long id, long user_id, long article_id, String content) {
		this(user_id, article_id, content);
		this.id = id;
	}

	public Comment(long user_id, long article_id, String content) {
		this.user_id = user_id;
		this.article_id = article_id;
		this.content = content;
		this.timeCreated = LocalDateTime.now();
	}

	public long getId() {
		return id;
	}

	public long getUser_id() {
		return user_id;
	}

	public long getArticle_id() {
		return article_id;
	}

	public String getContent() {
		return content;
	}

	public int getLikes() {
		return likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public LocalDateTime getTimeCreated() {
		return timeCreated;
	}

	public boolean isAproved() {
		return isAproved;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (article_id ^ (article_id >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (article_id != other.article_id)
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
}
