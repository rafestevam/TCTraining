package com.softwareag.testing.TCTraining.services;

import java.util.List;

import com.softwareag.testing.TCTraining.models.Person;

public interface PersonService {
	
	public void save(Person person);
	
	public List<Person> getAllPersons();
	
	public Person getPersonWithID(int id);
	
	public List<Person> getPersonsFromCache();

}
