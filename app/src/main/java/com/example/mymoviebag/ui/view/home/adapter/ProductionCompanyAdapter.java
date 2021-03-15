package com.example.mymoviebag.ui.view.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviebag.R;
import com.example.mymoviebag.data.remote.model.MovieDetails;
import com.example.mymoviebag.databinding.ProdCompCardItemBinding;
import com.example.mymoviebag.util.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductionCompanyAdapter extends RecyclerView.Adapter<ProductionCompanyAdapter.ProductionCompanyViewHolder> {
    private ProdCompCardItemBinding mProdCompCardItemBinding;
    private Context mContext;
    private List<MovieDetails.ProductionCompany> mProductionCompanyList;

    public ProductionCompanyAdapter(Context context, List<MovieDetails.ProductionCompany> productionCompanyList) {
        this.mProductionCompanyList = productionCompanyList;
        this.mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ProductionCompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prod_comp_card_item, parent, false);
        return new ProductionCompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionCompanyViewHolder holder, int position) {
        mProdCompCardItemBinding = DataBindingUtil.bind(holder.itemView);

        assert mProdCompCardItemBinding != null;
        mProdCompCardItemBinding.tvCompanyName.setText(mProductionCompanyList.get(position).getName());
        setPoster(CommonUtils.MOVIE_BASE_URL + mProductionCompanyList.get(position).getLogoPath());
    }

    public void setPoster(String url) {
        Picasso.get()
                .load(url)
                .into(mProdCompCardItemBinding.ivCompLogo);
    }

    @Override
    public int getItemCount() {
        return mProductionCompanyList.size();
    }

    public static class ProductionCompanyViewHolder extends RecyclerView.ViewHolder {
        public ProductionCompanyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
