package com.qintess.dvdrental.executavel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.qintess.dvdrental.executavel.entidades.Actor;
import com.qintess.dvdrental.executavel.entidades.Category;
import com.qintess.dvdrental.executavel.entidades.Film;
import com.qintess.dvdrental.executavel.entidades.Language;
import com.qintess.dvdrental.executavel.servicos.ChecagemServico;
import com.qintess.dvdrental.executavel.servicos.FilmActorCategory;


public class FilmDao implements DaoBase<Film> {

	private Connection conn;

	public FilmDao (Connection conn) {
		this.conn = conn;
	} 

	@Override
	public List<Film> listaTodos() {
		List<Film> filmList = new ArrayList<Film>();
		try(Connection conn = Conexao.abreConexao()) {

			PreparedStatement pstmt = conn.prepareStatement(
					"select "
							+ "f.film_id, "
							+ "f.title, "
							+ "f.description, "
							+ "f.release_year,"
							+ "f.language_id, "
							+ "f.rental_duration, "
							+ "f.rental_rate, "
							+ "f.length, "
							+ "f.replacement_cost, "
							+ "f.rating, "
							+ "f.special_features, "
							+ "l.name "
							+ "FROM film f "
							+ "INNER JOIN language as l "
							+ "ON f.language_id = l.language_id "
					);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				filmList.add(initializeFilm(rs));

			}


		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filmList; 
	}

	@Override
	public Film buscaPorId(int id) {

		Film filme = null;

		try {


			String sql = "select "
					+ "f.film_id, "
					+ "f.title, "
					+ "f.description, "
					+ "f.release_year,"
					+ "f.language_id, "
					+ "f.rental_duration, "
					+ "f.rental_rate, "
					+ "f.length, "
					+ "f.replacement_cost, "
					+ "f.rating, "
					+ "f.special_features, "
					+ "l.name "
					+ "FROM film f "
					+ "INNER JOIN language as l "
					+ "ON f.language_id = l.language_id "
					+ "WHERE f.film_id = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {

				filme = initializeFilm(rs);

			}



		} catch (Exception e) {
			e.printStackTrace();
		}
		return filme;
	}

	private Film initializeFilm(ResultSet rs) throws SQLException {

		Film filme = new Film(rs);
		Language lingua = new Language(rs);
		filme.setLanguage(lingua);


		FilmActorCategory fac = new FilmActorCategory(Conexao.abreConexao());

		List<Actor> actors = fac.carregaActor(filme.getFilm_id());

		for (Actor actor : actors) {
			filme.addActor(actor);
		}

		List<Category> category = fac.carregaCate(filme.getFilm_id());

		for (Category cat : category) {
			filme.addCategory(cat);
		}

		return filme;

	}

	@Override
	public boolean deleta(int id) {
		try(Connection conn = Conexao.abreConexao()) {
			conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM film_actor WHERE film_id = ?");
            pstmt.setInt(1, id);
            if(pstmt.executeUpdate() > 0) {
            	pstmt = conn.prepareStatement("DELETE FROM film_category WHERE film_id = ?");
                pstmt.setInt(1, id);
                if(pstmt.executeUpdate() > 0) {
                	
                	pstmt = conn.prepareStatement("DELETE FROM INVENTORY WHERE film_id = ?");
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                	
                	pstmt = conn.prepareStatement("DELETE FROM film WHERE film_id = ?");
                    pstmt.setInt(1, id);
                    if(pstmt.executeUpdate() > 0) {
                        conn.commit();
                        return true;
                    }
                }
            }else{
            	pstmt = conn.prepareStatement("DELETE FROM film_category WHERE film_id = ?");
                pstmt.setInt(1, id);
                if(pstmt.executeUpdate() > 0) {
                	pstmt = conn.prepareStatement("DELETE FROM INVENTORY WHERE film_id = ?");
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                	
                	pstmt = conn.prepareStatement("DELETE FROM film WHERE film_id = ?");
                    pstmt.setInt(1, id);
                    if(pstmt.executeUpdate() > 0) {
                        conn.commit();
                        return true;
                    }
                }else {
                	pstmt = conn.prepareStatement("DELETE FROM INVENTORY WHERE film_id = ?");
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                	
                	pstmt = conn.prepareStatement("DELETE FROM film WHERE film_id = ?");
                    pstmt.setInt(1, id);
                    if(pstmt.executeUpdate() > 0) {
                        conn.commit();
                        return true;
                    }
                }
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean altera(Film entidade) {

		try(Connection conn = Conexao.abreConexao()) {

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE film SET title = ?, description = ?, release_year = ?, "
							+ "language_id = ?, rental_duration = ?, rental_rate = ?, "
							+ "length = ?, replacement_cost = ?, rating = ?::mpaa_rating, special_features = ?::text[] "
							+ "WHERE film_id  = ?");
			pstmt.setString(1, entidade.getTitle());
			pstmt.setString(2, entidade.getDescription());
			pstmt.setInt(3, entidade.getReleaseYear());
			pstmt.setInt(4, entidade.getLanguage().getLanguageId());
			pstmt.setInt(5, entidade.getRentalDuration());
			pstmt.setDouble(6, entidade.getRentalRate());
			pstmt.setInt(7, entidade.getLength());
			pstmt.setDouble(8, entidade.getReplancementeCost());
			pstmt.setString(9, entidade.getRating());
			pstmt.setString(10, entidade.getSpecialFeatures());
			pstmt.setInt(11, entidade.getFilm_id());

			int rowsAffected = pstmt.executeUpdate();

			boolean updated = (rowsAffected > 0) ? true : false;
			conn.commit();

			System.out.println("alterado com sucesso! ");
			return updated;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public boolean insere(Film filme) {

		try(Connection conn = Conexao.abreConexao()){

			conn.setAutoCommit(false);
			ChecagemServico checa = new ChecagemServico(Conexao.abreConexao());

			filme.setLanguage(checa.checaLang(filme.getLanguage()));

			String sql = "INSERT INTO film (title, description, release_year, "
					+ "language_id, rental_duration, rental_rate, length, "
					+ "replacement_cost, rating, special_features) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?::mpaa_rating, ?::text[]);";

			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, filme.getTitle());
			pstmt.setString(2, filme.getDescription());
			pstmt.setInt(3, filme.getReleaseYear());
			pstmt.setInt(4, filme.getLanguage().getLanguageId());
			pstmt.setInt(5, filme.getRentalDuration());
			pstmt.setDouble(6, filme.getRentalRate());
			pstmt.setInt(7, filme.getLength());
			pstmt.setDouble(8, filme.getReplancementeCost());
			pstmt.setString(9, filme.getRating());
			pstmt.setString(10, filme.getSpecialFeatures());

			if(pstmt.executeUpdate() > 0 ) {
				int id = 0;
				ResultSet rs = pstmt.getGeneratedKeys();
				while(rs.next()) {
					id = rs.getInt(1);
					filme.setFilm_id(id);
				}
			}else {
				throw new SQLException("Não foi possivel inserir o Customer");
			}

			conn.commit(); 

		}catch(SQLException e) {
			e.printStackTrace();
			return false;

		}

		return true;
	}	

}