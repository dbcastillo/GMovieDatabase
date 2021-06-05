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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;



import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public String getJSON(String path) throws  Exception{
        Path paths = Paths.get(path);
        return new String(Files.readAllBytes(paths));
    }

}
