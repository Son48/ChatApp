package com.example.chatapp.model;

import android.app.Application;

import com.example.chatapp.utilities.Constants;
import com.example.chatapp.utilities.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AppRepository {
    private Application application;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private PreferenceManager preferenceManager;

    public AppRepository(Application application){
        this.application=application;
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        preferenceManager = new PreferenceManager(application.getApplicationContext());

    }
    public void signIn(String email,String password){
        firebaseAuth.signInWithEmailAndPassword(email,password);

    }

    public void setuserID(){
        String userId = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection(Constants.KEY_COLLECTION_USER).document(userId).get();
    }
    public void signUp(){

    }
}
