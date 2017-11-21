package com.sa.restaurant.mvp.login.view;

/**
 * Created by bansaripatel on 24/10/17.
 */

public interface LoginView
{
    void displayLoginMessage(int message);
    void onLoginSuccess();
    void onSocialLoginSuccess(int  message);
    void moveToSignUp(String email);
    void saveLoginStatus(String value);
}
