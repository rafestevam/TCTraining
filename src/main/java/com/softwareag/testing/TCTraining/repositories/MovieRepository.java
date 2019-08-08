package com.softwareag.testing.TCTraining.repositories;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;

import com.softwareag.testing.TCTraining.models.Movie;

@CacheConfig(cacheNames="movieCache")
public class MovieRepository {
	
	private static List<Movie> movies = new ArrayList<Movie>();
	
	private static Logger log = LoggerFactory.getLogger(MovieRepository.class);
	
	//@CachePut(value = "testCache", key="#movie.id")
	public static void save(Movie movie) {
		movies.add(movie);
	}
	
	public static List<Movie> getAllMovies() {
		return movies;
	}
	
	//@CachePut(value="movieCache", key="#result.id", condition="#result.id == 3")
	public static Movie getMovieById(int cod) {
		log.info("Executando método...");
		Movie movie = new Movie();
		movies.forEach(mov -> {
			if (mov.getId() == cod) {
				movie.setId(mov.getId());
				movie.setDirector(mov.getDirector());
				movie.setName(mov.getName());
			}
		});
		return movie;
	}

}
