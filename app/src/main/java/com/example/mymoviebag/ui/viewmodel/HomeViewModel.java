package com.example.mymoviebag.ui.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviebag.data.remote.model.Movie;
import com.example.mymoviebag.data.remote.model.MovieCasts;
import com.example.mymoviebag.data.remote.model.MovieDetails;
import com.example.mymoviebag.data.remote.model.MovieReview;
import com.example.mymoviebag.repository.PopularMovieRepository;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {
    PopularMovieRepository mPopularMovieRepository;

    @ViewModelInject
    @Inject
    public HomeViewModel(PopularMovieRepository popularMovieRepository) {
        this.mPopularMovieRepository = popularMovieRepository;
    }

    public LiveData<Movie> getPopularMovieList(int pageIndex) {
        return mPopularMovieRepository.getPopularMovieList(pageIndex);
    }

    public LiveData<MovieDetails> getMovieDetails(int movieId) {
        return mPopularMovieRepository.getMovieDetails(movieId);
    }

    public LiveData<MovieCasts> getMovieCast(int movieId) {
        return mPopularMovieRepository.getMovieCast(movieId);
    }
    public LiveData<MovieReview> getMovieReview(int movieId) {
        return mPopularMovieRepository.getMovieReview(movieId);
    }

    public LiveData<String> getText() {
        return mPopularMovieRepository.getText();
    }
}