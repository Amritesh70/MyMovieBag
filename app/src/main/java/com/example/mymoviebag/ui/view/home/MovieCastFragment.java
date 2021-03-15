package com.example.mymoviebag.ui.view.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviebag.R;
import com.example.mymoviebag.data.remote.model.Movie;
import com.example.mymoviebag.data.remote.model.MovieCasts;
import com.example.mymoviebag.data.remote.model.MovieReview;
import com.example.mymoviebag.databinding.FragmentMovieCastsBinding;
import com.example.mymoviebag.databinding.FragmentMovieDetailsBinding;
import com.example.mymoviebag.ui.view.home.adapter.MovieCastAdapter;
import com.example.mymoviebag.ui.view.home.adapter.ProductionCompanyAdapter;
import com.example.mymoviebag.ui.viewmodel.HomeViewModel;
import com.example.mymoviebag.util.CommonUtils;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class MovieCastFragment extends DialogFragment {
    private FragmentMovieCastsBinding mBinding;
    private String mMovieName;
    private String mViewType;
    private int mMovieId;
    @Inject
    HomeViewModel mHomeViewModel;

    public MovieCastFragment() {
        //Empty Constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_casts,container,false);

        Bundle bundle = getArguments();
        if(bundle!=null)
            mMovieName = bundle.getString("movieName");
            mMovieId = bundle.getInt("movieId");
            mViewType = bundle.getString("view");

        mHomeViewModel =
                new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        mBinding.heading.setText(mMovieName);
        if(mViewType.equalsIgnoreCase("Casts"))
            getMovieCast(mMovieId);
        else
            getMovieReview(mMovieId);
        listener();
        return mBinding.getRoot();
    }

    private void listener() {
        mBinding.ivBack.setOnClickListener(v -> {
            if(getDialog()!=null)
                getDialog().dismiss();
        });
    }

    private void getMovieCast(Integer id) {
        mHomeViewModel.getMovieCast(id).observe(getViewLifecycleOwner(), this::configureMovieCast);
    }

    private void configureMovieCast(MovieCasts movieCasts) {
        if(movieCasts!=null && movieCasts.getCast()!=null && !movieCasts.getCast().isEmpty()) {
            mBinding.recyclerviewMovieCast.setVisibility(View.VISIBLE);
            mBinding.noDataFound.setVisibility(View.GONE);
            mBinding.recyclerviewMovieCast.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            mBinding.recyclerviewMovieCast.setHasFixedSize(true);
            MovieCastAdapter mProductionCompanyAdapter = new MovieCastAdapter(getActivity(), movieCasts.getCast(),mViewType);
            mBinding.recyclerviewMovieCast.setAdapter(mProductionCompanyAdapter);
        } else {
            mBinding.noDataFound.setVisibility(View.VISIBLE);
            mBinding.recyclerviewMovieCast.setVisibility(View.GONE);
        }
    }

    private void getMovieReview(int mMovieId) {
        mHomeViewModel.getMovieReview(mMovieId).observe(getViewLifecycleOwner(), this::configureMovieReview);
    }

    private void configureMovieReview(MovieReview movieReview) {
        if(movieReview!=null && movieReview.getResults()!=null && !movieReview.getResults().isEmpty()) {
            mBinding.recyclerviewMovieCast.setVisibility(View.VISIBLE);
            mBinding.noDataFound.setVisibility(View.GONE);
            mBinding.recyclerviewMovieCast.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            mBinding.recyclerviewMovieCast.setHasFixedSize(true);
            MovieCastAdapter mProductionCompanyAdapter = new MovieCastAdapter(getActivity(), movieReview.getResults(),mViewType,0);
            mBinding.recyclerviewMovieCast.setAdapter(mProductionCompanyAdapter);
        } else {
            mBinding.noDataFound.setVisibility(View.VISIBLE);
            mBinding.recyclerviewMovieCast.setVisibility(View.GONE);
        }
    }
}