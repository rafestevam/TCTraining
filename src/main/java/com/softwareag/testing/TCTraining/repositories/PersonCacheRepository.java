package com.softwareag.testing.TCTraining.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.softwareag.testing.TCTraining.models.Person;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;
import net.sf.ehcache.search.expression.Criteria;

@Component
public class PersonCacheRepository {
	
	@Autowired
	private CacheManager cacheManager;
	
	public List<Person> findInCacheByBMI() {
		
		List<Person> personList = new ArrayList<Person>();
		Cache cache = cacheManager.getCache("personCache");
		Query query = cache.createQuery();
		query.includeValues(); //Habilita a query para buscar pelos valores
		
		//Attribute<Double> bmi = cache.getSearchAttribute("bmi");
		//Criteria anorectic = bmi.lt(1.85);
		//Criteria obese = bmi.gt(3.00);
		//Criteria criteria = anorectic.or(obese);

		Attribute<Integer> height = cache.getSearchAttribute("height");
		Criteria higher = height.gt(180);
		query.addCriteria(higher);
		
		Results results = query.execute();
		List<Result> resultList = results.all();
		resultList.forEach(result ->{
			personList.add((Person)result.getValue());
		});
		
		return personList;
		
	}
	
}
