package com.qintess.dvdrental.executavel.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Staff {

	private int staff_id;
	private String first_name;
	private String last_name;
	private String email;
	private Store store_id;
	private boolean active;
	private String username;
	private String passworld;

	public Staff() {}

	public Staff(int staff_id, String first_name, String last_name, String email, Store store_id, boolean active,
			String username, String passworld) {
		this.staff_id = staff_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.store_id = store_id;
		this.active = active;
		this.username = username;
		this.passworld = passworld;
	}

	public Staff(ResultSet rs) throws SQLException {
		this.staff_id = rs.getInt("staff_id");
		this.first_name = rs.getString("first_name");
		this.last_name = rs.getString("last_name");
		this.email = rs.getString("email");
		this.username = rs.getString("username");
		this.passworld = rs.getString("password");
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public int getId() {
		return staff_id;
	}

	public void setId(int staff_id) {
		this.staff_id = staff_id;
	}
	public String getPassworld() {
		return passworld;
	}

	public void setPassworld(String passworld) {
		this.passworld = passworld;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Store getStore() {
		return this.store_id;
	}
	public void setStore(Store store) {
		this.store_id = store;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Staff [staff_id=" + staff_id + ", first_name=" + first_name + ", last_name=" + last_name + ", email="
				+ email + ", store_id=" + store_id + ", active=" + active + ", username=" + username + ", passworld="
				+ passworld + "]";
	} 	
}	
