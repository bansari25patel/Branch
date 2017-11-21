package com.sa.restaurant.mvp.signup.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.sa.restaurant.R;
import com.sa.restaurant.mvp.db.DBUser;
import com.sa.restaurant.mvp.signup.view.SignUpView;



public class SignUpPresenterImp extends DBUser  implements SignUpPresenter
{
    private SignUpView signUpView;
    private static final String PasswordPattern =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{4,}$";




    public SignUpPresenterImp(Context context,SignUpView signUpView)
    {
       super(context);
        this.signUpView = signUpView;
    }



    @Override
    public void validateCredentials(String email, String password, String mobile, String name, String status)
    {
        if(TextUtils.isEmpty(email))
        {
            signUpView.displayValidationMessage(R.string.msg_valid_email);
        }
        else if(TextUtils.isEmpty(password))
        {
            signUpView.displayValidationMessage(R.string.msg_valid_password);
        }
        else if(TextUtils.isEmpty(name))
        {
            signUpView.displayValidationMessage(R.string.msg_invalid_name);
        }
        else if(TextUtils.isEmpty(mobile))
        {
            signUpView.displayValidationMessage(R.string.msg_invalid_mobile);
        }
        else if(!(mobile.length()==10))
        {
            signUpView.displayValidationMessage(R.string.msg_invalid_mobile);
        }
        else if(password.length()<8)
        {
            signUpView.displayValidationMessage(R.string.msg_valid_length_password);
        }
        else if(!password.matches(PasswordPattern))
        {
            signUpView.displayValidationMessage(R.string.msg_valid_password);
        }
        else
        {
            signUpView.displayValidationMessage(R.string.msg_success);
            boolean value=insertUser(email,password,mobile, name,status);
            //getUserList(username,password);
            signUpView.onSignUpSuccess();

        }

    }
}
