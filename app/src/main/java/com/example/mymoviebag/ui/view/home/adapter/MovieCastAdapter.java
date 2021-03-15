package com.example.mymoviebag.ui.view.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviebag.R;
import com.example.mymoviebag.data.remote.model.MovieCasts;
import com.example.mymoviebag.data.remote.model.MovieDetails;
import com.example.mymoviebag.data.remote.model.MovieReview;
import com.example.mymoviebag.databinding.MovieCastCardItemBinding;
import com.example.mymoviebag.databinding.ProdCompCardItemBinding;
import com.example.mymoviebag.util.CommonUtils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class MovieCastAdapter extends RecyclerView.Adapter<MovieCastAdapter.ProductionCompanyViewHolder> {
    private MovieCastCardItemBinding mBinding;
    private Context mContext;
    private List<MovieCasts.Cast> mMovieCast;
    private List<MovieReview.Result> mMovieReview;
    private String mViewType;

    public MovieCastAdapter(Context context, List<MovieCasts.Cast> movieCast, String viewType) {
        this.mContext = context;
        this.mMovieCast = movieCast;
        this.mViewType = viewType;
    }

    public MovieCastAdapter(Context context, List<MovieReview.Result> movieReview,String viewType,int val) {
        this.mContext = context;
        this.mMovieReview = movieReview;
        this.mViewType = viewType;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ProductionCompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_cast_card_item, parent, false);
        return new ProductionCompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionCompanyViewHolder holder, int position) {
        mBinding = DataBindingUtil.bind(holder.itemView);

        assert mBinding != null;
        if(mViewType.equalsIgnoreCase("Casts")) {
            setPoster(CommonUtils.MOVIE_BASE_URL + mMovieCast.get(position).getProfilePath());
            mBinding.tvUserName.setText("Name : " + mMovieCast.get(position).getName());
            mBinding.tvDepartment.setText("Department : " + mMovieCast.get(position).getKnownForDepartment());
            mBinding.tvCharacter.setText("Character : " + mMovieCast.get(position).getCharacter());
        } else {
            setPoster(CommonUtils.MOVIE_BASE_URL + mMovieReview.get(position).getAuthorDetails().getAvatarPath());
            mBinding.tvUserName.setText("Name : " + mMovieReview.get(position).getAuthorDetails().getUsername());
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = null;
            try {
                date = inputFormat.parse(mMovieReview.get(position).getCreatedAt());
                String formattedDate = outputFormat.format(date);
                mBinding.tvDepartment.setText("Created Date : " + formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mBinding.view.setVisibility(View.VISIBLE);
            mBinding.tvContent.setText("Content : " + mMovieReview.get(position).getContent());
        }
    }

    public void setPoster(String url) {
        Picasso.get()
                .load(url)
                .into(mBinding.ivUserPic);
    }

    @Override
    public int getItemCount() {
        return mViewType.equalsIgnoreCase("Casts")?mMovieCast.size():mMovieReview.size();
    }

    public static class ProductionCompanyViewHolder extends RecyclerView.ViewHolder {
        public ProductionCompanyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
