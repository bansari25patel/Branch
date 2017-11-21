package
        com.sa.restaurant.mvp.service;

import android.app.IntentService;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


import com.sa.restaurant.MainActivity;
import com.sa.restaurant.mvp.weather.model.WeatherInfo;
import com.sa.restaurant.mvp.webservice.WeatherAPI;
import com.sa.restaurant.mvp.webservice.WeatherClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherService extends IntentService
{
    public WeatherService() {
        super("NAme");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
        Toast.makeText(this,"CAll",Toast.LENGTH_SHORT).show();
        final SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.prefName, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        String latitude = sharedPreferences.getString("LATITUDE", "");
        String longitude = sharedPreferences.getString("LONGITUDE", "");
        WeatherAPI weatherAPI = WeatherClient.getInstance().create(WeatherAPI.class);
        Call<WeatherInfo> infoCall= weatherAPI.getCurrentWeather(latitude,longitude,WeatherAPI.API_KEY,"metric");
        infoCall.enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                if(response!=null)
                {
                   WeatherInfo weatherInfo=response.body();
                    editor.putString("NAME",weatherInfo.getName());
                    editor.putString("TEMP",String.valueOf(weatherInfo.getMain().getTemp()));
                    editor.putString("HUMIDITY",String.valueOf(weatherInfo.getMain().getHumidity()));
                    editor.putString("PRESSURE",String.valueOf(weatherInfo.getMain().getPressure()));
                    editor.putString("CONDITION",weatherInfo.getWeather().get(0).getDescription());
                    editor.putString("WINDSPEED",String.valueOf(weatherInfo.getWind().getSpeed()));
                    editor.apply();
                    Log.e("TAG",String.valueOf(weatherInfo.getName()));

                }
                else
                {
                    Log.e("RESPONSE","Null");
                }
            }

            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {
                Log.e("RESPONSE",""+t);
            }
        });


    }
//    private boolean isRunning;
//
//
//    private Thread backgroundThread;
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//
//        this.isRunning = false;
//        this.backgroundThread = new Thread(myTask);
//    }
//
//    private Runnable myTask = new Runnable() {
//        public void run() {
//            Log.e("mytask","mytask");
//            Log.e("WEATHER","SERVICE CALL");
//            new WeatherAync().execute(WeatherService.this);
//
//
//        }
//    };
//
//    @Override
//    public void onDestroy() {
//        this.isRunning = false;
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        if(!this.isRunning) {
//            this.isRunning = true;
//            this.backgroundThread.start();
//        }
//        return START_STICKY;
//    }



}