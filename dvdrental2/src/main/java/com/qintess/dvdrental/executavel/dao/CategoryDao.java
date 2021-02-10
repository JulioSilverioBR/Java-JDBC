package com.qintess.dvdrental.executavel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.qintess.dvdrental.executavel.entidades.Category;

public class CategoryDao implements DaoBase<Category> {

	private Connection conn;

	public CategoryDao (Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Category> listaTodos() {
		List<Category> listCat = new ArrayList<>();
		try(Connection conn = Conexao.abreConexao()) {
			PreparedStatement pstmt = conn.prepareStatement("SELECT category_id, name "
					+ "	FROM public.category");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				listCat.add(initializeCategory(rs));
			}
			Collections.sort(listCat);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listCat;
	}

	@Override
	public Category buscaPorId(int id) {
		
		Category cat = null;

		try {

			String sql = "SELECT category_id, name\r\n"
					+ "	FROM public.category WHERE category_id = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {

				cat = initializeCategory(rs);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cat;
	}

	@Override
	public boolean deleta(int id) {
		try(Connection conn = Conexao.abreConexao()) {
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM public.category\r\n"
            		+ "	WHERE category_id = ?;");
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
	public boolean altera(Category cat) {
		try(Connection conn = Conexao.abreConexao();) {

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("UPDATE public.category\r\n"
					+ "	SET name=? "
					+ "	WHERE category_id = ?");
			pstmt.setString(1, cat.getCategory());
			pstmt.setInt(2, cat.getCategory_id());

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
	public boolean insere(Category cat) {
		Category cate = null;
		try(Connection conn = Conexao.abreConexao()){
			
			conn.setAutoCommit(false);

			cate = cat;			

			String sql = "INSERT INTO public.category(\r\n"
					+ "	name)\r\n"
					+ "	VALUES (?)";

			PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, cate.getCategory());

			if(pstmt.executeUpdate() > 0 ) {
				int id = 0;
				ResultSet rs = pstmt.getGeneratedKeys();
				while(rs.next()) {
					id = rs.getInt(1);
					cate.setCategory_id(id);
				}
			}
			conn.commit(); 
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return true;
	} 

	
	private Category initializeCategory(ResultSet rs) throws SQLException {
		Category cat = new Category(rs);
		return cat;
	}
}
