package com.qintess.dvdrental.executavel.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Category implements Comparable<Category>{

	private Integer category_id;
	private String category;
	
	public Category() {}
	
	public Category(int category_id, String category) {
		this.category_id = category_id;
		this.category = category;
	}
	
	public Category(ResultSet rs) throws SQLException {
		this.category_id = rs.getInt("category_id");
		this.category = rs.getString("name");
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

	public int getCategory_id() {
		return category_id;
	}

	public int compareTo(Category o) {
		return this.category_id.compareTo(o.category_id);
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("Categoria: ");
		sb.append(category);

		return sb.toString();
	}
	
	
}
