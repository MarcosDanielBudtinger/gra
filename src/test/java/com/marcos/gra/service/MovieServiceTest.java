package com.marcos.gra.service;

import com.marcos.gra.dto.MovieResponseDTO;
import com.marcos.gra.entity.Movie;
import com.marcos.gra.repository.MovieRepository;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

   @Mock
   MovieRepository movieRepository;
   @Captor
   private ArgumentCaptor<Movie> movieArgumentCaptor;
   @InjectMocks
   private MovieService movieService;

   @Test
   void shouldSaveMovie() {
      Movie movie = mockMovie();

      movieService.save(movie);
      verify(movieRepository).save(movieArgumentCaptor.capture());
      Movie result = movieArgumentCaptor.getValue();

      assertEquals(1999, result.getYear());
      assertEquals("Can't Stop the Music", result.getTitle());
      assertEquals("Associated Film Distribution", result.getStudios());
      assertEquals("Allan Carr", result.getProducers());
      assertTrue(result.isWinner());
   }

   @Test
   void shouldReturnMovieResponseDTO() {
      when(movieRepository.findByWinnerTrue()).thenReturn(mockMovieList());

      Optional<MovieResponseDTO> response = movieService.findMovies();

      assertThat(response).isPresent();
   }

   private Movie mockMovie(){
      return Movie.of(1999, "Can't Stop the Music", "Associated Film Distribution", "Allan Carr", true);
   }

   private List<Movie> mockMovieList() {
      List<Movie> movieList = new ArrayList<>();
      Movie m1 = Movie.of(1984, "Bolero", "Cannon Films", "Bo Derek", true);
      Movie m2 = Movie.of(1990, "Ghosts Can't Do It", "Triumph Releasing", "Bo Derek", true);
      movieList.add(m1);
      movieList.add(m2);
      return movieList;
   }
}
