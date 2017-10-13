package model;

import java.util.ArrayList;
import java.util.List;

public class Category extends Subcategory {
	
	private String name;
	private List<Subcategory> subcategories;
	
	
	public Category(String name) {
		super(name);
		this.subcategories = new ArrayList<Subcategory>();
	} 
	
	
	
	
	//TODO methods
	
}
