package com.sa.restaurant.mvp.login.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.sa.restaurant.R;
import com.sa.restaurant.mvp.db.DBUser;
import com.sa.restaurant.mvp.login.view.LoginView;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginPresenterImp extends DBUser implements LoginPresenter
{
    private LoginView loginView;
    public LoginPresenterImp(Context context, LoginView loginView) {
        super(context);
        this.loginView=loginView;
    }

    @Override
    public void validateLogindata(String email, String password)
    {
        if(TextUtils.isEmpty(email))
        {
            loginView.displayLoginMessage(R.string.msg_valid_email);
        }
        else if(TextUtils.isEmpty(password))
        {
            loginView.displayLoginMessage(R.string.msg_valid_password);
        }
        else if(checkUser(email,password))
        {
            if(getUserStatus(email, password).equals("NO"))
            {
                loginView.displayLoginMessage(R.string.msg_login_status);
                loginView.displayLoginMessage(R.string.msg_success);
                loginView.saveLoginStatus("YES");
                loginView.onLoginSuccess();
            }
            else
            {
                loginView.displayLoginMessage(R.string.msg_facebook_login_status);
                loginView.saveLoginStatus("NO");
            }
        }
        else
        {
            loginView.displayLoginMessage(R.string.msg_invalid_login);
        }
    }

    @Override
    public void socialLogin(LoginButton loginButton,CallbackManager  callbackManager)
    {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult)
            {

                Log.e("TAG","success");

                Log.e("TOKEN", "token"+loginResult.getAccessToken().getUserId());
                final String userFacebookId=loginResult.getAccessToken().getUserId();

                GraphRequest graphRequest=GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response)
                    {
                        try {
                          final String email=response.getJSONObject().get("email").toString();
//
                            Log.e("TAG",email);
                            if(getSocialUserId(email)==null || !getSocialUserId(email).equals(userFacebookId))
                            {
                                //signup/login and add record to database when record is not inserted
                                loginView.moveToSignUp(email);
                                boolean value=insertSocialUser(email,userFacebookId);
                                Log.e("VALUE","value"+value);
                            }
                            getUserList();
                            loginView.onSocialLoginSuccess(R.string.msg_social_login_success);
                            loginView.saveLoginStatus("YES");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                final Bundle bundle=new Bundle();
                bundle.putString("fields", "email");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();

            }

            @Override
            public void onCancel() {
                Log.e("TAG","Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("TAG","Error");
            }
        });
    }


}
