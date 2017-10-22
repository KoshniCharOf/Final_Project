package model;

import java.io.Serializable;

public class Media implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String IMAGE_URL = "D:\\uploads\\images\\";
	private long media_id;
	private String name;//45
	private String url;//1024
	
	public Media(long media_id, String name, String url) {
		this(name,url);
		this.media_id = media_id;
	}
	
	

	public Media(String name, String url) {
		this.name = name;
		this.url = url;
	}



	public void setId(long media_id) {
		this.media_id = media_id;
	}

	public long getMedia_id() {
		return media_id;
	}



	public String getName() {
		return name;
	}



	public String getUrl() {
		return url;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		Media other = (Media) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
	

}
