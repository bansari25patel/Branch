package com.sa.restaurant.mvp.webservice;


import com.sa.restaurant.mvp.weather.model.WeatherInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherAPI {

    String BASE_URL = "http://api.openweathermap.org/data/2.5/";
  String API_KEY = "4fdcdb983d406f886797f5df31040e2d";



    @GET("weather?")
    Call<WeatherInfo> getCurrentWeather(@Query("lat") String lat,
                                        @Query("lon") String lon,
                                        @Query("appid") String appid,
                                        @Query("units") String units);
}