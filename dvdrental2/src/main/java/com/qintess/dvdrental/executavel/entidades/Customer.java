package com.qintess.dvdrental.executavel.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer implements Comparable<Customer>{

	private Integer customerId;
	private String firstName;
	private String lastName;
	private String email;
	private int active;
	private String status;
	
	private Store store;

	private FullAddress fullAddress;

	public Customer() {}
	
	public Customer(String firstName, String lastName, String email, int active, String status,
			Store store, FullAddress fullAddress) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.active = active;
		this.status = status;
		this.store = store;
		this.fullAddress = fullAddress;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Customer(ResultSet rs) throws SQLException {
		this.customerId = rs.getInt("customer_id");
		this.firstName = rs.getString("first_name");
		this.lastName = rs.getString("last_name");
		this.email = rs.getString("email");
		this.active = rs.getInt("active");
	}

	public int getCustomerId() {
		return customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getStatus() {
		if(this.active==1) {
			return this.status = "Ativo";
		}else {
			return this.status = "Não ativo";
		}
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public FullAddress getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(FullAddress fullAddress) {
		this.fullAddress = fullAddress;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Cliente \n");
		sb.append("Nome: ");
		sb.append(firstName + " " + lastName + "\n");
		sb.append("Email: ");
		sb.append(email + "\n");
		sb.append("Status: ");
		sb.append(status + "\n");
		sb.append("Endereço completo: ");
		sb.append(fullAddress + "\n");
		return sb.toString();
	}
	@Override
	public int compareTo(Customer o) {
		return this.customerId.compareTo(o.customerId);
	}
	
	
}
