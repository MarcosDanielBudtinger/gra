package com.marcos.gra.resource;

import com.marcos.gra.dto.MovieResponseDTO;
import com.marcos.gra.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("v1/movies")
public class MovieResource {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<MovieResponseDTO> getAllMovies() {
        Optional<MovieResponseDTO> movieList = movieService.findMovies();
        return movieList.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
