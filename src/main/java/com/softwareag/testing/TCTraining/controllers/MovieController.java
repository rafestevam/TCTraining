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
		Element e = cache.removeAndReturnElement(1L); //Obtem e limpa o elemento cacheado

		return (Movie) e.getObjectValue();

	}
	
	public void lockExample() {
		
		Cache cache = cacheManager.getCache("testCache");
		
		//Obter Lock de Thread para uma chave
		cache.acquireReadLockOnKey(1L); //Lock de Leitura
		cache.acquireWriteLockOnKey(1L); //Lock de Escrita
		
		//Verificar se existe Lock de chave na Thread corrente
		cache.isReadLockedByCurrentThread(1L); //Lock de Leitura
		cache.isWriteLockedByCurrentThread(1L); //Lock de Escrita
		
		//Liberar Lock da Thread Corrente
		cache.releaseReadLockOnKey(1L);  //Lock de Leitura
		cache.releaseWriteLockOnKey(1L); //Lock de Escrita
		
	}
	
	public void persistenceExample() {
		
		Cache cache = cacheManager.getCache("testCache");
		
		//Os procedimentos abaixo são executados quando uma aplicação é STANDALONE
		//Em aplicações com cache distribuido, o TSA gerencia automaticamente o
		//ciclo de vida do cache
		
		//Se o cache está configurado como "disk persistent", ao final do
		//processamento do objeto é necessário descartar o cache
		cache.dispose(); //Executa o descarte do cache
		
		//Executa a limpeza do cache e aloca os dados para o disco
		cache.flush();
		
	}

}
