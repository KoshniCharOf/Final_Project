package model;

import java.time.LocalDateTime;
import java.util.HashSet;

public class User {

	private long id;
	private String username;
	private String password;
	private String email;
	private int age;
	private boolean isAdmin;
	private boolean isBanned;
	private LocalDateTime registered;
	private String avatar_url;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
