package com.example.gmoviedatabase.controller;

import com.example.gmoviedatabase.model.Movie;
import com.example.gmoviedatabase.model.Result;
import com.example.gmoviedatabase.repository.MovieRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @GetMapping("/movies/title")
    public Result getMovieByTitle(@RequestParam String title) {
        Movie movie = this.repository.findByTitle(title);
        Result result = new Result();

        if(movie != null){
           result.setMovie(movie);

        }else{
            result.setMessage("This movie "+ title+ " does not exist");
        }
        return result;
    }

    @PatchMapping("/movies/title")
    public Movie getMovieRating(@RequestBody Movie movieInput,@RequestParam String title){

        Movie movie = this.repository.findByTitle(title);
        if(movie != null){
            movie.setRating(movieInput.getRating());
            this.repository.save(movie);
        }
        else {
            new Movie();
        }
        return movie;
    }


//    @GetMapping("/movie/title")
//    public Movie getMovieAvgRating(@RequestParam String title) {
//
//        Movie movieRating = new Movie();
//        List<Integer> list = new ArrayList<>();
//        List<Movie> movieList = this.repository.findByMovieList(title);
//        int result =0;
//        int avgResult =0;
//        if (movieList.size() >0) {
//            for (int i = 0; i < movieList.size(); i++) {
//                Movie movie = movieList.get(i);
//                int iRating = Integer.parseInt(movie.getRating());
//                list.add(iRating);
//            }
//
//            for (int i = 0; i < list.size(); i++) {
//            result += list.get(i);
//            }
//            avgResult = result/ list.size();
//            movieRating = movieList.get(0);
//            movieRating.setRating(String.valueOf(avgResult));
//
//        }
//        return movieRating;
//    }
}
