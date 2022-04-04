package com.example.chatapp.utilities;

import android.text.TextUtils;
import android.util.Patterns;

public class StringUtils {
    public static boolean isAddressEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public static boolean isEmail(String email) {
        return TextUtils.isEmpty(email);
    }
    public static boolean isName(String name) {
        return TextUtils.isEmpty(name);
    }
    public static boolean isPassword(String password) {
        return TextUtils.isEmpty(password);
    }
    public static boolean isCornfirmPassword(String confirmPassword) {
        return TextUtils.isEmpty(confirmPassword);
    }
    public static boolean isPasswordCharactor(String passwordCharactor) {
        return passwordCharactor.length()<6;
    }
    public static boolean isPasswordConfirmPassword(String password,String confirmPassword) {
        return !password.equals(confirmPassword);
    }
    public static boolean isImage(String image) {
        return image==null;
    }
}
