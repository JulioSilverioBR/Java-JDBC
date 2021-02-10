package com.qintess.dvdrental.executavel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.qintess.dvdrental.executavel.entidades.FullAddress;
import com.qintess.dvdrental.executavel.entidades.Staff;
import com.qintess.dvdrental.executavel.entidades.Store;

public class StoreDao{

	private Connection conn = null;

	public StoreDao(Connection conn) {
		this.conn = conn;
	}

	public List<Store> listaTodos() {
		List<Store> storeList = new ArrayList<Store>();
		try(Connection conn = Conexao.abreConexao()) {

			PreparedStatement pstmt = conn.prepareStatement("SELECT s.store_id, st.staff_id, \r\n"
					+ "st.first_name, st.first_name, st.last_name, a.address_id, a.address, "
					+ "a.district, a.postal_code, a.phone\r\n"
					+ "FROM store AS s\r\n"
					+ "INNER JOIN staff AS st\r\n"
					+ "ON s.manager_staff_id = st.staff_id\r\n"
					+ "INNER JOIN address AS a\r\n"
					+ "ON s.address_id = a.address_id\r\n"
					);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				storeList.add(initializeStore(rs));
			}
			Collections.sort(storeList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return storeList; 
	}

	public Store buscaPorId(int id) {
		Store store = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement("SELECT s.store_id, st.staff_id, \r\n"
					+ "st.first_name, st.first_name, st.last_name, a.address_id, a.address, a.district, a.postal_code, a.phone\r\n"
					+ "FROM store AS s\r\n"
					+ "INNER JOIN staff AS st\r\n"
					+ "ON s.manager_staff_id = st.staff_id\r\n"
					+ "INNER JOIN address AS a\r\n"
					+ "ON s.address_id = a.address_id\r\n"
					+ "WHERE s.store_id = ?");
			
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				store = initializeStore(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return store;
	}

	private Store initializeStore(ResultSet rs) throws SQLException {
		
		
		Staff staff = new Staff(rs);
		
		FullAddress endereco = new FullAddress(rs);
		
		Store store = new Store(rs);
		store.setManage_staff(staff);
		store.setAddress(endereco);
		
		return store;
		
	}
}
