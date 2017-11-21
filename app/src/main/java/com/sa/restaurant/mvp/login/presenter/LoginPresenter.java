package com.sa.restaurant.mvp.login.presenter;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

/**
 * Created by bansaripatel on 24/10/17.
 */

public interface LoginPresenter
{
    void validateLogindata(String email,String password);
    void socialLogin(LoginButton loginButton, CallbackManager callbackManager);


}
