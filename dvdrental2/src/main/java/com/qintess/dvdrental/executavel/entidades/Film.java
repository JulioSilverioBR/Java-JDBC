package com.qintess.dvdrental.executavel.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Film {

	private Integer film_id;
	private String title;
	private String description;
	private Integer releaseYear;
	private int rentalDuration;
	private double rentalRate;
	private int length;
	private double replancementCost;
	private String rating;
	private String specialFeatures;
	
	private List<Actor> listActor = new ArrayList<>();
	private List<Category> listCategory = new ArrayList<>();
	private Language language;

	public Film() {}
	
	public Film(Integer film_id, String title, String description, int releaseYear, int rentalDuration,
			double rentalRate, int length, double replancementeCost, String rating, String specialFeatures, 
			Language language) {
		this.film_id = film_id;
		this.title = title;
		this.description = description;
		this.releaseYear = releaseYear;
		this.rentalDuration = rentalDuration;
		this.rentalRate = rentalRate;
		this.length = length;
		this.replancementCost = replancementeCost;
		this.rating = rating;
		this.specialFeatures = specialFeatures;
		this.language = language;
	}

	public Film(ResultSet rs) throws SQLException {
		this.film_id = rs.getInt("film_id");
		this.title = rs.getString("title");
		this.description = rs.getString("description");
		this.releaseYear = rs.getInt("release_year");
		this.rentalDuration = rs.getInt("rental_duration");
		this.rentalRate = rs.getDouble("rental_rate");
		this.length = rs.getInt("length");
		this.replancementCost = rs.getDouble("replacement_cost");
		this.rating = rs.getString("rating");
		this.specialFeatures = rs.getString("special_features");
	}

	public Integer getFilm_id() {
		return film_id;
	}

	public void setFilm_id(Integer film_id) {
		this.film_id = film_id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setReleaseYear(Integer releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}
	public int getRentalDuration() {
		return rentalDuration;
	}
	public void setRentalDuration(int rentalDuration) {
		this.rentalDuration = rentalDuration;
	}
	public double getRentalRate() {
		return rentalRate;
	}
	public void setRentalRate(double rentalRate) {
		this.rentalRate = rentalRate;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public double getReplancementeCost() {
		return replancementCost;
	}
	public void setReplancementeCost(double replancementeCost) {
		this.replancementCost = replancementeCost;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getSpecialFeatures() {
		return specialFeatures;
	}
	public void setSpecialFeatures(String specialFeatures) {
		this.specialFeatures = specialFeatures;
	}
	
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
	
	public List<Actor> getListActor() {
		return listActor;
	}
	
	public void addActor(Actor act) {
		listActor.add(act);
	}
	
	public void removeActor(Actor act) {
		listActor.remove(act);
	}
	
	public List<Category> getListCategory() {
		return listCategory;
	}

	public void addCategory(Category cat) {
		listCategory.add(cat);
	}
	
	public void removeCategory(Category cat) {
		listCategory.remove(cat);
	}
	
	public int compareTo(Film o) {
		return this.film_id.compareTo(o.film_id);
	}

	@Override
	public String toString() {
		
			StringBuilder sb = new StringBuilder();
			sb.append("Filme " + "\n");
			sb.append("Título: ");
			sb.append(title + "\n");
			sb.append("Descrição: ");
			sb.append(description + "\n");
			sb.append("Lançamento: ");
			sb.append(releaseYear + "\n");
			sb.append("Duração do aluguel: ");
			sb.append(rentalDuration + " dias \n");
			sb.append("Taxa de aluguel: ");
			sb.append(rentalRate + "\n");
			sb.append("Duração do filme: ");
			sb.append(length + " minutos \n");
			sb.append("Custo de reposição: ");
			sb.append(replancementCost + "\n");
			sb.append("Classificação indicativa: ");
			sb.append(rating + "\n");
			sb.append("Características: ");
			sb.append(specialFeatures + "\n");
			sb.append("Idioma: ");
			sb.append(language + "\n");
			sb.append("Lista de atores: ");
			sb.append(listActor + "\n");
			sb.append("Lista de categorias: ");
			sb.append(listCategory + "\n");

			return sb.toString();
		
		
		
	}
	
	
}
