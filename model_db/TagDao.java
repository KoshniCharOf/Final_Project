package model_db;

public final class TagDao {

	private static TagDao instance;
	private TagDao(){}
	
	public static synchronized TagDao getInstance(){
		if(instance == null){
			instance = new TagDao();
		}
		return instance;
	}
	
}
