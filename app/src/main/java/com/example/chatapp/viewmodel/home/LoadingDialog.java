package com.example.chatapp.viewmodel.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import com.example.chatapp.R;
import com.example.chatapp.databinding.ActivityLoadingDialogBinding;

public class LoadingDialog extends Dialog {
    private ActivityLoadingDialogBinding binding;
    public LoadingDialog(@NonNull Context context) {
        super(context);
        binding=ActivityLoadingDialogBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(binding.getRoot());
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

}