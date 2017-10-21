package model;


public class Category  {
	
	private long categoryId;
	private String name; //45
	

	public Category(long category_id, String name) {
		this(name);
		this.categoryId = category_id;
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

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long category_id) {
		this.categoryId = category_id;
	} 
	
	
}
