package com.softwareag.testing.TCTraining.services;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.softwareag.testing.TCTraining.models.Movie;
import com.softwareag.testing.TCTraining.repositories.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {

	public void save(Movie movie) {
		MovieRepository.save(movie);
	}

	public List<Movie> getAllMovies() {
		return MovieRepository.getAllMovies();
	}
	
	@Cacheable(value="movieCache", unless="#result.id != 3")
	public Movie getMovieById(int id) {
		return MovieRepository.getMovieById(id);
	}

}
