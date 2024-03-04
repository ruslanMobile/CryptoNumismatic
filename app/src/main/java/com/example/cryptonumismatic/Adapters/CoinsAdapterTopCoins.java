package com.example.cryptonumismatic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.cryptonumismatic.R;
import com.example.cryptonumismatic.models.NftModel;
import java.util.List;

public class CoinsAdapterTopCoins extends RecyclerView.Adapter<CoinsAdapterTopCoins.ViewHolder> {
    private List list;
    private Context context;

    public CoinsAdapterTopCoins(List list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CoinsAdapterTopCoins.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinsAdapterTopCoins.ViewHolder holder, int position) {
        NftModel item = (NftModel) list.get(position);
        try {
            holder.textId.setText(item.getSymbol());
            holder.textName.setText(item.getName());
            holder.imageViewAddElement.setVisibility(View.INVISIBLE);
            holder.textPrice.setText(item.getData().getFloor_price());
            holder.imageViewPercent.setVisibility(View.INVISIBLE);
            holder.textPercent.setVisibility(View.INVISIBLE);
        } catch (NullPointerException ex) {
        }
        Glide.with(context)
                .load(item.getThumb())
                .placeholder(R.drawable.ic_icon_bitcoin)
                .into(holder.imageViewLogo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void findCoins(List<NftModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewLogo, imageViewPercent, imageViewAddElement;
        TextView textName, textId, textPrice, textPercent;

        public ViewHolder(@NonNull View view) {
            super(view);
            imageViewLogo = view.findViewById(R.id.imageLogoCoinItem);
            imageViewPercent = view.findViewById(R.id.imagePriceCoinItem);
            textName = view.findViewById(R.id.textNameCoinItem);
            textId = view.findViewById(R.id.textIdCoinItem);
            textPrice = view.findViewById(R.id.textPriceCoinItem);
            textPercent = view.findViewById(R.id.textPercentCoinItem);
            imageViewAddElement = view.findViewById(R.id.imageAddToPortfolio);
        }
    }
}
