package com.example.chatapp.di;


import android.app.Application;

import com.example.chatapp.utilities.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModel {

    @Provides
    @Singleton
    static FirebaseFirestore  provideFirebaseInstance(){return FirebaseFirestore.getInstance();}

    @Provides
    @Singleton
    static FirebaseAuth getAuthInstance(){return FirebaseAuth.getInstance();}

    @Provides
    @Singleton
    static PreferenceManager getPrefrence(Application application){return new PreferenceManager(application);}

}
