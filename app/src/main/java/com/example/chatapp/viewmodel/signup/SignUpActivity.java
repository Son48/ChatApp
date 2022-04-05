package com.example.chatapp.viewmodel.signup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.base.BaseActivity;
import com.example.chatapp.base.BaseMVVMActivity;
import com.example.chatapp.databinding.ActivitySignInBinding;
import com.example.chatapp.model.UserModel;
import com.example.chatapp.databinding.ActivitySignUpBinding;
import com.example.chatapp.utilities.Constants;
import com.example.chatapp.utilities.PreferenceManager;
import com.example.chatapp.viewmodel.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpActivity  extends BaseMVVMActivity<ActivitySignUpBinding,SignUpViewModel> {


    private String endcodeImage;

    @Override
    protected Class<SignUpViewModel> getViewModelClass() {
        return SignUpViewModel.class;
    }

    @Override
    protected ActivitySignUpBinding getLayoutBinding() {
        return ActivitySignUpBinding.inflate(getLayoutInflater());
    }

    @Inject
    PreferenceManager preferenceManager;
    @Override
    protected void initialize() {
        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
        String confirmPassword=getLayoutBinding().inputConfirmPassword.getText().toString();
        String name =getLayoutBinding().inputName.getText().toString();
        getLayoutBinding().textSignIn.setOnClickListener(v ->
                onBackPressed());
        getLayoutBinding().buttonSignUp.setOnClickListener(v -> {
            if (getViewModel().isValidSignUpDetails(endcodeImage,email,password,confirmPassword)) {
                signUp();
            }
        });
        getLayoutBinding().layoutImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            pickImage.launch(intent);
        });
    }

    private void signUp() {
        loading(true);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(getLayoutBinding().inputEmail.getText().toString(), getLayoutBinding().inputPassword.getText().toString())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        String image=endcodeImage;
                        String inputName=getLayoutBinding().inputName.getText().toString().trim();
                        String inputEmail=getLayoutBinding().inputEmail.getText().toString().trim();
                        String inputPassword= getLayoutBinding().inputPassword.getText().toString().trim();
                        String id=firebaseAuth.getUid();
                        UserModel userModel=new UserModel(image,inputName,inputEmail,inputPassword,id);

                        firebaseFirestore.collection(Constants.KEY_COLLECTION_USER)
                                .document(id)
                                .set(userModel);
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(this, "Error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
}

    private String endcodeImage(Bitmap bitmap) {
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        getLayoutBinding().imageProfile.setImageBitmap(bitmap);
                        getLayoutBinding().textAddImage.setVisibility(View.GONE);
                        endcodeImage = endcodeImage(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
    );


    private void loading(Boolean isloading) {
        if (isloading) {
            showLoading();
        } else {
             hideLoading();
        }
    }
}