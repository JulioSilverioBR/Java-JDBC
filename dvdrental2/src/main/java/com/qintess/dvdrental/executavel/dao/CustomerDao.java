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
import com.qintess.dvdrental.executavel.entidades.Customer;
import com.qintess.dvdrental.executavel.entidades.FullAddress;
import com.qintess.dvdrental.executavel.entidades.Store;


public class CustomerDao implements DaoBase<Customer>{

	private Connection conn;

	public CustomerDao(Connection conn) {
		this.conn = conn;
	} // no caso de connectar aq, o try with resources se tornaria inutil

	@Override
	public Customer buscaPorId(int id) {

		Customer cust = null;

		try {

			String sql = "select "
					+ "		c.customer_id, "
					+ "		c.first_name, "
					+ "		c.last_name, "
					+ "		c.email, "
					+ "		c.active, "
					+ "		c.store_id,"	
					+ "		a.address_id, "
					+ "		a.address, "
					+ "		a.phone, "
					+ "		a.district, "
					+ "		a.postal_code,"
					+ "		ci.city_id, \r\n"
					+ "		ci.city, "
					+ "		co.country_id, \r\n"
					+ "		co.country "
					+ "from customer as c\r\n"
					+ "	inner join address as a\r\n"
					+ "		on c.address_id = a.address_id\r\n"
					+ "	inner join city as ci\r\n"
					+ "		on a.city_id = ci.city_id\r\n"
					+ "	inner join country as co\r\n"
					+ "		on ci.country_id = co.country_id\r\n"
					+ " where c.customer_id = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {

				cust = initializeCustomer(rs);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cust;
	}

	@Override
	public boolean insere(Customer customer) {


		try(Connection conn = Conexao.abreConexao()){

			conn.setAutoCommit(false);
			FullAddressDao fDao = new FullAddressDao(Conexao.abreConexao());
			customer.setFullAddress(fDao.checarFa(customer.getFullAddress()));
			String sql = " INSERT INTO public.customer(\r\n"
					+ " store_id, first_name, last_name, email, address_id, active)"
					+ "	VALUES (?, ?, ?, ?, ?, ?)";

			PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, customer.getStore().getStore_id());
			pstmt.setString(2, customer.getFirstName());
			pstmt.setString(3, customer.getLastName());
			pstmt.setString(4, customer.getEmail());
			pstmt.setInt(5, customer.getFullAddress().getId());
			pstmt.setInt(6, customer.getActive());

			if(pstmt.executeUpdate() > 0 ) {
				int id = 0;
				ResultSet rs = pstmt.getGeneratedKeys();
				while(rs.next()) {
					id = rs.getInt(1);
					customer.setCustomerId(id);
				}
			}else {
				throw new SQLException("Não foi possivel inserir o Customer");
			}
			conn.commit(); 
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Customer> listaTodos() {
		List<Customer> customerList = new ArrayList<Customer>();
		try(Connection conn = Conexao.abreConexao()) {

			PreparedStatement pstmt = conn.prepareStatement("select "
					+ "		c.customer_id, "
					+ "		c.first_name, "
					+ "		c.last_name, "
					+ "		c.email, "
					+ "		c.active, "
					+ "		c.store_id,"	
					+ "		a.address_id, "
					+ "		a.address, "
					+ "		a.phone, "
					+ "		a.district, "
					+ "		a.postal_code,"
					+ "		ci.city_id, \r\n"
					+ "		ci.city, "
					+ "		co.country_id, \r\n"
					+ "		co.country "
					+ "from customer as c\r\n"
					+ "	inner join address as a\r\n"
					+ "		on c.address_id = a.address_id\r\n"
					+ "	inner join city as ci\r\n"
					+ "		on a.city_id = ci.city_id\r\n"
					+ "	inner join country as co\r\n"
					+ "		on ci.country_id = co.country_id\r\n"
					);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				customerList.add(initializeCustomer(rs));
			}
			Collections.sort(customerList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerList; 
	}

	@Override
	public boolean deleta(int id) {

		try(Connection conn = Conexao.abreConexao()) {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("SELECT ACTIVE FROM CUSTOMER WHERE CUSTOMER_ID = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
                while(rs.next()) {
                    if(rs.getInt("active") == 0) {
                        return false;
                    }else {
                        pstmt = conn.prepareStatement("UPDATE CUSTOMER SET ACTIVEBOOL  = FALSE, ACTIVE = 0 WHERE CUSTOMER_ID = ?");
                        pstmt.setInt(1, id);
                        pstmt.executeUpdate();
                        conn.commit();
                        return true;
                    }
                }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

	@Override
	public boolean altera(Customer entidade) {
		try(Connection conn = Conexao.abreConexao();) {

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("UPDATE CUSTOMER SET STORE_ID = ?,FIRST_NAME = ?, LAST_NAME = ?, EMAIL = ?, ACTIVE = ?,ADDRESS_ID = ? WHERE CUSTOMER_ID  = ?");
			pstmt.setInt(1, entidade.getStore().getStore_id());
			pstmt.setString(2, entidade.getFirstName());
			pstmt.setString(3, entidade.getLastName());
			pstmt.setString(4, entidade.getEmail());
			pstmt.setInt(5, entidade.getActive());
			pstmt.setInt(6, entidade.getFullAddress().getId());
			pstmt.setInt(7, entidade.getCustomerId());
			int rowsAffected = pstmt.executeUpdate();

			boolean updated = (rowsAffected > 0)? true : false;
			conn.commit();
			return updated;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private Customer initializeCustomer(ResultSet rs) throws SQLException {
		Country count = new Country(rs);
		City city = new City(rs);
		city.setCountry(count);

		FullAddress endereco = new FullAddress(rs);
		endereco.setCity(city);

		Store store = new Store(rs);

		Customer cust = new Customer(rs);
		cust.setFullAddress(endereco);
		cust.setStore(store);
		cust.getStatus();

		return cust;
	}
}