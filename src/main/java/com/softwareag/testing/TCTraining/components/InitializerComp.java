package com.softwareag.testing.TCTraining.components;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.softwareag.testing.TCTraining.models.Person;
import com.softwareag.testing.TCTraining.services.MovieService;
import com.softwareag.testing.TCTraining.services.PersonService;

@Component
public class InitializerComp {
	
	@Autowired
	MovieService movieService;

	@Autowired
	PersonService personService;
	
	//@PostConstruct
//	private void initializeAPI() {
		
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
		
//	}
	
	@PostConstruct
	private void initializePersons() {
		Person person1 = new Person(1, "Rafael Estevam", 32, 8750, 188);
		personService.save(person1);
		
		Person person2 = new Person(2, "John Stewart", 28, 9600, 168);
		personService.save(person2);
		
		Person person3 = new Person(3, "Hollie James", 45, 7569, 194);
		personService.save(person3);
		
		Person person4 = new Person(4, "Eric Barbosa", 39, 8450, 180);
		personService.save(person4);
		
		Person person5 = new Person(5, "Sofia Allen", 21, 5740, 165);
		personService.save(person5);
		
		Person person6 = new Person(6, "Paula Patterson", 44, 6250, 175);
		personService.save(person6);
		
		Person person7 = new Person(7, "Jayden Hansen", 19, 7950, 179);
		personService.save(person7);
		
		Person person8 = new Person(8, "Alexis Moreno", 28, 11500, 178);
		personService.save(person8);
		
		Person person9 = new Person(9, "Robert Johnson", 35, 8690, 181);
		personService.save(person9);
	}
	
}
