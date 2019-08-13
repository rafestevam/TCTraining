package com.softwareag.testing.TCTraining.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.softwareag.testing.TCTraining.models.Person;
import com.softwareag.testing.TCTraining.repositories.PersonCacheRepository;
import com.softwareag.testing.TCTraining.repositories.PersonRepository;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Service
@CacheConfig(cacheNames = { "personCache" })
public class PersonServiceImpl implements PersonService {

	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	private PersonCacheRepository cacheRepository;
	
	private static Logger logger = LoggerFactory.getLogger(PersonService.class);

	@CachePut(key = "#person.id")
	public void save(Person person) {
		double bmi;
		bmi = (person.getHeight() / 100) / Math.pow(person.getHeight(), 2); //((height / 100) ^ 2);
		logger.info("O IMC de " + person.getName() + " é " + bmi);
		person.setBmi(bmi);
		PersonRepository.save(person);
	}

	public List<Person> getAllPersons() {
		List<Person> personsReturn = PersonRepository.getAllPersons();
		Cache cache = cacheManager.getCache("personCache");

		personsReturn.forEach(person -> {
			cache.put(new Element(person.getId(), person));
		});

		return personsReturn;
	}

	@Cacheable(cacheNames = { "personCache" })
	public Person getPersonWithID(int id) {
		return PersonRepository.getPersonWithID(id);
	}
	
	public List<Person> getPersonsFromCache(){
		return cacheRepository.findInCacheByBMI();
	}

}
