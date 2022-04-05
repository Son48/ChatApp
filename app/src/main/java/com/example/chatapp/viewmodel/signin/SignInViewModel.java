package com.example.chatapp.viewmodel.signin;

import android.text.TextUtils;

import com.example.chatapp.R;
import com.example.chatapp.base.BaseViewModel;
import com.example.chatapp.model.UserModel;
import com.example.chatapp.utilities.StringUtils;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SignInViewModel extends BaseViewModel {


    @Inject
    public SignInViewModel(){

    }
//    //todo đưa vào viewmodel

    public Boolean isValidSignInDetails(String email,String pasword) {
        //todo tạo class utilities
        if (StringUtils.isEmail(email)){
            showErrorMessage(String.valueOf(R.string.enter_email));
            return false;
            //todo TextUtils
        } else if (StringUtils.isAddressEmail(email)) {
            //todo add string.xml
            showErrorMessage(String.valueOf(R.string.email_error));
            return false;
        } else if(StringUtils.isPassword(pasword)){
            showErrorMessage(String.valueOf(R.string.password));
            return false;
        }else {
            return true;
        }
    }
}
