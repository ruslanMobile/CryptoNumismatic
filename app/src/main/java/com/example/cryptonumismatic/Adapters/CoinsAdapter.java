package com.example.cryptonumismatic.Adapters;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.cryptonumismatic.R;
import com.example.cryptonumismatic.models.ModelTopCoin;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoinsAdapter extends RecyclerView.Adapter<CoinsAdapter.ViewHolder> {
    List list;
    Context context;
    public CoinsAdapter(List list,Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CoinsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinsAdapter.ViewHolder holder, int position) {
        ModelTopCoin item = (ModelTopCoin) list.get(position);
        DecimalFormat f = new DecimalFormat("0.000");


        try {
            holder.textId.setText(item.getId());
            holder.textName.setText(item.getName());
            holder.textPercent.setText(f.format(Math.abs(Double.valueOf(item.getModelOneDayChange().getPriceChangePct())*100)) + "%");
            holder.textPrice.setText(f.format(Double.valueOf(item.getPrice())));
            if(Double.valueOf(item.getModelOneDayChange().getPriceChangePct())>0) {
                holder.imageViewPercent.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_plus_percent));
                holder.textPercent.setTextColor(context.getResources().getColor(R.color.percentPlus));
            }else {
                holder.imageViewPercent.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_minus_persent));
                holder.textPercent.setTextColor(context.getResources().getColor(R.color.percentMinus));
            }
        }catch (NullPointerException ex) {}


        Pattern pattern = Pattern.compile(".svg");
        Matcher matcher = pattern.matcher(item.getLogoUrl());
        if(matcher.find()) {
            GlideToVectorYou
                    .init()
                    .with(context)
                    .setPlaceHolder(R.drawable.ic_icon_bitcoin, R.drawable.ic_icon_bitcoin)
                    .load(Uri.parse(item.getLogoUrl()), holder.imageViewLogo);
        }else {
            Glide.with(context)
                    .load(item.getLogoUrl())
                    .placeholder(R.drawable.ic_icon_bitcoin)
                    .into(holder.imageViewLogo);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void findCoins(List<ModelTopCoin> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewLogo,imageViewPercent;
        TextView textName,textId,textPrice,textPercent;
        public ViewHolder(@NonNull View view) {
            super(view);
            imageViewLogo = view.findViewById(R.id.imageLogoCoinItem);
            imageViewPercent = view.findViewById(R.id.imagePriceCoinItem);
            textName = view.findViewById(R.id.textNameCoinItem);
            textId = view.findViewById(R.id.textIdCoinItem);
            textPrice = view.findViewById(R.id.textPriceCoinItem);
            textPercent = view.findViewById(R.id.textPercentCoinItem);
        }
    }
}