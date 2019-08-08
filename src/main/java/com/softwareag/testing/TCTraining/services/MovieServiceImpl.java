package com.softwareag.testing.TCTraining.services;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.softwareag.testing.TCTraining.models.Movie;
import com.softwareag.testing.TCTraining.repositories.MovieRepository;

@Service
@CacheConfig(cacheNames= {"testCache"})
public class MovieServiceImpl implements MovieService {
	
	@CachePut(key="#movie.id")
	public void save(Movie movie) {
		MovieRepository.save(movie);
	}
	
	@Cacheable(key="#result.id", unless="#result.id==null")
	public List<Movie> getAllMovies() {
		return MovieRepository.getAllMovies();
	}
	
	//@Cacheable(value="movieCache", unless="#result.id != 3")
	@Cacheable(key="#result.id", unless="#result.id==null")
	public Movie getMovieById(int id) {
		return MovieRepository.getMovieById(id);
	}

}
