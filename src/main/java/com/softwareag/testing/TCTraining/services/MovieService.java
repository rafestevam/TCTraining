package com.softwareag.testing.TCTraining.services;

import java.util.List;

import com.softwareag.testing.TCTraining.models.Movie;

public interface MovieService {
	
	public void save(Movie movie);
	
	public List<Movie> getAllMovies();
	
	public Movie getMovieById(int id);

}
