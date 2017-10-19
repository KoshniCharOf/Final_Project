package model_db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import model.User;
import util.Encrypter;

public final class UserDao {
	private static UserDao instance;
	private UserDao(){}
	
	public static synchronized UserDao getInstance(){
		if(instance == null){
			instance = new UserDao();
		}
		return instance;
	}
	
	public void insertUser(User u) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO users (username, password, email, isAdmin, isBanned, date_time_registered) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, u.getUsername());
		ps.setString(2, Encrypter.encrypt(u.getPassword()));//put good salt
		ps.setString(3, u.getEmail());
	
		ps.setBoolean(4, u.isAdmin());
		ps.setBoolean(5, u.isBanned());
		ps.setTimestamp(6, Timestamp.valueOf(u.getRegistered()));
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
	
		u.setId(rs.getLong(1));

	}
	
	public boolean existsUser(String username, String password) throws SQLException{

		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT count(*) as count FROM users u WHERE u.username = ? AND u.password = ?");
		ps.setString(1, username);
		ps.setString(2, Encrypter.encrypt(password));
		ResultSet rs = ps.executeQuery();
		rs.next();
	
		return rs.getInt("count")>0;
	}
	
	public User getUser(String username) throws SQLException{
		
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT user_id, username, password, email, age, date_time_registered, isBanned, isAdmin FROM users WHERE username = ?");
		ps.setString(1, username);
		
		ResultSet rs = ps.executeQuery();
		
		rs.next();
		
		return new User(
				rs.getLong("user_id"), 
				username, 
				rs.getString("password"), 
				rs.getString("email"),
				18,//rs.getInt("age")
				rs.getTimestamp("date_time_registered").toLocalDateTime(),
				rs.getBoolean("isBanned"),
				rs.getBoolean("isAdmin")
				);
	}
	
	public boolean isAdmin(String username, String password) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("select u.isAdmin  from users u where u.name = ?");
		ps.setString(1, username);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt("u.isAdmin") == 1;
	}

	public boolean checkEmail(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean checkUsername(String username) {
		// TODO Auto-generated method stub
		return false;
	}
}
