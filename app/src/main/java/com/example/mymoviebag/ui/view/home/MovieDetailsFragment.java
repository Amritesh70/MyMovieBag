package com.example.mymoviebag.ui.view.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviebag.R;
import com.example.mymoviebag.data.remote.model.Movie;
import com.example.mymoviebag.data.remote.model.MovieDetails;
import com.example.mymoviebag.databinding.FragmentMovieDetailsBinding;
import com.example.mymoviebag.ui.view.home.adapter.MovieListAdapter;
import com.example.mymoviebag.ui.view.home.adapter.ProductionCompanyAdapter;
import com.example.mymoviebag.ui.viewmodel.HomeViewModel;
import com.example.mymoviebag.util.CommonUtils;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class MovieDetailsFragment extends DialogFragment {
    private FragmentMovieDetailsBinding mBinding;
    private Movie.Result mResult;
    @Inject
    HomeViewModel mHomeViewModel;

    public MovieDetailsFragment() {
        //Empty Constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details,container,false);

        Bundle bundle = getArguments();
        if(bundle!=null)
            mResult = (Movie.Result)bundle.getSerializable("modelData");

        //initViews();
        mHomeViewModel =
                new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        getMovieDetails(mResult.getId());
        listener();
        return mBinding.getRoot();
    }

    private void listener() {
        mBinding.ivBack.setOnClickListener(v -> {
            if(getDialog()!=null)
                getDialog().dismiss();
        });

        mBinding.buttonCasts.setOnClickListener(v -> {
            DialogFragment detailsFragment = new MovieCastFragment();
            Bundle arg = new Bundle();
            arg.putString("movieName",mResult.getOriginalTitle());
            arg.putInt("movieId",mResult.getId());
            arg.putString("view","Casts");
            detailsFragment.setArguments(arg);
            detailsFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.FilterDialog);
            detailsFragment.show(getChildFragmentManager(),"");
        });

        mBinding.buttonReviews.setOnClickListener(v -> {
            DialogFragment detailsFragment = new MovieCastFragment();
            Bundle arg = new Bundle();
            arg.putString("movieName",mResult.getOriginalTitle());
            arg.putInt("movieId",mResult.getId());
            arg.putString("view","Review");
            detailsFragment.setArguments(arg);
            detailsFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.FilterDialog);
            detailsFragment.show(getChildFragmentManager(),"");
        });

    }

    private void getMovieDetails(Integer movieId) {
        mHomeViewModel.getMovieDetails(movieId).observe(getViewLifecycleOwner(), this::configureMovieDetails);
    }

    private void configureMovieDetails(MovieDetails movieDetails) {
        Picasso.get()
                .load(CommonUtils.MOVIE_BASE_URL+mResult.getPosterPath())
                .into(mBinding.ivMovieposter);
        mBinding.tvOverView.setText(mResult.getOverview());
        mBinding.tvMovieTitle.setText(mResult.getTitle());
        mBinding.tvAvgVote.setText(mResult.getVoteAverage().toString());
        mBinding.tvLanguage.setText(mResult.getOriginalLanguage());
        mBinding.tvPopularity.setText(mResult.getPopularity().toString());
        mBinding.tvReleaseStatus.setText(movieDetails.getStatus());
        if(movieDetails.getProductionCompanies()!=null && !movieDetails.getProductionCompanies().isEmpty()) {
            mBinding.recyclerviewProductionComp.setVisibility(View.VISIBLE);
            mBinding.tvTitleProductionComp.setVisibility(View.VISIBLE);

            mBinding.recyclerviewProductionComp.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
            mBinding.recyclerviewProductionComp.setHasFixedSize(true);
            ProductionCompanyAdapter mProductionCompanyAdapter = new ProductionCompanyAdapter(getActivity(), movieDetails.getProductionCompanies());
            mBinding.recyclerviewProductionComp.setAdapter(mProductionCompanyAdapter);
        }
    }
}
