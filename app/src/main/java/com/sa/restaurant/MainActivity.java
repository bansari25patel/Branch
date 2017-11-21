package com.sa.restaurant;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsResult;
import com.sa.restaurant.mvp.NavActivity;
import com.sa.restaurant.mvp.login.LoginFragment;
import com.sa.restaurant.mvp.receiver.WeatherReceiver;
import com.sa.restaurant.mvp.service.LocationService;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity
{
    public static final String prefName = "mypref";
    public static final String value = "VALUE_KEY";
    private static final int REQUEST_PERMISSIONS = 100;
    boolean boolean_permission;
    private static final int PERMISSION_REQUEST_CODE = 200;
  private   SharedPreferences sharedpreference;
    GoogleApiClient client;
    LocationRequest mLocationRequest;
    static final Integer GPS_SETTINGS = 0x7;
    PendingResult<LocationSettingsResult> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        client = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();

//
//                if (boolean_permission) {
//
//
//                        Intent intent = new Intent(getApplicationContext(), GoogleService.class);
//                        startService(intent);
//
//                    }
//                 else {
//                    Toast.makeText(getApplicationContext(), "Please enable the gps", Toast.LENGTH_SHORT).show();
//                    fn_permission();
//                }






        scheduleAlarm();
        sharedpreference = getSharedPreferences(prefName,
                Context.MODE_PRIVATE);
        if(!checkPermission())
        {
            requestPermission();
}
        else {
            //already login-check status
            if (sharedpreference.getString(value, "").equals("YES")) {
                final Intent homeIntent = new Intent(MainActivity.this, NavActivity.class);
                startActivity(homeIntent);
                finish();


            } //move to login
            else
                {
                LoginFragment loginFragment = new LoginFragment();
                Bundle bundle = new Bundle();
                bundle.putString("LOGIN", "login");
                loginFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().add(R.id.container, loginFragment).commit();
            }
        }


    }
//
    private void scheduleAlarm()
    {

       final Intent alarm = new Intent(getBaseContext(), WeatherReceiver.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(getBaseContext(), 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        if(!alarmRunning) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), AlarmManager.INTERVAL_HOUR, pendingIntent);
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean FinelocationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean CourseLocationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (FinelocationAccepted && CourseLocationAccepted)
                    { Toast.makeText(MainActivity.this,"Ok Permission Granted",Toast.LENGTH_SHORT).show();
                        // start locatiion service
                         Intent intent = new Intent(getApplicationContext(), LocationService.class);
                        startService(intent);
//
                        LoginFragment loginFragment = new LoginFragment();
                        loginFragment.setArguments(getIntent().getExtras());
                        getFragmentManager().beginTransaction().add(R.id.container, loginFragment,"LOGIN").commit();

                    }

                    else {
                        onBackPressed();
                        Toast.makeText(MainActivity.this,"Permission Cancle",Toast.LENGTH_SHORT).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {

        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this,ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(MainActivity.this, ACCESS_COARSE_LOCATION);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            this.finish();
        }
    }

}
