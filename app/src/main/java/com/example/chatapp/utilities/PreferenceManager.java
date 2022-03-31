package com.example.chatapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.firestore.auth.User;

//todo Apply Hilt
public class PreferenceManager {
    private final SharedPreferences sharedPreferences;

    public PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE);

    }

    public void putBoolean(String key, Boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public Boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void putString(String key, String value) {
        //todo clean code
        sharedPreferences
                .edit()
                .putString(key, value)
                .commit();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void clear() {
        sharedPreferences
                .edit()
                .clear()
                .apply();
    }
}
