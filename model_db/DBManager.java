package model_db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBManager {
	
	private static final String DB_IP = "localhost";
	private static final String DB_PORT = "3306";
	private static final String DB_DBNAME = "sportal";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "ppsf";
	
	
	private static DBManager instance;
	private Connection con;
	
	private DBManager(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found or failed to load. Check your libraries");
		}
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DB_IP + ":" + DB_PORT + "/" + DB_DBNAME, DB_USER, DB_PASS);
		} catch (SQLException e) {
			System.out.println("Ops");
		}
	}
	
	public static synchronized DBManager getInstance(){
		if(instance == null){
			instance = new DBManager();
		}
		return instance;
	}
	
	public Connection getConnection(){
		return con;
	}
	
	public void closeConnection(){
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("op");
			}
		}
	}
}
