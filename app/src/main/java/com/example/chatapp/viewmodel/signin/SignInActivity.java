package com.example.chatapp.viewmodel.signin;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.chatapp.R;
import com.example.chatapp.base.BaseActivity;
import com.example.chatapp.base.BaseMVVMActivity;
import com.example.chatapp.databinding.ActivitySignInBinding;
import com.example.chatapp.viewmodel.home.LoadingDialog;
import com.example.chatapp.viewmodel.signup.SignUpActivity;
import com.example.chatapp.utilities.Constants;
import com.example.chatapp.utilities.PreferenceManager;
import com.example.chatapp.utilities.StringUtils;
import com.example.chatapp.viewmodel.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

////todo apply hilt, mvvm, Rx
@AndroidEntryPoint
public class SignInActivity extends BaseMVVMActivity<ActivitySignInBinding,SignInViewModel> {
    //todo inject bằng Hilt



    @Override
    protected Class<SignInViewModel> getViewModelClass() {
        return SignInViewModel.class;
    }

    @Override
    protected ActivitySignInBinding getLayoutBinding() {
        return ActivitySignInBinding.inflate(getLayoutInflater());
    }
    @Inject
    PreferenceManager preferenceManager;
    @Override
    protected void initialize() {

        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            //todo clean code, dunfg this
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        setListeners();
    }

    @Override
    protected void registerViewEvent() {

    }

    @Override
    protected void registerViewModelObs() {

    }

    private void setListeners() {
        String email=getLayoutBinding().inputEmail.getText().toString();
        String password=getLayoutBinding().inputPassword.getText().toString();
        getLayoutBinding().textCreateNewAccount.setOnClickListener(v ->
                startActivity(new Intent(this, SignUpActivity.class)));
        getLayoutBinding().buttonSignIn.setOnClickListener(view -> {
            if (getViewModel().isValidSignInDetails(email,password)) {
                signIn();
            }
        });
    }

    //todo tạo base
//    private void showToast(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }

    private void signIn() {
        showLoading();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth.signInWithEmailAndPassword(getLayoutBinding().inputEmail.getText().toString(), getLayoutBinding().inputPassword.getText().toString())
                //todo sử dụng hàm success/fail
                .addOnSuccessListener(authResult -> {
                    String userId = firebaseAuth.getCurrentUser().getUid();
                    firebaseFirestore.collection(Constants.KEY_COLLECTION_USER).document(userId).get()
                            .addOnSuccessListener(documentSnapshot -> {
                                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                                Intent intent = new Intent(this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }).addOnFailureListener(e -> {
                        Toast.makeText(this, "Error!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                });
    }

//    //todo đưa vào viewmodel
//    private Boolean isValidSignInDetails() {
//        //todo tạo class utilities
//        if (StringUtils.isEmail(binding.inputEmail.getText().toString().trim())){
//            showToast(getString(R.string.enter_email));
//            return false;
//            //todo TextUtils
//        } else if (StringUtils.isPassword(binding.inputPassword.getText().toString().trim())) {
//            //todo add string.xml
//            showToast(getString(R.string.enter_password));
//            return false;
//        } else {
//            return true;
//        }
//
//    }

}