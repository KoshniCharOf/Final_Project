package model_db;

public final class CategoryDao {
	
	private static CategoryDao instance;
	private CategoryDao(){}
	
	public static synchronized CategoryDao getInstance(){
		if(instance == null){
			instance = new CategoryDao();
		}
		return instance;
	}

	
	
	
	
	
	
	
	
}
