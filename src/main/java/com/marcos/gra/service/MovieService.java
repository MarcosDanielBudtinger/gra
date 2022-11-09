package com.marcos.gra.service;

import com.marcos.gra.entity.Movie;
import com.marcos.gra.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> findMovies() {

        var movieList = movieRepository.findByWinnerTrue();

        var processedProducersName = processProducersName(movieList);

        var map = calculateProducersWinners(processedProducersName);

        var producersName = getProducersNameWithMoreWinners(map);

        var mapMoviesWithMoreWinners = extractMoviesAccordingProducersWithMoreWinners(movieList, producersName);

        System.out.println(mapMoviesWithMoreWinners);

        return movieList;
    }

    private static HashMap<String, List<Integer>> extractMoviesAccordingProducersWithMoreWinners(List<Movie> movieList, List<String> producersName) {
        var mapWinners = new HashMap<String, List<Integer>>();

        producersName.forEach(producerName -> movieList.forEach(movie -> {
            if(movie.getProducers().contains(producerName)){
                var listYear = mapWinners.get(producerName);
                if (!mapWinners.containsKey(producerName)){
                    var list = new ArrayList(Arrays.asList((movie.getSyear())));
                    mapWinners.put(producerName, list);
                } else {
                    Integer year = movie.getSyear();
                    listYear.add(year);
                    mapWinners.put(producerName, listYear);
                }
            }
        }));

        return mapWinners;
    }

    private static List<String> getProducersNameWithMoreWinners(HashMap<String, Integer> map) {
        return map.entrySet()
                .stream()
                .filter(m -> m.getValue() > 1)
                .map(Map.Entry::getKey).toList();
    }

    private static HashMap<String, Integer> calculateProducersWinners(List<String> processedProducersName) {
        var map = new HashMap<String, Integer>();
        processedProducersName.forEach(producer -> map.merge(producer, 1, Integer::sum));
        return map;
    }

    private static List<String> processProducersName(List<Movie> movieList) {
        List<String> producerWinner = new ArrayList<>();
        movieList.forEach(s -> {
            var result = s.getProducers().split(" and |, |;\\s");
            if(result.length > 0){
                producerWinner.addAll(Arrays.asList(result));
            }
        });
        return producerWinner;
    }
}
