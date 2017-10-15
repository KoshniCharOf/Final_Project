package model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Article {
	
	private long id;
	private String title;
	private String textContent;
	private String category_id; //or Object?
	private LocalDateTime created ;
	private long impressions;
	private boolean isLeading; //leading period duration 1 day
	private Set<Media> mediaFiles;//!!!Set<Integer> media_id???
	
	
	public Article(String title, String textContent, String category_id, boolean isLeading, Set<Media> mediaFiles) {

		this.title = title;
		this.textContent = textContent;
		this.category_id = category_id;
		this.created =  LocalDateTime.now();
		this.isLeading = isLeading;
		this.mediaFiles = mediaFiles;
	}
	
	public String getCategory_id() {
		return category_id;
	}

	public void addMedia(Media media) {
		this.mediaFiles.add(media);
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
	public Set<Media> getMediaFiles() {
		return mediaFiles;
	}
	
	
	
}
