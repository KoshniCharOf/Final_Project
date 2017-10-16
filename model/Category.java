package model;


public class Category  {
	
	private long category_id;
	private String name; //45
	

	public Category(long category_id, String name) {
		this(name);
		this.category_id = category_id;
	}

	public Category(String name)  {
		if(valid(name)) {
			this.name = name;
		}
		//TODO ELSE?
	}

	private boolean valid(String name) {
		return name!=null && !name.trim().isEmpty() && name.length() < 45;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return category_id;
	}

	public void setId(long category_id) {
		this.category_id = category_id;
	} 
	
	
}
