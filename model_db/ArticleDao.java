package model_db;

public final class ArticleDao {

	private static ArticleDao instance;
	private ArticleDao(){}
	
	public static synchronized ArticleDao getInstance(){
		if(instance == null){
			instance = new ArticleDao();
		}
		return instance;
	}
	
	
	
	
	
	
}
