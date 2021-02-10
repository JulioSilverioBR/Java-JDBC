package com.qintess.dvdrental.executavel;

import java.sql.SQLException;

import com.qintess.dvdrental.executavel.dao.ActorDao;
import com.qintess.dvdrental.executavel.dao.CategoryDao;
import com.qintess.dvdrental.executavel.dao.Conexao;
import com.qintess.dvdrental.executavel.dao.FilmDao;
import com.qintess.dvdrental.executavel.entidades.Actor;
import com.qintess.dvdrental.executavel.entidades.Category;
import com.qintess.dvdrental.executavel.entidades.Film;
import com.qintess.dvdrental.executavel.entidades.Language;
import com.qintess.dvdrental.executavel.servicos.FilmActorCategory;

public class AppFilm
{
	public static void main( String[] args )
	{
		try {

			System.out.println("--------Inserindo Film-------");
			FilmDao dao = new FilmDao(Conexao.abreConexao());
			Film filme = new Film();
			
			filme.setTitle("Jogos Vorazes");
			filme.setDescription("Katniss se voluntaria como Tributo no lugar de sua irmã");
			filme.setReleaseYear(2012);
			filme.setRentalDuration(3);
			filme.setRentalRate(7.59);
			filme.setLength(75);

			Language lingua = new Language();
			lingua.setName("Português");

			filme.setLanguage(lingua);
			filme.setReplancementeCost(3.90);
			filme.setRating("G");
			filme.setSpecialFeatures("{cenas deletadas}");
			
			if(dao.insere(filme)) {
				System.out.println("Inserido com sucesso!! \n");
				System.out.println(filme);
			}

			System.out.println("--------Buscando Filme-------");
			Film film = dao.buscaPorId(33308);
            if (film != null) 
                System.out.println(filme);
            else
                System.out.println("filme não encontrado");
			
			
			System.out.println("--------Alterando Filme-------");
			filme.setTitle("Jogos Vozes");
			filme.setDescription("Katniss se voluntaria como Tributo no lugar de sua irmã");
			filme.setReleaseYear(2012);
			filme.setRentalDuration(3);
			filme.setRentalRate(7.59);
			filme.setLength(75);

			lingua.setName("Português");

			filme.setLanguage(lingua);
			filme.setReplancementeCost(3.90);
			filme.setRating("G");
			filme.setSpecialFeatures("{cenas deletadas}");

			if(dao.insere(filme)) {
				System.out.println("Inserido com sucesso!! \n");
				System.out.println(filme);
			}else {
				System.out.println("não foi possivel inserir \n");
			}
			
			System.out.println("-------Insere Film_Category--------");
			CategoryDao cao = new CategoryDao(Conexao.abreConexao());
			Category cat = cao.buscaPorId(8);
			if (cat != null) {
                System.out.println(cat);
			}else {
                System.out.println("Categoria não encontrada");
			}
			
			FilmActorCategory fac = new FilmActorCategory(Conexao.abreConexao());
			fac.insereFilmCategory(filme, cat);

			System.out.println("Filme: " + filme.getTitle() + fac.carregaCate(filme.getFilm_id()));
			
			System.out.println("-------Insere Film_Actor--------");
			ActorDao adao = new ActorDao(Conexao.abreConexao());
			Actor act = adao.buscaPorId(4);
			if (act != null) {
                System.out.println(act);
			}else {
                System.out.println("Ator não encontrada");
			}
			
			fac.insereFilmActor(filme, act);
			System.out.println("Filme: " + filme.getTitle() + fac.carregaActor(filme.getFilm_id()));
			
//			System.out.println("--------Deletando Filme-------");
//			System.out.println(dao.deleta(5));

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}