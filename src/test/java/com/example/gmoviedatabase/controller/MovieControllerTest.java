package com.example.gmoviedatabase.controller;

import com.example.gmoviedatabase.model.Movie;
import com.example.gmoviedatabase.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    MovieRepository repository;

    @Test
    @Transactional
    @Rollback
    public void testEmptyDatabase() throws Exception {
        MockHttpServletRequestBuilder request=get("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").doesNotExist());

    }

    @Test
    @Transactional
    @Rollback
    public void testCreateMovie() throws Exception {
        String json=getJSON("src/test/resources/postMovie.json");
        MockHttpServletRequestBuilder request=post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("The Avengers")));
    }
    @Test
    @Transactional
    @Rollback
    public void testGetMovies() throws Exception {
      Movie movie = new Movie();
      movie.setTitle("The Avengers");
      this.repository.save(movie);

      Movie movie1 = new Movie();
      movie1.setTitle("Superman Returns");
      this.repository.save(movie1);

        MockHttpServletRequestBuilder request=get("/movies")
                .contentType(MediaType.APPLICATION_JSON);
                //.content(json);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("The Avengers")))
                .andExpect(jsonPath("$[1].title", is("Superman Returns")));

    }

    @Test
    @Transactional
    @Rollback
    public void testGetMovieByTitle() throws Exception {
        testCreateMovie();
        List<Movie> movieList = (List<Movie>) this.repository.findAll();
        Movie movie = movieList.get(0);
        RequestBuilder request = get("/movies/title")
                .param("title",movie.getTitle());

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movie.title", is(movie.getTitle())));
    }
    @Test
    @Transactional
    @Rollback
    public void testGetMovieByTitleNotExist() throws Exception {
        testCreateMovie();
        List<Movie> movieList = (List<Movie>) this.repository.findAll();
        Movie movie = movieList.get(0);
        RequestBuilder request = get("/movies/title")
                .param("title","Nomovie");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("This movie Nomovie does not exist")));
    }


    @Test
    @Transactional
    @Rollback
    public void testMovieRating() throws Exception {
        String json=getJSON("src/test/resources/movieRating.json");
        MockHttpServletRequestBuilder request=patch("/movies/title")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .param("title","The Avengers");

        this.mvc.perform(request)
                .andExpect(status().isOk());
               // .andExpect(jsonPath("$.[0].rating", is("5")));
    }


    public String getJSON(String path) throws  Exception{
        Path paths = Paths.get(path);
        return new String(Files.readAllBytes(paths));
    }

}
