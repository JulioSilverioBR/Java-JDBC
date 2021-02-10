package com.qintess.dvdrental.executavel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.qintess.dvdrental.executavel.entidades.City;
import com.qintess.dvdrental.executavel.entidades.Country;
import com.qintess.dvdrental.executavel.entidades.FullAddress;

public class FullAddressDao {

	private Connection conn;

	public FullAddressDao (Connection conn) {
		this.conn = conn;
	} 

	private Country checaCountry(Country count) {

		try {
			String sql = "SELECT COUNTRY, COUNTRY_ID FROM COUNTRY WHERE UPPER(COUNTRY) = UPPER(?)";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, count.getCountry());

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				Country country = new Country(rs);
				count = country;

			}else {

				sql = "INSERT INTO public.country(\r\n"
						+ "	country)\r\n"
						+ "	VALUES (?);";

				pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, count.getCountry());

				if(pstmt.executeUpdate() > 0 ) {
					rs = pstmt.getGeneratedKeys();
					int id = 0;
					while(rs.next()) {
						id = rs.getInt(1);
						count.setCountry_id(id);
					}
				}

			}
		}catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	private City checaCity(City city) {

		try {

			String sql = "SELECT CITY.CITY, CITY.CITY_ID, COUNTRY.COUNTRY_ID, COUNTRY.COUNTRY FROM CITY INNER JOIN COUNTRY ON COUNTRY.COUNTRY_ID = CITY.COUNTRY_ID WHERE UPPER(CITY) = UPPER(?);";

			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, city.getCity());

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				Country count = new Country(rs);

				City cit = new City(rs);
				cit.setCountry(count);

				city = cit;

			} else {

				sql = "INSERT INTO public.city(\r\n"
						+ "	city, country_id)\r\n"
						+ "	VALUES (?, ?);";

				pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, city.getCity());
				pstmt.setInt(2, city.getCountry().getCountry_id());

				if(pstmt.executeUpdate() > 0 ) {
					rs = pstmt.getGeneratedKeys();
					int id = 0;
					while(rs.next()) {
						id = rs.getInt(1);
						city.setCity_id(id);
					}
				}

			}
		}catch (SQLException e) {
			e.printStackTrace();
		}

		return city;
	}


	public FullAddress checarFa(FullAddress fa) {

		try {
			fa.getCity().setCountry(checaCountry(fa.getCity().getCountry()));
			fa.setCity(checaCity(fa.getCity()));
			fa = insere(fa);
		} catch (Exception e) {

			e.printStackTrace();

		}
		return fa;
	}


	public List<FullAddress> listaTodos() {
		List<FullAddress> listFullAddress = new ArrayList<FullAddress>();
		try(Connection conn = Conexao.abreConexao()) {
			PreparedStatement pstmt = conn.prepareStatement("select "
					+ "		a.address_id, "
					+ "		a.address, "
					+ "		a.address2, "
					+ "		a.phone, "
					+ "		a.district, "
					+ "		a.postal_code,"
					+ "		ci.city_id, \r\n"
					+ "		ci.city, "
					+ "from address as a\r\n"
					+ "	inner join city as ci\r\n"
					+ "		on a.city_id = ci.city_id\r\n"
					);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				listFullAddress.add(initializeAddress(rs));
			}
			Collections.sort(listFullAddress);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listFullAddress;
	}

	public FullAddress buscaPorId(int id) {

		FullAddress add = null;

		try {

			String sql = "select "
					+ "		a.address_id, "
					+ "		a.address, "
					+ "		a.address2, "
					+ "		a.phone, "
					+ "		a.district, "
					+ "		a.postal_code,"
					+ "		ci.city_id, \r\n"
					+ "		ci.city, "
					+ "from address as a\r\n"
					+ "	inner join city as ci\r\n"
					+ "		on a.city_id = ci.city_id\r\n"
					+ " where c.customer_id = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {

				add = initializeAddress(rs);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return add;
	}

	public boolean altera(FullAddress entidade) {
		try(Connection conn = Conexao.abreConexao();) {

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("UPDATE ADDRESS SET ADDRESS = ?,ADDRESS2 = ?,DISTRICT = ?, CITY_ID = ?, POSTAL_CODE = ?, PHONE = ? WHERE ADDRESS_ID  = ?");
			pstmt.setString(1, entidade.getAddress());
			pstmt.setString(2, entidade.getAddress2());
			pstmt.setString(3, entidade.getDistrict());
			pstmt.setInt(4, entidade.getCity().getCity_id());
			pstmt.setString(5, entidade.getPostalCode());
			pstmt.setString(6, entidade.getPhone());
			pstmt.setInt(7, entidade.getId());
			int rowsAffected = pstmt.executeUpdate();

			boolean updated = (rowsAffected > 0)? true : false;
			conn.commit();
			return updated;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}


	private FullAddress insere(FullAddress entidade) {
		FullAddress fa = null;
		try(Connection conn = Conexao.abreConexao()){
			conn.setAutoCommit(false);

			fa = entidade;			

			String sql = "INSERT INTO public.address(\r\n"
					+ "	address, address2, district, city_id, postal_code, phone)\r\n"
					+ "	VALUES (?, ?, ?, ?, ?, ?);";

			PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, fa.getAddress());
			pstmt.setString(2, fa.getAddress2());
			pstmt.setString(3, fa.getDistrict());
			pstmt.setInt(4, fa.getCity().getCity_id());
			pstmt.setString(5, fa.getPostalCode());
			pstmt.setString(6, fa.getPhone());

			if(pstmt.executeUpdate() > 0 ) {
				int id = 0;
				ResultSet rs = pstmt.getGeneratedKeys();
				while(rs.next()) {
					id = rs.getInt(1);
					fa.setId(id);
				}
			}
			conn.commit(); 
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return fa;
	}


	private FullAddress initializeAddress(ResultSet rs) throws SQLException {

		Country count = new Country(rs);
		City city = new City(rs);
		city.setCountry(count);

		FullAddress endereco = new FullAddress(rs);
		endereco.setCity(city);

		return endereco;
	}
}

