package com.softwareag.testing.TCTraining.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softwareag.testing.TCTraining.models.Movie;
import com.softwareag.testing.TCTraining.services.MovieService;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@RestController
@RequestMapping("/movie")
public class MovieController {

	private static Logger log = LoggerFactory.getLogger(MovieController.class);

	@Autowired
	private MovieService movieService;

	// O objeto CacheManager manipula um conjunto de caches em uma só JVM
	// Para criar um CacheManager, usar: CacheManager cacheManager = new
	// CacheManager("ehcache.xml")
	// Como existe o uso do Spring Boot, o EHCACHE é aderente à especificação
	// JSR-107 e o proprio
	// Spring consegue construir o CacheManager automaticamente, bastando ao
	// desenvolvedor trabalhar
	// somente com a injeção de dependencias
	@Autowired
	private CacheManager cacheManager;

	@RequestMapping(value = "/getMovies", method = RequestMethod.GET, produces = "application/json")
	public List<Movie> getAllMovies() {
		return movieService.getAllMovies();
	}

	@RequestMapping(value = "/getMovie/{cod}", method = RequestMethod.GET, produces = "application/json")
	public Movie getMovieById(@PathVariable("cod") int cod) {
		log.info("Chamando metodo...");
		return movieService.getMovieById(cod);
	}

	@RequestMapping(value = "/cacheMovie", method = RequestMethod.POST, produces = "application/json")
	public String setMovieInCache() {

		Movie movie = new Movie(4, "Titanic", "James Cameron");

		Cache cache = cacheManager.getCache("testCache"); // Obtem um cache configurado em EHCACHE.XML
		cache.put(new Element(1L, movie)); // Guarda um elemento no Cache

		return "Elemento " + movie.toString() + " cacheado!";

	}

	@RequestMapping(value = "/getCachedMovie", method = RequestMethod.GET, produces = "application/json")
	public Movie getCachedMovie() {

		Cache cache = cacheManager.getCache("testCache");
		Element e = cache.get(1L); // Obtem elemento do Cache
		Movie cachedMovie = (Movie) e.getObjectValue(); // Obtem o Valor do Elemento cacheado

		return cachedMovie;

	}

	@RequestMapping(value = "/removeAndUpdateCache", method = RequestMethod.GET, produces = "application/json")
	public Movie updateAndRemove() {

		Cache cache = cacheManager.getCache("testCache");
		Element e = cache.removeAndReturnElement(1L); // Obtem e limpa o elemento cacheado

		return (Movie) e.getObjectValue();

	}

	public void lockExample() {

		Cache cache = cacheManager.getCache("testCache");

		// Obter Lock de Thread para uma chave
		cache.acquireReadLockOnKey(1L); // Lock de Leitura
		cache.acquireWriteLockOnKey(1L); // Lock de Escrita

		// Verificar se existe Lock de chave na Thread corrente
		cache.isReadLockedByCurrentThread(1L); // Lock de Leitura
		cache.isWriteLockedByCurrentThread(1L); // Lock de Escrita

		// Liberar Lock da Thread Corrente
		cache.releaseReadLockOnKey(1L); // Lock de Leitura
		cache.releaseWriteLockOnKey(1L); // Lock de Escrita

	}

	public void persistenceExample() {

		Cache cache = cacheManager.getCache("testCache");

		// Os procedimentos abaixo são executados quando uma aplicação é STANDALONE
		// Em aplicações com cache distribuido, o TSA gerencia automaticamente o
		// ciclo de vida do cache

		// Se o cache está configurado como "disk persistent", ao final do
		// processamento do objeto é necessário descartar o cache
		cache.dispose(); // Executa o descarte do cache

		// Executa a limpeza do cache e aloca os dados para o disco
		cache.flush();

	}

	@RequestMapping(value = "/addInCache", method = RequestMethod.POST, produces = "application/json")
	public String addEntriesInCache() {
		
		Cache cache = cacheManager.getCache("testCache");
		
		Stream.iterate(0, n -> n + 1).limit(60).forEach(x -> {
			movieService.save(new Movie(new Integer(x), "Filme " + x, "Diretor " + x));
//			Movie movie = new Movie(new Integer(x), "Filme " + x, "Diretor " + x);
//			cache.put(new Element(new Integer(x), movie));
		});

		int cacheSize = cache.getKeys().size();

		return "O numero de entradas no cache é de " + cacheSize;

	}
	
	@RequestMapping(value="/getFromCache", method = RequestMethod.GET, produces = "application/json")
	public List<Movie> getEntriesFromCache(){
		
		List<Movie> returnList = new ArrayList<Movie>();
		List<Integer> keysToRead = new ArrayList<Integer>();
		
		Stream.iterate(0, n -> n + 1).limit(60).forEach(x ->{
			keysToRead.add(x);
		});
		
		Cache cache = cacheManager.getCache("testCache");
		Map<Object, Element> all = cache.getAll(keysToRead);
		for(Integer key : keysToRead) {
			Element element = all.get(key);
			if(element == null) continue;
			returnList.add((Movie) element.getObjectValue());
		}
		
		return returnList;
		
	}
	
	@RequestMapping(value="/getFromCache/{cod}", method = RequestMethod.GET, produces = "application/json")
	public Movie getFromCacheWithId(@PathVariable("cod") Integer id) {
		
		Cache cache = cacheManager.getCache("testCache");
		Element element = cache.get(id);
		return (Movie) element.getObjectValue();
		
	}

	@RequestMapping(value="/addInCacheInterval", method = RequestMethod.POST, produces = "application/json")
	public List<String> addEntriesInCacheInterval() throws Exception{
		
		List<String> resultList = new ArrayList<String>();
		Cache cache = cacheManager.getCache("testCache");
		
		Stream.iterate(0,  n -> n + 1).limit(3).forEach(x -> {
			Stream.iterate(0, n -> n + 1).limit(10).forEach(y -> {
				int i = (x > 0) ? ((x * 10) + y) : y;
					movieService.save(new Movie(i, "Filme " + i, "Diretor " + i));
			});
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		HashSet<Integer> keysExpected = new HashSet<Integer>();
		Stream.iterate(0, n -> n + 1).limit(30).forEach(x -> {
			keysExpected.add(x);
		});
		
		Map<Object, Element> all = cache.getAll(keysExpected);
		for(Integer key : keysExpected) {
				
			Element element = all.get(key);
			if (element == null)
				continue;
			if(element.isExpired()) {
				resultList.add("Elemento " + key + " expirado.");
			}else {
				resultList.add("Elemento " + key + " ativo e no cache.");
			}
			
		}
		
		return resultList;
		
	}

}
