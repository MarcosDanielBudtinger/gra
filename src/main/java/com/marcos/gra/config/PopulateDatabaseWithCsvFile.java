package com.marcos.gra.config;

import com.marcos.gra.entity.Movie;
import com.marcos.gra.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.*;

@Configuration
public class PopulateDatabaseWithCsvFile {

    private static final Logger log = LoggerFactory.getLogger(PopulateDatabaseWithCsvFile.class);

    @Autowired
    @Value("classpath:/csvfile")
    Resource resource;

    @Bean
    CommandLineRunner populateDatabase(MovieRepository repository) {

        return args -> {
            log.info("Starting populate table movies process...");
            try {
                var file = new File(resource.getFile().getPath()+"/movielist.csv");
                List<Movie> movieList = new ArrayList<>();

                var reader = new BufferedReader(new InputStreamReader(new FileInputStream(file.getPath())));
                String row = null;
                while ((row = reader.readLine()) != null) {
                    String[] dados = row.split(";");
                    if(!dados[0].equals("year")){
                        movieList.add(Movie.of(Integer.parseInt(dados[0]), dados[1], dados[2], dados[3], dados.length > 4));
                    }
                }
                reader.close();

                if(!movieList.isEmpty()) {
                    movieList.parallelStream().forEach(repository::save);
                }
                log.info("Process populate table movies completed...");
            }catch (Exception e) {
                log.error("Error trying to read csv file!");
            }
        };
    }

}
