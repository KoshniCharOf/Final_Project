package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {

	private long id;
	private String username;
	private String password;
	private String email;
	private int age;
	private boolean isAdmin;
	private boolean isBanned;
	private LocalDateTime registered;
	private List<User> friends = new ArrayList<User>(); //friends list?

	public User(long id, String username, String password, String email, int age, LocalDateTime registered, boolean isBanned, boolean isAdmin) {
		this(username, password, email);
		this.id = id;
		this.age = age;
		this.registered = registered;
		this.isAdmin = isAdmin;
		this.isBanned = isBanned;
	}
	
	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.registered = LocalDateTime.now();
		this.isAdmin = false;
		this.isBanned = false;
	}

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
}
