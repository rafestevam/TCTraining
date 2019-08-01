package com.softwareag.testing.TCTraining.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softwareag.testing.TCTraining.models.Movie;
import com.softwareag.testing.TCTraining.services.MovieService;

@RestController
@RequestMapping("/movie")
public class MovieController {
	
	private static Logger log = LoggerFactory.getLogger(MovieController.class);
	
	@Autowired
	private MovieService movieService;
	
	@RequestMapping(value="/getMovies", method=RequestMethod.GET, produces="application/json")
	public List<Movie> getAllMovies(){
		return movieService.getAllMovies();
	}
	
	@RequestMapping(value="/getMovie/{cod}", method=RequestMethod.GET, produces="application/json")
	public Movie getMovieById(@PathVariable("cod") int cod) {
		log.info("Chamando metodo...");
		return movieService.getMovieById(cod);
	}
	
}
