package com.example.mymoviebag.ui.view.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.mymoviebag.R;
import com.example.mymoviebag.data.remote.model.Movie;
import com.example.mymoviebag.databinding.FragmentHomeBinding;
import com.example.mymoviebag.ui.view.home.adapter.MovieListAdapter;
import com.example.mymoviebag.ui.view.home.adapter.MovieListViewPagerAdapter;
import com.example.mymoviebag.ui.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    private FragmentHomeBinding mFrgmentHomeBinding;
    @Inject
    HomeViewModel mHomeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mFrgmentHomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        initViews();
        return mFrgmentHomeBinding.getRoot();
    }

    private void initViews() {
        mHomeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        mHomeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mFrgmentHomeBinding.textHome.setText(s);
            }
        });

        getMovies(mCurrentPage);

    }

    private void getMovies(int currentPage) {
        mHomeViewModel.getPopularMovieList(currentPage).observe(getViewLifecycleOwner(), movies -> configurePopularMovies(movies));
    }

    private MovieListAdapter mMovieListAdapter;
    private void configurePopularMovies(Movie movies) {
        if(mCurrentPage==1) {
            configureHorizontalView(movies);
        }
        configureVirticalView(movies);
    }

    private List<Movie.Result> mMovieResultList = new ArrayList<>();
    private int mTotalPageCount = 0;
    private int mCurrentPage = 1;
    private void configureVirticalView(Movie movies) {
        mMovieResultList.addAll(movies.getResults());
        if(mCurrentPage==1) {
            mTotalPageCount = movies.getTotalPages();
            mFrgmentHomeBinding.recyclerviewMovielist.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            mFrgmentHomeBinding.recyclerviewMovielist.setHasFixedSize(true);
            mMovieListAdapter = new MovieListAdapter(getActivity(), mFrgmentHomeBinding.recyclerviewMovielist, mMovieResultList);
            mFrgmentHomeBinding.recyclerviewMovielist.setAdapter(mMovieListAdapter);
        }
        mMovieListAdapter.notifyDataSetChanged();
        mMovieListAdapter.setLoaded();
        mMovieListAdapter.setOnLoadMoreListener(()-> {
            if(mTotalPageCount>mCurrentPage){
                ++mCurrentPage;
                getMovies(mCurrentPage);
            }
        });
    }

    private void configureHorizontalView(Movie movies) {
        PagerAdapter pagerAdapter = new MovieListViewPagerAdapter(getContext(),movies);
        mFrgmentHomeBinding.viewPagerBanner.setAdapter(pagerAdapter);
        timerForViewPager(mFrgmentHomeBinding.viewPagerBanner, movies.getResults().size(), 5000, 5000);
        mFrgmentHomeBinding.tabLayout.setupWithViewPager(mFrgmentHomeBinding.viewPagerBanner,true);
    }

    private void timerForViewPager(ViewPager viewPagerBanner, int size, int delay, int period) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                viewPagerBanner.post(()->{
                    viewPagerBanner.setCurrentItem(viewPagerBanner.getCurrentItem()+1 % size);
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,delay,period);
    }
}