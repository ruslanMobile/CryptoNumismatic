package com.example.cryptonumismatic.BottomSheets;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.cryptonumismatic.R;
import com.example.cryptonumismatic.ViewModel.ViewModelBottomSheet;
import com.example.cryptonumismatic.models.ModelCoin;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomBottomSheet extends BottomSheetDialogFragment {
    private Bundle bundle;
    private TextView textId,textName;
    private CircleImageView circleImageView;
    private Button addElementButton;
    private TextInputEditText textInputEditTextPrice,textInputEditTextCount,textInputEditTextDate;
    private ModelCoin coin;
    private ViewModelBottomSheet viewModelBottomSheet;
    private ImageView imageViewClose;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet,container,false);
        textId = view.findViewById(R.id.textIdBottomSheet);
        textName = view.findViewById(R.id.textNameBottomSheet);
        circleImageView = view.findViewById(R.id.circleImageLogoBottomSheet);
        addElementButton = view.findViewById(R.id.buttonAddCoinBottomSheet);
        addElementButton.setOnClickListener(addElementListener);
        textInputEditTextPrice = view.findViewById(R.id.editInputPriceBottomSheet);
        textInputEditTextCount = view.findViewById(R.id.editInputQuantityBottomSheet);
        textInputEditTextDate = view.findViewById(R.id.editInputDateBottomSheet);
        imageViewClose = view.findViewById(R.id.imageCloseBottomSheet);
        imageViewClose.setOnClickListener((a)-> dismiss());

        bundle = getArguments();
        coin = (ModelCoin) bundle.getSerializable("coin");
        textId.setText(coin.getId());
        textName.setText("("+coin.getName()+")");
        uploadPhoto(coin);

        viewModelBottomSheet = new ViewModelProvider(this).get(ViewModelBottomSheet.class);
        return view;
    }

    //Перевірка, в кому розширенні приходить картинка. Відповідно до того загружати її
    public void uploadPhoto(ModelCoin coin){
            Glide.with(getActivity())
                    .load(coin.getLogoUrl())
                    .placeholder(R.drawable.ic_icon_bitcoin)
                    .into(circleImageView);
    }

    //Слухач додавання нового елементу
    View.OnClickListener addElementListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(checkEditText()) {
                viewModelBottomSheet.addElementToFirebase(coin.getFull_id(), textInputEditTextPrice.getText().toString(),
                        textInputEditTextCount.getText().toString(), textInputEditTextDate.getText().toString());
                dismiss();
            }
        }
    };
    //Перевірка, чи всі поля BottomSheet були заповнені
    public boolean checkEditText(){
        if(textInputEditTextPrice.getText().toString().trim().isEmpty())
            textInputEditTextPrice.setHintTextColor(getResources().getColor(R.color.redText));
        else textInputEditTextPrice.setHintTextColor(getResources().getColor(R.color.textColorHint));

        if(textInputEditTextCount.getText().toString().trim().isEmpty())
            textInputEditTextCount.setHintTextColor(getResources().getColor(R.color.redText));
        else textInputEditTextCount.setHintTextColor(getResources().getColor(R.color.textColorHint));

        if(textInputEditTextDate.getText().toString().trim().isEmpty())
            textInputEditTextDate.setHintTextColor(getResources().getColor(R.color.redText));
        else textInputEditTextDate.setHintTextColor(getResources().getColor(R.color.textColorHint));

        if(textInputEditTextPrice.getText().toString().trim().isEmpty() || textInputEditTextCount.getText().toString().trim().isEmpty()
        || textInputEditTextDate.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(),"Fill in all fields",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
