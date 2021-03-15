package com.example.mymoviebag.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mymoviebag.data.remote.api.MovieService;
import com.example.mymoviebag.data.remote.api.ServiceGenerator;
import com.example.mymoviebag.data.remote.model.Movie;
import com.example.mymoviebag.data.remote.model.MovieCasts;
import com.example.mymoviebag.data.remote.model.MovieDetails;
import com.example.mymoviebag.data.remote.model.MovieReview;
import com.example.mymoviebag.util.CommonUtils;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMovieRepository {
    private ServiceGenerator mServiceGenerator;
    private MutableLiveData<Movie> mMovieList = new MutableLiveData<>();
    private MutableLiveData<MovieDetails> mMovieDetails = new MutableLiveData<>();
    private MutableLiveData<MovieCasts> mMovieCast = new MutableLiveData<>();
    private MutableLiveData<MovieReview> mMovieReview = new MutableLiveData<>();
    private MutableLiveData<String> mText;

    @Inject
    public PopularMovieRepository(ServiceGenerator mServiceGenerator) {
        this.mServiceGenerator = mServiceGenerator;
        mText = new MutableLiveData<>();
        mText.setValue("Popular Movies");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Movie> getPopularMovieList(int pageIndex){
        mMovieList = new MutableLiveData<>();

        mServiceGenerator.createService(MovieService.class).getPopularMovieList("popularity.desc", CommonUtils.API_KEY,pageIndex).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                int statusCode = response.code();
                if(statusCode==200){
                    mMovieList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });

        return mMovieList;
    }

    public LiveData<MovieDetails> getMovieDetails(int movieId){
        mMovieDetails = new MutableLiveData<>();

        mServiceGenerator.createService(MovieService.class).getMovieDetails(movieId, CommonUtils.API_KEY,"en-US").enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                int statusCode = response.code();
                if(statusCode==200){
                    mMovieDetails.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

            }
        });

        return mMovieDetails;
    }

    public LiveData<MovieCasts> getMovieCast(int movieId){
        mMovieCast = new MutableLiveData<>();

        mServiceGenerator.createService(MovieService.class).getCastDetails(movieId, CommonUtils.API_KEY,"en-US").enqueue(new Callback<MovieCasts>() {
            @Override
            public void onResponse(Call<MovieCasts> call, Response<MovieCasts> response) {
                int statusCode = response.code();
                if(statusCode==200){
                    mMovieCast.setValue(response.body());
                } else {
                    mMovieCast.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<MovieCasts> call, Throwable t) {
                mMovieCast.setValue(null);
            }
        });

        return mMovieCast;
    }

    public LiveData<MovieReview> getMovieReview(int movieId){
        mMovieReview = new MutableLiveData<>();

        mServiceGenerator.createService(MovieService.class).getMovieReview(movieId, CommonUtils.API_KEY,"en-US").enqueue(new Callback<MovieReview>() {
            @Override
            public void onResponse(Call<MovieReview> call, Response<MovieReview> response) {
                int statusCode = response.code();
                if(statusCode==200){
                    mMovieReview.setValue(response.body());
                } else {
                    mMovieReview.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<MovieReview> call, Throwable t) {
                mMovieReview.setValue(null);
            }
        });

        return mMovieReview;
    }

}
