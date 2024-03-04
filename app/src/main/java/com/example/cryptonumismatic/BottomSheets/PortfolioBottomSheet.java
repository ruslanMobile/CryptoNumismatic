package com.example.cryptonumismatic.BottomSheets;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.cryptonumismatic.R;
import com.example.cryptonumismatic.ViewModel.ViewModelBottomSheet;
import com.example.cryptonumismatic.models.ModelCoin;
import com.example.cryptonumismatic.models.ModelCoinPortfolio;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class PortfolioBottomSheet extends BottomSheetDialogFragment {
    private Bundle bundle;
    private ModelCoinPortfolio modelCoinPortfolio;
    private ImageView imageClose,circleImageProfit;
    private CircleImageView circleImageLogo;
    private TextView textDate,textId,textName,textPercent,textUSDChange,textPrice,textCount;
    private Button buttonDelete;
    private ViewModelBottomSheet viewModelBottomSheet;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModelBottomSheet = new ViewModelProvider(requireActivity()).get(ViewModelBottomSheet.class);
        View view = inflater.inflate(R.layout.bottom_sheet_portfolio,container,false);
        bundle = getArguments();
        modelCoinPortfolio = (ModelCoinPortfolio) bundle.getSerializable("coin");
        circleImageLogo = view.findViewById(R.id.circleImageLogoPortfolioBottomSheet);
        circleImageProfit = view.findViewById(R.id.circleImageProfitPortfolioBottomSheet);
        textDate = view.findViewById(R.id.textDatePortfolioBottomSheet);
        textId = view.findViewById(R.id.textIdPortfolioBottomSheet);
        textName = view.findViewById(R.id.textNamePortfolioBottomSheet);
        textPercent = view.findViewById(R.id.textPercentPortfolioBottomSheet);
        textUSDChange = view.findViewById(R.id.textUSDChangePortfolioBottomSheet);
        textPrice = view.findViewById(R.id.textUSDPricePortfolioBottomSheet);
        textCount = view.findViewById(R.id.textCountPortfolioBottomSheet);
        imageClose = view.findViewById(R.id.imageClosePortfolioBottomSheet);
        buttonDelete = view.findViewById(R.id.buttonDelete);

        buttonDelete.setOnClickListener((a)->{
            viewModelBottomSheet.deleteElementFromFirebase(modelCoinPortfolio.getModelCoinFirebase().getHashId());
            viewModelBottomSheet.getAllElementsFromFirebase();
            dismiss();
        });
        //Встановлення всіх властивостей BottomSheet
        imageClose.setOnClickListener((a)->dismiss());
        textId.setText(modelCoinPortfolio.getModelCoin().getId());
        textName.setText("(" + modelCoinPortfolio.getModelCoin().getName() + ")");
        textDate.setText(modelCoinPortfolio.getModelCoinFirebase().getDate());
        textPercent.setText(String.valueOf(new DecimalFormat("0.0").format(100*(Double.valueOf(modelCoinPortfolio.getModelCoin().getPrice())-Double.valueOf(modelCoinPortfolio.getModelCoinFirebase().getPrice()))/Double.valueOf(modelCoinPortfolio.getModelCoinFirebase().getPrice())))+"%");
        textPrice.setText("USD" + new DecimalFormat("0.0").format(Double.valueOf(modelCoinPortfolio.getModelCoinFirebase().getPrice())));
        String usdChange = new DecimalFormat("0.0").format((Double.valueOf(modelCoinPortfolio.getModelCoin().getPrice()) * Double.valueOf(modelCoinPortfolio.getModelCoinFirebase().getCount())) -
                ((Double.valueOf(modelCoinPortfolio.getModelCoinFirebase().getPrice()) * Double.valueOf(modelCoinPortfolio.getModelCoinFirebase().getCount()))));
        textUSDChange.setText("USD" + usdChange);
        textCount.setText(new DecimalFormat("0.0").format(Double.valueOf(modelCoinPortfolio.getModelCoinFirebase().getCount())));

        if(Double.parseDouble(usdChange.replaceAll(",",".")) < 0) {
            textUSDChange.setTextColor(getResources().getColor(R.color.percentMinus));
            circleImageProfit.setImageResource(R.drawable.ic_icon_price_down);
        }
        else {
            textUSDChange.setTextColor(getResources().getColor(R.color.percentPlus));
            circleImageProfit.setImageResource(R.drawable.ic_icon_price_up);
        }

        uploadPhoto();

        return view;
    }

    //Перевірка, в кому розширенні приходить картинка. Відповідно до того загружати її
    public void uploadPhoto(){
            Glide.with(getActivity())
                    .load(modelCoinPortfolio.getModelCoin().getLogoUrl())
                    .placeholder(R.drawable.ic_icon_bitcoin)
                    .into(circleImageLogo);
    }
}
