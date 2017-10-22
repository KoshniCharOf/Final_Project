package model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

public class Article {
	
	private long id;
	private String title;
	private String textContent;
	private long category_id; //or Object?
	private LocalDateTime created ;
	private long impressions;
	private boolean isLeading; //leading period duration 1 day
	private Set<Media> mediaFiles;//!!!Set<Integer> media_id???
	
	
	public Article(long id, String title, String textContent, long category_id, LocalDateTime created, long impressions,
			boolean isLeading, Set<Media> mediaFiles) {
		this(title, textContent, category_id, created, impressions, isLeading, mediaFiles);
		this.id = id;
		
	}

	public Article(String title, String textContent, long category_id, LocalDateTime created, long impressions,
			boolean isLeading, Set<Media> mediaFiles) {
		super();
		this.title = title;
		this.textContent = textContent;
		this.category_id = category_id;
		this.created = created;
		this.impressions = impressions;
		this.isLeading = isLeading;
		this.mediaFiles = mediaFiles;
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
	
	
}
