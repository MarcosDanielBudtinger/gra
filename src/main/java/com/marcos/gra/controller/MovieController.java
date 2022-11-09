package com.marcos.gra.controller;

import com.marcos.gra.entity.Movie;
import com.marcos.gra.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movieList = movieService.findMovies();
        return movieList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(movieList);
    }
}
