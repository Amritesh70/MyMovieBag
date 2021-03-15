package com.example.mymoviebag.ui.view.home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviebag.R;
import com.example.mymoviebag.data.remote.model.Movie;
import com.example.mymoviebag.databinding.MovieCardItemBinding;
import com.example.mymoviebag.ui.view.home.MovieDetailsFragment;
import com.example.mymoviebag.util.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    private MovieCardItemBinding mMovieCardItemBinding;
    private List<Movie.Result> mMovieList;
    private Context mContext;
    private int mTotalItemCount;
    private int mLastVisibleItem;
    private int mVisibleThreshold=5;

    public MovieListAdapter(Context context, RecyclerView recyclerView, List<Movie.Result> movie) {
        this.mMovieList = movie;
        this.mContext = context;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mTotalItemCount = linearLayoutManager.getItemCount();
                mLastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if(!loading && mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold)) {
                    if(mOnLoadMoreListener!=null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    loading = true;
                }
            }
        });
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        mMovieCardItemBinding = DataBindingUtil.bind(holder.itemView);

        assert mMovieCardItemBinding!=null;
        setPoster(CommonUtils.MOVIE_BASE_URL+mMovieList.get(position).getPosterPath());
        mMovieCardItemBinding.tvMovieTitle.setText(mMovieList.get(position).getTitle());
        mMovieCardItemBinding.tvAvgVote.setText("Avg Vote : "+mMovieList.get(position).getVoteAverage());
        mMovieCardItemBinding.tvLanguage.setText("Language : "+mMovieList.get(position).getOriginalLanguage());
        mMovieCardItemBinding.tvReleaseDate.setText("Release Date : "+mMovieList.get(position).getReleaseDate());

        mMovieCardItemBinding.cardviewMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity fragmentActivity = (FragmentActivity)mContext;
                FragmentManager fm = fragmentActivity.getSupportFragmentManager();
                DialogFragment detailsFragment = new MovieDetailsFragment();
                Bundle arg = new Bundle();
                arg.putSerializable("modelData",mMovieList.get(position));
                detailsFragment.setArguments(arg);
                detailsFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.FilterDialog);
                detailsFragment.show(fm,"");
            }
        });
    }

    public void setPoster(String url) {
        Picasso.get()
                .load(url)
                .into(mMovieCardItemBinding.ivMovieposter);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public MovieViewHolder(View itemView) {
            super(itemView);
        }
    }

    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean loading;
    public void setLoaded(){
        loading = false;
    }
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
