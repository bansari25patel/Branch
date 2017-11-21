package com.sa.restaurant.mvp.signup.presenter;

/**
 * Created by bansaripatel on 24/10/17.
 */

public interface SignUpPresenter
{
    void validateCredentials(String email, String password, String mobile, String name, String status);
}
