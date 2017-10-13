package model_db;

public final class CommentDao {
	
	private static CommentDao instance;
	private CommentDao(){}
	
	public static synchronized CommentDao getInstance(){
		if(instance == null){
			instance = new CommentDao();
		}
		return instance;
	}

	
	
	
	
	
	
	
	
}
