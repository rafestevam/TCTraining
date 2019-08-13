package com.softwareag.testing.TCTraining.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softwareag.testing.TCTraining.models.Person;
import com.softwareag.testing.TCTraining.services.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private PersonService personService;

	@RequestMapping(value = "/getPersons", method = RequestMethod.GET, produces = "application/json")
	public List<Person> getAllPersons() {
		return personService.getAllPersons();
	}

	@RequestMapping(value = "/getPerson/{cod}", method = RequestMethod.GET, produces = "application/json")
	public Person getPersonWithID(@PathVariable("cod") int cod) {
		return personService.getPersonWithID(cod);
	}

	@RequestMapping(value = "/getPersonsFromCache", method = RequestMethod.GET, produces = "application/json")
	public List<Person> getPersonsFromCache() {
		return personService.getPersonsFromCache();
	}

}
