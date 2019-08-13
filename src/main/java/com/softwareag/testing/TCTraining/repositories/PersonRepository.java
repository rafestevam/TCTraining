package com.softwareag.testing.TCTraining.repositories;

import java.util.ArrayList;
import java.util.List;

import com.softwareag.testing.TCTraining.models.Person;

public class PersonRepository {

	private static List<Person> persons = new ArrayList<Person>();
	
	public static void save(Person person) {
		persons.add(person);
	}
	
	public static List<Person> getAllPersons() {
		return persons;
	}
	
	public static Person getPersonWithID(int id) {
		Person personReturn = new Person(); 
		persons.forEach(person ->{
			if(person.getId() == id) {
				personReturn.setAge(person.getAge());
				personReturn.setBmi(person.getBmi());
				personReturn.setHeight(person.getHeight());
				personReturn.setId(person.getId());
				personReturn.setName(person.getName());
				personReturn.setWeight(person.getWeight());
				return;
			}
		});
		return personReturn;
	}
	
}
