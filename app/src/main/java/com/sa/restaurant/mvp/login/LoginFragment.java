package com.sa.restaurant.mvp.login;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.sa.restaurant.MainActivity;
import com.sa.restaurant.R;
import com.sa.restaurant.mvp.NavActivity;
import com.sa.restaurant.mvp.home.RestaurantFragment;
import com.sa.restaurant.mvp.login.presenter.LoginPresenterImp;
import com.sa.restaurant.mvp.login.view.LoginView;
import com.sa.restaurant.mvp.signup.SignUpFragment;


public class LoginFragment extends Fragment implements View.OnClickListener,LoginView
{
    private LoginPresenterImp loginPresenterImp;
    private EditText edtEmail,edtPassword;
    private View rootView;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {


        rootView= inflater.inflate(R.layout.fragment_login,container,false);

            if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
            {
                rootView= inflater.inflate(R.layout.fragment_login_land,container,false);
            }
            else
            {
                rootView= inflater.inflate(R.layout.fragment_login,container,false);
            }

        Log.e("TAG","oncreate view LoginFragment");

        return rootView;

    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE)
//        {
//
//        }
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initializeComponent(view);

    }

    private void initializeComponent(View view)
    {
        final TextView txtSingUp = view.findViewById(R.id.fragment_login_txt_signup);
        edtEmail=view.findViewById(R.id.fragment_login_edt_email);
        edtPassword=view.findViewById(R.id.fragment_login_edt_password);
        final  TextView btnLogin=view.findViewById(R.id.fragment_login_btn_login);
        loginPresenterImp=new LoginPresenterImp(getActivity(),this);
        btnLogin.setOnClickListener(this);
        txtSingUp.setOnClickListener(this);
        loginButton=view.findViewById(R.id.fragment_login_btn_facebook_login);
        callbackManager=CallbackManager.Factory.create();
        loginButton.setReadPermissions("email");
      //  loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.setFragment(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.fragment_login_txt_signup:
                if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE)
                {
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                }
                getFragmentManager().beginTransaction().replace(R.id.container,new SignUpFragment())
                        .commit();

                break;
            case R.id.fragment_login_btn_login:
                final  String email=edtEmail.getText().toString().trim();
                final String password=edtPassword.getText().toString().trim();
                loginPresenterImp.validateLogindata(email,password);
                break;

            case R.id.fragment_login_btn_facebook_login:
                loginPresenterImp.socialLogin(loginButton,callbackManager);


        }
    }

    @Override
    public void displayLoginMessage(int message)
    {
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLoginSuccess()
    {
        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE)
        {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        }

            //getFragmentManager().beginTransaction().add(R.id.fragment_login_container,new NavigationDrawerFragment()).commit();
            final  Intent homeIntent=new Intent(getActivity(), NavActivity.class);
            startActivity(homeIntent);
            getActivity().finish();


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        }
    }

    @Override
    public void onSocialLoginSuccess(int message)
    {
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
        final  Intent homeIntent=new Intent(getActivity(), NavActivity.class);
        startActivity(homeIntent);
        getActivity().finish();

    }

    @Override
    public void moveToSignUp(String email)
    {
     getFragmentManager().beginTransaction().replace(R.id.fragment_login_container,new SignUpFragment()).addToBackStack(null).commit();
//        final Bundle emailBundle=new Bundle();
//        emailBundle.putString("EMAIL",email);
//        setArguments(emailBundle);
    }

    @Override
    public void saveLoginStatus(String value)
    {
       final SharedPreferences loginPref = getActivity().getSharedPreferences(MainActivity.prefName, Context.MODE_PRIVATE);
       final SharedPreferences.Editor editor = loginPref.edit();
        editor.putString(MainActivity.value,value);
        editor.apply();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
