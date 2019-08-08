package com.softwareag.testing.TCTraining.components;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.softwareag.testing.TCTraining.services.MovieService;

@Component
public class InitializerComp {
	
	@Autowired
	MovieService movieService;

	@PostConstruct
	private void initializeAPI() {
		
//		Movie movie1 = new Movie();
//		movie1.setId(1);
//		movie1.setName("O Poderoso Chefão");
//		movie1.setDirector("Francis Ford Copolla");
//		movieService.save(movie1);
//
//		Movie movie2 = new Movie();
//		movie2.setId(2);
//		movie2.setName("Kill Bill");
//		movie2.setDirector("Quentin Tarantino");
//		movieService.save(movie2);
//		
//		Movie movie3 = new Movie();
//		movie3.setId(3);
//		movie3.setName("Taxi Driver");
//		movie3.setDirector("Martin Scorsese");
//		movieService.save(movie3);
		
	}
	
}
