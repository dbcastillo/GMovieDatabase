package com.example.gmoviedatabase.controller;

import com.example.gmoviedatabase.model.Movie;
import com.example.gmoviedatabase.repository.MovieRepository;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/movies/{id}")
    public Movie getMovie(@PathVariable Long id) {
        return this.repository.findById(id).orElse(new Movie());
    }
}
