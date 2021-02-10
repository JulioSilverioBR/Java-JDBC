package com.qintess.dvdrental.executavel.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao implements AutoCloseable {
	
	private static String url = "jdbc:postgresql://localhost:5432/dvdrental";
	private static String user = "postgres";
	private static String pass = "admin";
	
	private static Connection conn;
	
	public static Connection abreConexao() throws SQLException {
		conn = DriverManager.getConnection(url, user, pass);
		return conn;
	}

	@Override
	public void close() throws Exception {
		conn.close();
	}
	
	

}
