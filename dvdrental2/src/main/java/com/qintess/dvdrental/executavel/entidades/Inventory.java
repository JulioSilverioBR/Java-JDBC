package com.qintess.dvdrental.executavel.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Inventory {
	private Integer inventoryId;
	private Film filmId;
	private Store storeId;
	
	public Inventory(Integer inventoryId, Film filmId, Store storeId) {
		this.inventoryId = inventoryId;
		this.filmId = filmId;
		this.storeId = storeId;
	}

	public Inventory(ResultSet rs) throws SQLException {
		this.inventoryId = rs.getInt("inventory_id");
	}
	
	public Integer getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Integer inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Film getFilmId() {
		return filmId;
	}

	public void setFilmId(Film filmId) {
		this.filmId = filmId;
	}

	public Store getStoreId() {
		return storeId;
	}

	public void setStoreId(Store storeId) {
		this.storeId = storeId;
	}

	@Override
	public String toString() {
		return "Inventory [inventoryId=" + inventoryId + ", filmId=" + filmId + ", storeId=" + storeId + "]";
	}
	
}
