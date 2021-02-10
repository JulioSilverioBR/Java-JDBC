package com.qintess.dvdrental.executavel.entidades;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Language implements Comparable<Language> {
	
	private Integer languageId;
	private String name; 
	
	public Language() {}
	
	public Language(int languageId, String name, Date lastUpdate) {
		this.languageId = languageId;
		this.name = name;
	}
	
	public Language(ResultSet rs) throws SQLException {
		this.languageId = rs.getInt("language_id");
		this.name = rs.getString("name");
	}
	
	public int getLanguageId() {
		return languageId;
	}
	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		
		
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		return sb.toString();
	}

	@Override
	public int compareTo(Language o) {
		return this.languageId.compareTo(o.languageId);
	}

}
