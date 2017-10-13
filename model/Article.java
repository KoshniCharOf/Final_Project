package model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Article {
	
	private String title;
	private String textContent;
	private LocalDateTime created ;
	private LocalDateTime updated; //TBD - this field it's missing at the DB base and has to be inserted
	private int impressions;
	private boolean isLeading; //leading period duration??
	private Set<Tag> tags;
	private Set<Media> mediaFiles;
	
	
	public Article(String title, String textContent, LocalDateTime created,
			boolean isLeading, String tags, List<Media> mediaFiles) {

		this.title = title;
		this.textContent = textContent;
		this.created =  LocalDateTime.now();
		this.updated = created;
		this.impressions = 0;
		this.isLeading = isLeading;
		this.tags = new HashSet<>();
		//traverse
		String[] tagsA = tags.split(",");
		for (String string : tagsA) {
			addTag(string.trim());
		}
		
		this.mediaFiles = new HashSet<>();
	}
	
	public void addTag(String tag) {
		this.tags.add(new Tag(tag));
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
	public LocalDateTime getUpdated() {
		return updated;
	}
	public int getImpressions() {
		return impressions;
	}
	public boolean isLeading() {
		//return false if more than 1 day old
		return isLeading && created.isAfter(LocalDateTime.now().minusDays(1));
	}
	public Set<Tag> getTags() {
		return tags;
	}
	public Set<Media> getMediaFiles() {
		return mediaFiles;
	}
	
	
	
}
