package com.qintess.dvdrental.executavel.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Store implements Comparable<Store> {

	private Integer store_id;
	private Staff manage_staff;
	private FullAddress address;
	
	public Store() {}
	
	public Store(int store_id, Staff manage_staff, FullAddress address) {
		this.store_id = store_id;
		this.manage_staff = manage_staff;
		this.address = address;
	}

	public Store(ResultSet rs) throws SQLException{
		this.store_id = rs.getInt("store_id");
	}

	public int getStore_id() {
		return store_id;
	}
	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public Staff getManage_staff() {
		return manage_staff;
	}

	public void setManage_staff(Staff manage_staff) {
		this.manage_staff = manage_staff;
	}

	public FullAddress getAddress() {
		return address;
	}

	public void setAddress(FullAddress address) {
		this.address = address;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nLoja: \n");
		sb.append("Número: ");
		sb.append(store_id);
		sb.append("\nEndereço loja: ");
		sb.append(address + "\n");
		sb.append("Funcionário: ");
		sb.append(manage_staff);
		return sb.toString();
	}

	@Override
	public int compareTo(Store o) {
		return this.store_id.compareTo(o.store_id);
	}
	
}
