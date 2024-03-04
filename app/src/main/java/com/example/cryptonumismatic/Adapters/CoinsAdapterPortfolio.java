package com.example.cryptonumismatic.Adapters;

import android.content.Context;
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
import com.example.cryptonumismatic.models.ModelCoinPortfolio;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoinsAdapterPortfolio extends RecyclerView.Adapter<CoinsAdapterPortfolio.ViewHolder> {
    private List list;
    private Context context;
    private ClickMoreInfoListener clickMoreInfoListener;

    public CoinsAdapterPortfolio(List list, Context context,ClickMoreInfoListener clickMoreInfoListener) {
        this.list = list;
        this.context = context;
        this.clickMoreInfoListener = clickMoreInfoListener;
    }

    public void updateList(List list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CoinsAdapterPortfolio.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_portfolio,parent,false);
        return new ViewHolder(view,clickMoreInfoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinsAdapterPortfolio.ViewHolder holder, int position) {
        ModelCoinPortfolio modelCoinPortfolio = (ModelCoinPortfolio) list.get(position);
        //Заокруглення до трьох нулів
        DecimalFormat f = new DecimalFormat("0.000");

        try {
            holder.textId.setText(modelCoinPortfolio.getModelCoinFirebase().getIdName());
            holder.textCount.setText(modelCoinPortfolio.getModelCoinFirebase().getCount());
            holder.textName.setText(modelCoinPortfolio.getModelCoin().getName());
            holder.textPercent.setText(f.format(Math.abs(Double.valueOf(modelCoinPortfolio.getModelCoin().getPrice_change_percentage_24h()))) + "%");
            holder.textPrice.setText(f.format(Double.valueOf(modelCoinPortfolio.getModelCoin().getPrice())));

            //кількість монети в доларах
            double usd = Double.valueOf(modelCoinPortfolio.getModelCoinFirebase().getCount()) * Double.valueOf(modelCoinPortfolio.getModelCoin().getPrice());
            if(usd<1000)
                holder.textUSD.setText(String.valueOf(new DecimalFormat("0.00").format(usd))+" USD");
            else if(usd>1000 && usd<10000)
                holder.textUSD.setText(String.valueOf(new DecimalFormat("0.0").format(usd))+" USD");
            else holder.textUSD.setText(String.valueOf(new DecimalFormat("0").format(usd))+" USD");

            if (Double.valueOf(modelCoinPortfolio.getModelCoin().getPrice_change_percentage_24h()) > 0) {
                holder.imageViewPercent.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_plus_percent));
                holder.textPercent.setTextColor(context.getResources().getColor(R.color.percentPlus));
            } else {
                holder.imageViewPercent.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_minus_persent));
                holder.textPercent.setTextColor(context.getResources().getColor(R.color.percentMinus));
            }
        }catch (NullPointerException ex){}
        Glide.with(context)
                .load(modelCoinPortfolio.getModelCoin().getLogoUrl())
                .placeholder(R.drawable.ic_icon_bitcoin)
                .into(holder.imageViewLogo);
    }

    @Override
    public int getItemCount() { return list.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewLogo,imageViewPercent,imageViewMore;
        TextView textName,textId,textPrice,textPercent,textUSD,textCount;
        ClickMoreInfoListener clickMoreInfoListener;
        public ViewHolder(@NonNull View view,ClickMoreInfoListener clickMoreInfoListener) {
            super(view);
            imageViewLogo = view.findViewById(R.id.imageLogoCoinItemPortfolio);
            imageViewPercent = view.findViewById(R.id.imagePriceCoinItemPortfolio);
            textName = view.findViewById(R.id.textNameCoinItemPortfolio);
            textId = view.findViewById(R.id.textIdCoinItemPortfolio);
            textPrice = view.findViewById(R.id.textPriceCoinItemPortfolio);
            textPercent = view.findViewById(R.id.textPercentCoinItemPortfolio);
            imageViewMore = view.findViewById(R.id.imageMorePortfolio);
            textUSD = view.findViewById(R.id.textUSDCoinItemPortfolio);
            textCount = view.findViewById(R.id.textCountCoinItemPortfolio);

            this.clickMoreInfoListener = clickMoreInfoListener;
            imageViewMore.setOnClickListener(this);
        }
        //Клік на кнопку - додаткова інформація
        @Override
        public void onClick(View v) {
            clickMoreInfoListener.onClickMoreInfoPortfolio(getAdapterPosition());
        }
    }
    public interface ClickMoreInfoListener{
        void onClickMoreInfoPortfolio(int position);
    }
}
