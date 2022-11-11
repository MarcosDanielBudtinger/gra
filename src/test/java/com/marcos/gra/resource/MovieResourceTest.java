package com.marcos.gra.resource;

import com.marcos.gra.dto.MovieDTO;
import com.marcos.gra.dto.MovieResponseDTO;
import com.marcos.gra.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MovieResourceTest {

   private MockMvc mockMvc;

   @InjectMocks
   private MovieResource movieResource;

   private static final String CONTEXT_ROOT = "/v1/movies";

   @Mock
   private MovieService movieService;

   @BeforeEach
   void setup() {
      mockMvc = MockMvcBuilders.standaloneSetup(movieResource).build();
   }

   @Test
   void shouldReturnOkStatus() throws Exception {
      given(movieService.findMovies()).willReturn(Optional.of(mockMovieResponseDTO()));

      mockMvc
         .perform(get(CONTEXT_ROOT ))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
         .andReturn();
   }

   @Test
   void shouldReturnNoContentStatus() throws Exception {
      given(movieService.findMovies()).willReturn(Optional.empty());

      mockMvc
         .perform(get(CONTEXT_ROOT ))
         .andExpect(MockMvcResultMatchers.status().isNoContent())
         .andReturn();
   }

   private MovieResponseDTO  mockMovieResponseDTO() {
      return MovieResponseDTO.of(mockMovieDTOList(), mockMovieDTOList());
   }

   private List<MovieDTO> mockMovieDTOList(){
      List<MovieDTO> movieDTOList = new ArrayList<>();
      List<Integer> years = new ArrayList<>();
      years.add(1999);
      years.add(1998);
      MovieDTO movieDTO = MovieDTO.of("Producer 1", years);
      movieDTOList.add(movieDTO);
      return movieDTOList;
   }
}
