package com.qintess.dvdrental.executavel.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Actor implements Comparable<Actor> {

	private Integer actor_id;
	private String first_name;
	private String last_name;
	
	public Actor() {}
	
	public Actor(int actor_id, String first_name, String last_name) {
		this.actor_id = actor_id;
		this.first_name = first_name;
		this.last_name = last_name;
	}
	
	public Actor(ResultSet rs) throws SQLException {
		
		this.actor_id = rs.getInt("actor_id");
		this.first_name = rs.getString("first_name");
		this.last_name = rs.getString("last_name");
		
	}

	public int getActor_id() {
		return actor_id;
	}
	public void setActor_id(int actor_id) {
		this.actor_id = actor_id;
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

	@Override
	public int compareTo(Actor o) {
		return this.actor_id.compareTo(o.actor_id);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(first_name + " " +last_name );

		return sb.toString();
	}
	
	
}
