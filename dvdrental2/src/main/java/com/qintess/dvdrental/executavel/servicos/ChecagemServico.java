package com.qintess.dvdrental.executavel.servicos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.qintess.dvdrental.executavel.entidades.Category;
import com.qintess.dvdrental.executavel.entidades.Language;

public class ChecagemServico {

	private Connection conn;

	public ChecagemServico (Connection conn) {
		this.conn = conn;
	} 
	
	public Language checaLang(Language lang) {

		try {

			String sql = "SELECT language_id, name "
					+ "	FROM public.language WHERE UPPER(name) = UPPER(?)";

			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, lang.getName());

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				Language language = new Language(rs);
				lang = language;

			} else {

				sql = "INSERT INTO public.language(\r\n"
						+ "	name)\r\n"
						+ "	VALUES (?);";

				pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, lang.getName());
				if(pstmt.executeUpdate() > 0 ) {
					rs = pstmt.getGeneratedKeys();
					int id = 0;
					while(rs.next()) {
						id = rs.getInt(1);
						lang.setLanguageId(id);
					}
				}

			}
		}catch (SQLException e) {
			e.printStackTrace();
		}

		return lang;
	}
	
	public Category checaCategory(Category cat) {

		try {

			String sql = "SELECT category_id, name\r\n"
					+ "	FROM public.category WHERE UPPER(name) = UPPER(?)";

			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, cat.getCategory());

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				Category cate = new Category(rs);
				cat = cate;

			} else {

				sql = "INSERT INTO public.category(\r\n"
						+ "	name)\r\n"
						+ "	VALUES (?);";

				pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, cat.getCategory());
				if(pstmt.executeUpdate() > 0 ) {
					rs = pstmt.getGeneratedKeys();
					int id = 0;
					while(rs.next()) {
						id = rs.getInt(1);
						cat.setCategory_id(id);
					}
				}

			}
		}catch (SQLException e) {
			e.printStackTrace();
		}

		return cat;
		
	}
	
}
