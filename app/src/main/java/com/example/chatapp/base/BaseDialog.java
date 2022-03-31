package com.example.chatapp.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

public abstract class BaseDialog<V extends ViewBinding> extends Dialog {
    private V binding;

    protected V getBinding() {
        return binding;
    }

    public BaseDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        binding = getLayoutBinding();
        setContentView(binding.getRoot());
        initialize();
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    protected abstract V getLayoutBinding();

    protected abstract void initialize();
}
