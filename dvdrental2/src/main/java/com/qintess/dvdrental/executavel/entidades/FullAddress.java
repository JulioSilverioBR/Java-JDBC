package com.qintess.dvdrental.executavel.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FullAddress implements Comparable<FullAddress> {
	
	private Integer id;
	private String address;
	private String address2;
	private String district;
	private String postalCode;
	private String phone;

	private City city; //criar entidade city

	public FullAddress() {}
	
	public FullAddress(Integer id, String address, String address2, String district, String postalCode, String phone,
			City city) {
		this.id = id;
		this.address = address;
		this.address2 = address2;
		this.district = district;
		this.postalCode = postalCode;
		this.phone = phone;
		this.city = city;
	}

	public FullAddress(ResultSet rs) throws SQLException {
		this.id = rs.getInt("address_id");
		this.address = rs.getString("address");
		this.district = rs.getString("district");
		this.postalCode = rs.getString("postal_code");
		this.phone = rs.getString("phone");
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}
	
	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(address + "\n");
		sb.append("Distrito: ");
		sb.append(district + "\n");
		sb.append("Codigo Postal: ");
		sb.append(postalCode + "\n");
		sb.append("Telefone: ");
		sb.append(phone + "\n");
		sb.append("Cidade: ");
		sb.append(city + "\n");
		return sb.toString();
		

	}

	public int compareTo(FullAddress o) {
		return this.id.compareTo(o.id);
	}
	

}
