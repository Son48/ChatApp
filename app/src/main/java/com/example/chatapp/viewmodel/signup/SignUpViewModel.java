package com.example.chatapp.viewmodel.signup;

import android.text.TextUtils;
import android.util.Patterns;

import com.example.chatapp.R;
import com.example.chatapp.base.BaseViewModel;
import com.example.chatapp.utilities.PreferenceManager;
import com.example.chatapp.utilities.StringUtils;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SignUpViewModel extends BaseViewModel {
    @Inject
    public SignUpViewModel(){

    }
    public Boolean isValidSignUpDetails(String endcodeImage,String email,String password,String confirmPassword) {
        if (endcodeImage == null) {

            return false;
        } else if (TextUtils.isEmpty(endcodeImage)) {

            return false;
        } else if (StringUtils.isEmail(email)) {

            return false;
        } else if (StringUtils.isPassword(password)) {

            return false;
        } else if (StringUtils.isCornfirmPassword(confirmPassword)) {
            return false;
        } else if (StringUtils.isPasswordCharactor(confirmPassword)) {

            return false;
        } else if (StringUtils.isPasswordConfirmPassword(password,confirmPassword)) {

            return false;
        } else {
            return true;
        }

    }

}
