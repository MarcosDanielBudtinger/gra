package com.marcos.gra.config;

import com.marcos.gra.GraApplication;
import com.marcos.gra.entity.Movie;
import com.marcos.gra.repository.MovieRepository;
import com.marcos.gra.util.CsvMovie;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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

            File file = new File(resource.getFile().getPath()+"/movielist.csv");

            List<Movie> movieList = new ArrayList<>();

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file.getPath())));
            String linha = null;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if(!dados[0].equals("year")){
                    System.out.println(Arrays.toString(dados));
                    System.out.println("year: " + dados[0]);
                    System.out.println("title: " + dados[1]);
                    System.out.println("studios: " + dados[2]);
                    System.out.println("producers: " + dados[3]);
                    System.out.println("--------------------------");
                    movieList.add(Movie.of(Integer.parseInt(dados[0]), dados[1], dados[2], dados[3], dados.length > 4 ? dados[4] : null));
                }
            }
            reader.close();

            if(!movieList.isEmpty()) {
                movieList.stream().forEach(movie -> repository.save(movie));
            }

//            Reader reader = Files.newBufferedReader(Paths.get(file.getPath()));
//            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
//
//            List<String[]> pessoas = csvReader.readAll();
//            for (String[] pessoa : pessoas)
//                System.out.println(pessoa[0]);

//            Files.lines(Paths.get(file.getPath()))
//                    .skip(1)
//                    .forEach(System.out::println);

//            Files.lines(Paths.get(file.getPath()))
//                    .skip(1)
//                    .map(line -> line.split(";"))
//                    .map(col-> new CsvMovie(Integer.valueOf(col[0]), col[1], col[2], col[3], Objects.))
//                    .forEach(System.out::println);

////
//            Reader reader = Files.newBufferedReader(Paths.get(file.getPath()));
//
//            List<CsvMovie> movies = new CsvToBeanBuilder(new FileReader(file.getPath()))
//                    .withType(CsvMovie.class).build().parse();

//            List<CsvMovie> movies = csvToBean.parse();
//
//            for (CsvMovie movie : movies)
//                System.out.println(movie);
            log.info("Process populate table movies completed...");
        };
    }

}
