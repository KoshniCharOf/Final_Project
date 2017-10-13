package model_db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;

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
		PreparedStatement ps = con.prepareStatement("INSERT INTO users (username, password, email) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, u.getUsername());
		ps.setString(2, u.getPassword());
		ps.setString(3, u.getEmail());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		u.setId(rs.getLong(1));
	}
	
	public boolean existsUser(String username, String password) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT count(*) as count FROM users WHERE username = ? AND password = ?");
		ps.setString(1, username);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt("count") > 0;
	}
	
	public User getUser(String username) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT user_id, name, password, e_mail, age, date_time_registered, isBanned, isAdmin FROM users WHERE username = ?");
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return new User(
				rs.getLong("user_id"), 
				username, 
				rs.getString("password"), 
				rs.getString("e_mail"),
				rs.getInt("age"),
				rs.getTimestamp("date_time_registered").toLocalDateTime(),
				rs.getBoolean("isBanned"),
				rs.getBoolean("isAdmin")
				);
	}
}
