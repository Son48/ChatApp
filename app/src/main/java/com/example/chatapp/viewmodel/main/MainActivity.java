package com.example.chatapp.viewmodel.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.databinding.ActivityMainBinding;
import com.example.chatapp.viewmodel.signin.SignInActivity;
import com.example.chatapp.utilities.Constants;
import com.example.chatapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String userId;
    private ActivityMainBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        loadUserDetails();
        getToken();
        setListeners();
    }

    private void setListeners() {
        binding.imageSignOut.setOnClickListener(view -> {
            signOut();
        });
    }

    private void loadUserDetails() {
        firebaseFirestore
                .collection(Constants.KEY_COLLECTION_USER)
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        binding.textName.setText(documentSnapshot.getString(Constants.KEY_NAME));
                        byte[] bytes = Base64.decode(documentSnapshot.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        binding.imageProfile.setImageBitmap(bitmap);
                    }
                });

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void updateToken(String token) {
        //todo clean code
        firebaseFirestore.collection(Constants.KEY_COLLECTION_USER)
                .document(userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            documentSnapshot.getString(Constants.KEY_USER_ID);
                        }
                    }
                });
        firebaseFirestore.collection(Constants.KEY_COLLECTION_USER)
                .document(userId)
                .update(Constants.KEY_FCM_TOKEN, token)
                .addOnSuccessListener(unused -> {
                    showToast("Token updated successfully");
                })
                .addOnFailureListener(e -> {
                    showToast("Unable ot update token");
                });
    }

    private void signOut() {
        showToast("Signing out...");
        HashMap<String, Object> updates = new HashMap<>();
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        firebaseFirestore.collection(Constants.KEY_COLLECTION_USER)
                .document(userId)
                .update(updates)
                .addOnSuccessListener(unused -> {
                    firebaseAuth.signOut();
                    preferenceManager.clear();
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                    finish();
                });
    }
}