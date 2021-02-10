package com.qintess.dvdrental.executavel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.qintess.dvdrental.executavel.entidades.Actor;

public class ActorDao implements DaoBase<Actor> {

	private Connection conn;

	public ActorDao (Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Actor> listaTodos() {
		List<Actor> listActor = new ArrayList<>();
		try(Connection conn = Conexao.abreConexao()) {
			PreparedStatement pstmt = conn.prepareStatement("SELECT actor_id, first_name, last_name, last_update\r\n"
					+ "	FROM public.actor;");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				listActor.add(initializeActor(rs));
			}
			Collections.sort(listActor);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listActor;
	}

	@Override
	public Actor buscaPorId(int id) {
		
		Actor act = null;

		try {

			String sql = "SELECT actor_id, first_name, last_name "
					+ "	FROM public.actor WHERE actor_id = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {

				act = initializeActor(rs);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return act;
	}

	private Actor initializeActor(ResultSet rs) throws SQLException {
		Actor act = new Actor(rs);
		return act;
	}

	@Override
	public boolean deleta(int id) {
		try(Connection conn = Conexao.abreConexao()) {
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM public.actor\r\n"
            		+ "	WHERE actor_id = ?;");
            pstmt.setInt(1, id);
            if(pstmt.executeUpdate() == 0) {
                return false;
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
	}

	@Override
	public boolean altera(Actor act) {
		try(Connection conn = Conexao.abreConexao();) {

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("UPDATE public.actor\r\n"
					+ "	SET first_name=?, last_name=? \r\n"
					+ "	WHERE actor_id = ?;");
			pstmt.setString(1, act.getFirst_name());
			pstmt.setString(2, act.getLast_name());
			pstmt.setInt(3, act.getActor_id());

			int rowsAffected = pstmt.executeUpdate();

			boolean updated = (rowsAffected > 0)? true : false;
			conn.commit();
			
			return updated;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean insere(Actor actor) {
		
		try(Connection conn = Conexao.abreConexao()){
			
			conn.setAutoCommit(false);		

			String sql = "INSERT INTO public.actor(\r\n"
					+ "	first_name, last_name)\r\n"
					+ "	VALUES (?, ?);";

			PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, actor.getFirst_name());
			pstmt.setString(2, actor.getLast_name());

			if(pstmt.executeUpdate() > 0 ) {
				int id = 0;
				ResultSet rs = pstmt.getGeneratedKeys();
				while(rs.next()) {
					id = rs.getInt(1);
					actor.setActor_id(id);
				}
			}else {
				throw new SQLException("Não foi possível inserir um Actor");
			}
			conn.commit(); 
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return true;
	} 

	
}
