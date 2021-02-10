package com.qintess.dvdrental.executavel;

import java.sql.SQLException;

import com.qintess.dvdrental.executavel.dao.Conexao;
import com.qintess.dvdrental.executavel.dao.CustomerDao;
import com.qintess.dvdrental.executavel.entidades.City;
import com.qintess.dvdrental.executavel.entidades.Country;
import com.qintess.dvdrental.executavel.entidades.Customer;
import com.qintess.dvdrental.executavel.entidades.FullAddress;
import com.qintess.dvdrental.executavel.entidades.Store;

public class AppCustomer 
{
	public static void main( String[] args )
	{
		try {
			
			System.out.println("--------Inserindo Customer-------");
			CustomerDao dao = new CustomerDao(Conexao.abreConexao());
			Customer customer = new Customer();
			customer.setFirstName("erick");
			customer.setLastName("rodrigues");
			customer.setEmail("erick@.com");
			customer.setActive(1);
			customer.getStatus();
			
			FullAddress end = new FullAddress();
			end.setAddress("Rua da varzea");
			end.setDistrict("Zona Leste");
			end.setPhone("11111");
			end.setPostalCode("2222");   

			Country pais = new Country("Brasil");
			City cidade = new City("SÃO PaUlo", pais);
			end.setCity(cidade);
			
			Store store = new Store();
			store.setStore_id(1);
			customer.setStore(store);

			customer.setFullAddress(end);
			if(dao.insere(customer)) {
				System.out.println("Inserido com sucesso!! \n");
				System.out.println(customer);
			}else {
				System.out.println("não foi possivel inserir \n");
			}
		

			System.out.println("--------Buscando Customer-------");
			Customer busca = dao.buscaPorId(956);
            if (busca != null) 
                System.out.println(customer);
            else
                System.out.println("Customer não encontrado");
			
			System.out.println("\n--------Alterando Customer-------");
			customer.setFirstName("Natasha");
			customer.setLastName("Santana");
			customer.setEmail("nathyyy@gmail.com");
			customer.setActive(1);
			customer.getStatus();
			
			end.setAddress("Rua dos Laranjais");
			end.setDistrict("Bairro do Limoreiro");
			end.setPhone("945644");
			end.setPostalCode("0281");   

			pais.setCountry("Neverland");
			cidade.setCity("Cidade das Conchas");
			cidade.setCountry(pais);
			end.setCity(cidade);
			
			store.setStore_id(2);
			customer.setStore(store);

			customer.setFullAddress(end);
			if(dao.altera(customer)) {
				System.out.println("Alterado com sucesso com sucesso!! \n");
				System.out.println(customer);
			}else {
				System.out.println("Não foi possivel alterar\n");
			}
			
			
			System.out.println("--------Deletando Customer-------");
            if(dao.deleta(1)) {
                System.out.println("Deletado com sucesso");
            }else {
                System.out.println("Não foi possivel deletar");
            }
            
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}
}