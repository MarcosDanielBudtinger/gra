package com.marcos.gra.config;

import com.marcos.gra.entity.Movie;
import com.marcos.gra.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.*;

@Configuration
public class PopulateDatabaseWithCsvFile {

    private static final Logger log = LoggerFactory.getLogger(PopulateDatabaseWithCsvFile.class);

    @Bean
    CommandLineRunner populateDatabase(MovieService service) {

        return args -> {
            log.info("Starting populate table movies process...");
            try {
                InputStream imp = new ClassPathResource("/csvfile/movielist.csv").getInputStream();

                List<Movie> movieList = new ArrayList<>();

                var reader = new BufferedReader(new InputStreamReader(imp));
                String row = null;
                while ((row = reader.readLine()) != null) {
                    String[] dados = row.split(";");
                    if(!dados[0].equals("year")){
                        movieList.add(Movie.of(Integer.parseInt(dados[0]), dados[1], dados[2], dados[3], dados.length > 4));
                    }
                }
                reader.close();

                if(!movieList.isEmpty()) {
                    movieList.parallelStream().forEach(service::save);
                }
                log.info("Process populate table movies completed...");
            }catch (Exception e) {
                String exception = e.getMessage();
                log.error(String.format("Error trying to read csv file! " +
                   "Please verify that the file exists and is in the correct location. ERROR: %s", exception));
            }
        };
    }

}
