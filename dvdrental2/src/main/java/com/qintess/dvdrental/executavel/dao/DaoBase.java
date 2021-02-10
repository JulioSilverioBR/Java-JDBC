package com.qintess.dvdrental.executavel.dao;

import java.util.List;

public interface DaoBase<T> {

	List<T> listaTodos();
	T buscaPorId(int id);
	boolean deleta(int id);
	boolean altera(T entidade);
	boolean insere(T entidade);
	
}
