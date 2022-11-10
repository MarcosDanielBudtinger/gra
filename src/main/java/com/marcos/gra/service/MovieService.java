package com.marcos.gra.service;

import com.marcos.gra.dto.MovieDTO;
import com.marcos.gra.dto.MovieResponseDTO;
import com.marcos.gra.entity.Movie;
import com.marcos.gra.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Optional<MovieResponseDTO> findMovies() {

        var movieList = movieRepository.findByWinnerTrue();

        var processedProducersName = processProducersName(movieList);

        var map = calculateProducersWinners(processedProducersName);

        var producersName = getProducersNameWithMoreWinners(map);

        var mapMoviesWithMoreWinners = extractMoviesAccordingProducersWithMoreWinners(movieList, producersName);

        var movieDTOList = transformWinnersInMovieDto(mapMoviesWithMoreWinners);

        var average = getAverageInterval(movieDTOList);

        return Optional.of(getMovieResponse(movieDTOList, average));
    }

    private static MovieResponseDTO getMovieResponse(List<MovieDTO> movieDTOList, Double average){
        List<MovieDTO> min = new ArrayList<>();
        List<MovieDTO> max = new ArrayList<>();
        movieDTOList.forEach(movieDTO -> {
            if(movieDTO.getInterval() < average) {
                min.add(movieDTO);
            } else{
                max.add(movieDTO);
            }
        });
        return MovieResponseDTO.of(min, max);
    }

    private static Double getAverageInterval(List<MovieDTO> movieDTOList) {
        return movieDTOList.stream().mapToInt(MovieDTO::getInterval).sum() / (double) movieDTOList.size();
    }

    private static List<MovieDTO> transformWinnersInMovieDto(HashMap<String, List<Integer>> mapMoviesWithMoreWinners) {
        List<MovieDTO> movieDTOList = new ArrayList<>();
        mapMoviesWithMoreWinners.forEach((key, value) -> movieDTOList.add(MovieDTO.of(key, value)));
        return movieDTOList;
    }

    private static HashMap<String, List<Integer>> extractMoviesAccordingProducersWithMoreWinners(List<Movie> movieList, List<String> producersName) {
        var mapWinners = new HashMap<String, List<Integer>>();

        producersName.forEach(producerName -> movieList.forEach(movie -> {
            if(movie.getProducers().contains(producerName)){
                var listYear = mapWinners.get(producerName);
                if (!mapWinners.containsKey(producerName)){
                    List<Integer> list = new ArrayList<>();
                    list.add(movie.getYear());
                    mapWinners.put(producerName, list);
                } else {
                    var year = movie.getYear();
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
        movieList.forEach(movie -> {
            var result = movie.getProducers().split(" and |, |;\\s");
            if(result.length > 0){
                producerWinner.addAll(Arrays.asList(result));
            }
        });
        return producerWinner;
    }
}
