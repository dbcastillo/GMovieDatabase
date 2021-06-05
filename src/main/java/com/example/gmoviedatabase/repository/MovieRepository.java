package com.example.gmoviedatabase.repository;

import com.example.gmoviedatabase.model.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    List<Movie> findByTitle(String title);
}
