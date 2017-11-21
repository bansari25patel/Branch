package com.sa.restaurant.mvp.signup;

import android.app.Fragment;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sa.restaurant.R;
import com.sa.restaurant.mvp.login.LoginFragment;
import com.sa.restaurant.mvp.signup.presenter.SignUpPresenterImp;
import com.sa.restaurant.mvp.signup.view.SignUpView;



public class SignUpFragment extends Fragment implements View.OnClickListener,SignUpView
{
    private EditText edtEmail,edtPassword,edtName,edtMobile;
    private SignUpPresenterImp signUpPresenterImp;
    private TextView btnSignup;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {

        if(rootView==null)
        {

           rootView= inflater.inflate(R.layout.fragment_signup,container,false);
        }
//        if(rootView==null)
//        {
//        }

//        edtEmail=view.findViewById(R.id.fragment_signup_edt_email);
//        edtPassword=view.findViewById(R.id.fragment_signup_edt_password);
//        btnSignup = view.findViewById(R.id.fragment_signup_btn_signup);
//        signUpPresenterImp=new SignUpPresenterImp(getActivity(),this);
//        btnSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(),"CAll",Toast.LENGTH_LONG).show();
//            }
//        });
        return rootView ;



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initializeComponent(view);



    }

    private void initializeComponent(View view)
    {
//        Bundle emailBundle=getArguments();
//        if(emailBundle.getString("EMAUL")!=null)
//        {
//            edtEmail.setText(emailBundle.getString("EMAIL"));
//        }
        edtEmail=view.findViewById(R.id.fragment_signup_edt_email);
        edtName=view.findViewById(R.id.fragment_signup_edt_username);
        edtMobile=view.findViewById(R.id.fragment_signup_edt_mobile);
        edtPassword=view.findViewById(R.id.fragment_signup_edt_password);
        btnSignup = view.findViewById(R.id.fragment_signup_btn_signup);
        signUpPresenterImp=new SignUpPresenterImp(getActivity(),this);
        btnSignup.setOnClickListener(this);



    }

    @Override
    public void displayValidationMessage(int message)
    {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSignUpSuccess( )
    {

       getFragmentManager().beginTransaction().add(R.id.fragment_signup_container,new LoginFragment()).commit();

    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.fragment_signup_btn_signup:
                final String email=edtEmail.getText().toString().trim();
                final String password=edtPassword.getText().toString().trim();
                final String mobile=edtMobile.getText().toString().trim();
                final String name=edtName.getText().toString().trim();
                final String status="NO";
                signUpPresenterImp.validateCredentials(email,password,mobile,name,status);
                break;


        }

    }

}
