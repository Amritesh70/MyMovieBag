package com.example.mymoviebag.data.remote.api;

import com.example.mymoviebag.data.remote.model.Movie;
import com.example.mymoviebag.data.remote.model.MovieCasts;
import com.example.mymoviebag.data.remote.model.MovieDetails;
import com.example.mymoviebag.data.remote.model.MovieReview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("/3/discover/movie/")
    Call<Movie> getPopularMovieList(@Query("sort_by") String sortBy, @Query("api_key") String apiKey, @Query("page") int pageIndex);

    @GET("/3/movie/{movie_id}")
    Call<MovieDetails> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String apiKey, @Query("language") String lang);

    @GET("/3/movie/{id}/casts")
    Call<MovieCasts> getCastDetails(@Path("id") int movieId, @Query("api_key") String apiKey, @Query("language") String lang);

    @GET("/3/movie/{movie_id}/reviews")
    Call<MovieReview> getMovieReview(@Path("movie_id") int movieId, @Query("api_key") String apiKey, @Query("language") String lang);
}
