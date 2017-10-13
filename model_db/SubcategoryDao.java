package model_db;

public final class SubcategoryDao {
	
	private static SubcategoryDao instance;
	private SubcategoryDao(){}
	
	public static synchronized SubcategoryDao getInstance(){
		if(instance == null){
			instance = new SubcategoryDao();
		}
		return instance;
	}

	
	
	
	
	
	

}
