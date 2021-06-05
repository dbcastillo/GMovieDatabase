package com.example.gmoviedatabase.controller;

import com.example.gmoviedatabase.model.Movie;
import com.example.gmoviedatabase.repository.MovieRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    private final MovieRepository repository;

    public MovieController(MovieRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/movies")
    public Iterable<Movie> list() {
        return this.repository.findAll();
    }

    @PostMapping("/movies")
    public Movie create(@RequestBody Movie movie) {
        return this.repository.save(movie);
    }
}
