package com.qintess.dvdrental.executavel.servicos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qintess.dvdrental.executavel.dao.Conexao;
import com.qintess.dvdrental.executavel.entidades.Actor;
import com.qintess.dvdrental.executavel.entidades.Category;
import com.qintess.dvdrental.executavel.entidades.Film;

public class FilmActorCategory {

	private Connection conn;

	public FilmActorCategory (Connection conn) {
		this.conn = conn;
	} 
	
	
	public List<Actor> carregaActor(int idFilm) {
		List<Actor> ator = new ArrayList<>();
		try {
			
			String sql = "SELECT act.* FROM actor AS act\r\n"
					+ "INNER JOIN film_actor AS f_ac\r\n"
					+ "ON f_ac.actor_id = act.actor_id\r\n"
					+ "WHERE f_ac.film_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idFilm);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ator.add(new Actor(rs));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return ator;
	}
	
	public List<Category> carregaCate(int idFilm) {
		
		List<Category> cate = new ArrayList<>();
		
		try {
			
			String sql = "SELECT cat.* FROM category AS cat\r\n"
					+ "INNER JOIN film_category AS f_cat\r\n"
					+ "ON f_cat.category_id = cat.category_id\r\n"
					+ "WHERE f_cat.film_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idFilm);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				cate.add(new Category(rs));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return cate;
	}
	
	public boolean insereFilmActor(Film film, Actor act) {
		
		try (Connection conn = Conexao.abreConexao()){
			
			this.conn.setAutoCommit(false);
			
			String sql = "INSERT INTO public.film_actor(\r\n"
					+ " film_id, actor_id)\r\n"
					+ "	VALUES (?, ?)";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, film.getFilm_id());
			pstmt.setInt(2, act.getActor_id());
			
			if(pstmt.executeUpdate() > 0 ) {
				this.conn.commit();
				return true;
			}else {
				throw new SQLException("Não foi possivel inserir o Film_actor");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean insereFilmCategory(Film film, Category cat) {
		
		try (Connection conn = Conexao.abreConexao()){
			
			this.conn.setAutoCommit(false);
			
			String sql = "INSERT INTO public.film_category(\r\n"
					+ "	film_id, category_id)\r\n"
					+ "	VALUES (?, ?)";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, film.getFilm_id());
			pstmt.setInt(2, cat.getCategory_id());
			
			if(pstmt.executeUpdate() > 0 ) {
				this.conn.commit();
				return true;
			}else {
				throw new SQLException("Não foi possivel inserir o Film_actor");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
}
