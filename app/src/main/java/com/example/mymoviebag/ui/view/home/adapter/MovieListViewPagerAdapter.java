package com.example.mymoviebag.ui.view.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mymoviebag.R;
import com.example.mymoviebag.data.remote.model.Movie;
import com.example.mymoviebag.util.CommonUtils;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MovieListViewPagerAdapter extends PagerAdapter {
    private List<Movie.Result> mMovieList;
    private Context context;

    public MovieListViewPagerAdapter(Context mContext, Movie movie) {
        this.mMovieList = movie.getResults();
        this.context = mContext;
    }

    @Override
    public int getCount() {
        return mMovieList.size();
    }

    @Override
    public @NotNull Object instantiateItem(@NotNull ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup)inflater.inflate(R.layout.poster_card_item, collection, false);
        AppCompatImageView appCompatImageView = layout.findViewById(R.id.iv_movieposter);
        setPoster(CommonUtils.MOVIE_BASE_URL+mMovieList.get(position).getBackdropPath(),appCompatImageView);

        AppCompatTextView mMovieTitle = layout.findViewById(R.id.tv_movieTitle);
        mMovieTitle.setText(mMovieList.get(position).getTitle());

        AppCompatTextView tvReleaseDate = layout.findViewById(R.id.tv_releaseDate);
        tvReleaseDate.setText("Release Date : "+mMovieList.get(position).getReleaseDate());

        collection.addView(layout);
        return layout;
    }

    public void setPoster(String url,AppCompatImageView appCompatImageView) {
        Picasso.get()
                .load(url)
                .into(appCompatImageView);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NotNull Object object) {
        container.removeView((View) object);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public MovieViewHolder(View itemView) {
            super(itemView);
        }
    }
}
