package model;

import java.time.LocalDateTime;
import java.util.List;

public class Article {
	
	private String title;
	private String textContent;
	private LocalDateTime created = LocalDateTime.now();
	private LocalDateTime updated; //TBD - this field it's missing at the DB base and has to be inserted
	private int impressions;
	private boolean isLeading; // leading period duration??
	private List<Tag> tags;
	private List<Media> mediaFiles;
	
	
	
	
	
	
	public String getTitle() {
		return title;
	}
	public String getTextContent() {
		return textContent;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public LocalDateTime getUpdated() {
		return updated;
	}
	public int getImpressions() {
		return impressions;
	}
	public boolean isLeading() {
		return isLeading;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public List<Media> getMediaFiles() {
		return mediaFiles;
	}
	
	
	
}
