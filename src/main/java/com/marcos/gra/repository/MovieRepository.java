package com.marcos.gra.repository;

import com.marcos.gra.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByWinnerTrue();
}
