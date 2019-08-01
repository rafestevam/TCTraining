package com.softwareag.testing.TCTraining;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.softwareag.testing.TCTraining.components.InitializerComp;
import com.softwareag.testing.TCTraining.configurations.CacheConfig;
import com.softwareag.testing.TCTraining.controllers.MovieController;
import com.softwareag.testing.TCTraining.models.Movie;
import com.softwareag.testing.TCTraining.repositories.MovieRepository;
import com.softwareag.testing.TCTraining.services.MovieServiceImpl;

@SpringBootApplication(scanBasePackageClasses= {
	//Components
	InitializerComp.class,
	//Configurations
	CacheConfig.class,
	//Controllers
	MovieController.class,
	//Models
	Movie.class,
	//Repositories
	MovieRepository.class,
	//Services
	MovieServiceImpl.class
})
//@EnableCaching
public class App 
{
    public static void main( String[] args ) throws Exception {
    	SpringApplication.run(App.class, args);
    }
}
