package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

public class Article implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String title;
	private String textContent;
	private long category_id; //or Object?
	private LocalDateTime created ;
	private long impressions;
	private boolean isLeading; //leading period duration 1 day
	private Set<Media> mediaFiles;//!!!Set<Integer> media_id???
	private Set<Comment> comments;
	
	
	public Article(long id, String title, String textContent, long category_id, LocalDateTime created, long impressions,
			boolean isLeading, Set<Media> mediaFiles,Set<Comment> comments) {
		this(title, textContent, category_id, created, impressions, isLeading, mediaFiles,comments);
		this.id = id;
		
	}

	public Article(String title, String textContent, long category_id, LocalDateTime created, long impressions,
			boolean isLeading, Set<Media> mediaFiles,Set<Comment> comments) {
		super();
		this.title = title;
		this.textContent = textContent;
		this.category_id = category_id;
		this.created = created;
		this.impressions = impressions;
		this.isLeading = isLeading;
		this.mediaFiles = mediaFiles;
		this.comments = comments;
	}





	// TODO ADD MEDIA
	
	public long getCategory_id() {
		return category_id;
	}

	public String getTitle() {
		return title;
	}
	
	public String getTextContent() {
		return textContent;
	}
	
	public LocalDateTime getCreated() {
		return created;
	}
	
	public long getImpressions() {
		return impressions;
	}
	
	public boolean isLeading() {
		//return false if more than 1 day old
		return isLeading && created.isAfter(LocalDateTime.now().minusDays(1));
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<Media> getMediaFiles() {
		return Collections.unmodifiableSet(mediaFiles);
	}

	public Set<Comment> getComments() {
		return comments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (category_id ^ (category_id >>> 32));
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
		Article other = (Article) obj;
		if (category_id != other.category_id)
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
