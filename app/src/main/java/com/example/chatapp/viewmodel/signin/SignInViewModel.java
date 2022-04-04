package com.example.chatapp.viewmodel.signin;

import android.text.TextUtils;

import com.example.chatapp.R;
import com.example.chatapp.base.BaseViewModel;
import com.example.chatapp.model.UserModel;
import com.example.chatapp.utilities.StringUtils;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;


public class SignInViewModel extends BaseViewModel {


//    //todo đưa vào viewmodel
//    @Inject
//    public Boolean isValidSignInDetails() {
//        UserModel userModel=new UserModel();
//        //todo tạo class utilities
//        if (StringUtils.isEmail(userModel.getEmail())){
//            showErrorMessage(String.valueOf(R.string.enter_email));
//            return false;
//            //todo TextUtils
//        } else if (StringUtils.isAddressEmail(userModel.getEmail())) {
//            //todo add string.xml
//            showErrorMessage(String.valueOf(R.string.email_error));
//            return false;
//        } else if(StringUtils.isPassword(userModel.getPassword())){
//            showErrorMessage(String.valueOf(R.string.password));
//            return false;
//        }else {
//            return true;
//        }
//    }
}
