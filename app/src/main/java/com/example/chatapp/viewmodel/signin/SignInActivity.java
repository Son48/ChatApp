package com.example.chatapp.viewmodel.signin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.base.BaseMVVMActivity;
import com.example.chatapp.databinding.ActivitySignInBinding;
import com.example.chatapp.viewmodel.signup.SignUpActivity;
import com.example.chatapp.utilities.Constants;
import com.example.chatapp.utilities.PreferenceManager;
import com.example.chatapp.utilities.StringUtils;
import com.example.chatapp.viewmodel.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

//todo apply hilt, mvvm, Rx
public class SignInActivity extends BaseMVVMActivity<ActivitySignInBinding,SignInViewModel> {

    ActivitySignInBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //todo inject bằng Hilt
        preferenceManager = new PreferenceManager(this);

        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            //todo clean code, dunfg this
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        setListeners();

    }

    @Override
    protected Class<SignInViewModel> getViewModelClass() {
        return null;
    }

    @Override
    protected ActivitySignInBinding getLayoutBinding() {
        return ActivitySignInBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void registerViewEvent() {

    }

    @Override
    protected void registerViewModelObs() {

    }

    private void setListeners() {
        binding.textCreateNewAccount.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));
        binding.buttonSignIn.setOnClickListener(view -> {
            if (isValidSignInDetails()) {
                signIn();
            }
        });
    }

    //todo tạo base
//    private void showToast(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }

    private void signIn() {
        loading(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth.signInWithEmailAndPassword(binding.inputEmail.getText().toString(), binding.inputPassword.getText().toString())
                //todo sử dụng hàm success/fail
                .addOnSuccessListener(authResult -> {
                    String userId = firebaseAuth.getCurrentUser().getUid();
                    firebaseFirestore.collection(Constants.KEY_COLLECTION_USER).document(userId).get()
                            .addOnSuccessListener(documentSnapshot -> {
                                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }).addOnFailureListener(e -> {
                        Toast.makeText(this, "Error!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                });
    }

    //todo đưa vào viewmodel
    private Boolean isValidSignInDetails() {
        //todo tạo class utilities
        if (!StringUtils.isEmail(binding.inputEmail.getText().toString()) || TextUtils.isEmpty(binding.inputEmail.getText().toString().trim())) {
            showToast(getString(R.string.enter_email));
            return false;
            //todo TextUtils
        } else if (TextUtils.isEmpty(binding.inputPassword.getText().toString().trim())) {
            //todo add string.xml
            showToast(getString(R.string.enter_password));
            return false;
        } else {
            return true;
        }

    }

}