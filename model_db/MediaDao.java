package model_db;

public final class MediaDao {

	private static MediaDao instance;
	private MediaDao(){}
	
	public static synchronized MediaDao getInstance(){
		if(instance == null){
			instance = new MediaDao();
		}
		return instance;
	}
	
}
