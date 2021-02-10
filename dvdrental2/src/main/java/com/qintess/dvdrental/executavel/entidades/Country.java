package com.qintess.dvdrental.executavel.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Country{

	private Integer country_id = 0;
	private String country;
	
	public Country(String country) {
		this.country = country;
	}
	
	public Country(ResultSet rs) throws SQLException {
		this.country_id = rs.getInt("country_id");
		this.country = rs.getString("country");
	}
	
	public int getCountry_id() {
		return country_id;
	}
	public void setCountry_id(Integer country_id) {
		this.country_id = country_id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public int compareTo(Country o) {
		return this.country_id.compareTo(o.country_id);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(country);
		return sb.toString(); 
		
		
	}
	
}
