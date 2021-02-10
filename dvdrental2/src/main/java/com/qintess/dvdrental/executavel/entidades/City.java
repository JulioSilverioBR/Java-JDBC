package com.qintess.dvdrental.executavel.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;

public class City {

	private Integer city_id;
	private String city;
	
	private Country country;

	public City(String city, Country country) {
		this.city = city;
		this.country = country;
	}

	public City(ResultSet rs) throws SQLException {
		
		this.city_id = rs.getInt("city_id");
		this.city = rs.getString("city");
//		this.country.setCountry_id(rs.getInt("country_id"));
	}
	
	public int getCity_id() {
		return city_id;
	}

	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Country getCountry() {
		return country;
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(city + "\n");
		sb.append("País: ");
		sb.append(country + "\n");
		
		return sb.toString(); 
		

	}

	public int compareTo(City o) {
		return this.city_id.compareTo(o.city_id);
	}
}
