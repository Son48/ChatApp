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

public class SignUpActivity  extends BaseActivity {

    private ActivitySignUpBinding binding;
    private PreferenceManager preferenceManager;
    private String endcodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        setListeners();

    }

    private void setListeners() {
        binding.textSignIn.setOnClickListener(v ->
                onBackPressed());
        binding.buttonSignUp.setOnClickListener(v -> {
            if (isValidSignUpDetails()) {
                signUp();
            }
        });
        binding.layoutImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            pickImage.launch(intent);
        });
    }

    private void signUp() {
        loading(true);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

//        Map<String, Object> user = new HashMap<>();
//        user.put(Constants.KEY_IMAGE, endcodeImage);
//        user.put(Constants.KEY_NAME, binding.inputName.getText().toString().trim());
//        user.put(Constants.KEY_EMAIL, binding.inputEmail.getText().toString().trim());
//        user.put(Constants.KEY_PASSWORD, binding.inputPassword.getText().toString().trim());

        firebaseAuth.createUserWithEmailAndPassword(binding.inputEmail.getText().toString(), binding.inputPassword.getText().toString())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        String image=endcodeImage;
                        String inputName=binding.inputName.getText().toString().trim();
                        String inputEmail=binding.inputEmail.getText().toString().trim();
                        String inputPassword= binding.inputPassword.getText().toString().trim();
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
                        binding.imageProfile.setImageBitmap(bitmap);
                        binding.textAddImage.setVisibility(View.GONE);
                        endcodeImage = endcodeImage(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    private Boolean isValidSignUpDetails() {
        if (endcodeImage == null) {
            showToast(getString(R.string.select_profile_image));
            return false;
        } else if (TextUtils.isEmpty(binding.inputName.getText().toString())) {
            showToast(getString(R.string.enter_name));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.getText().toString()).matches() || binding.inputEmail.getText().toString().trim().isEmpty()) {
            showToast(getString(R.string.enter_email));
            return false;
        } else if (binding.inputPassword.getText().toString().trim().isEmpty()) {
            showToast(getString(R.string.enter_password));
            return false;
        } else if (binding.inputConfirmPassword.getText().toString().trim().isEmpty()) {
            showToast(getString(R.string.confirm_password));
            return false;
        } else if (binding.inputPassword.getText().toString().length() < 6) {
            showToast(getString(R.string.password_must_be_more_than_6_characters));
            return false;
        } else if (!binding.inputPassword.getText().toString().equals(binding.inputConfirmPassword.getText().toString())) {
            showToast(getString(R.string.password_and_confirm_password_must_be_same));
            return false;
        } else {
            return true;
        }

    }

    private void loading(Boolean isloading) {
        if (isloading) {
            showLoading();
        } else {
             hideLoading();
        }
    }
}