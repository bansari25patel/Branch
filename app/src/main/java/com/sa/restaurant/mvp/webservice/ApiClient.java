package com.sa.restaurant.mvp.webservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ApiClient
{
    private static final String BASE_URL="https://maps.googleapis.com/maps/";
    public static final String GOOGLE_PLACE_API_KEY = " AIzaSyCfrRVsTxP5zRzF0uUjUcFoSEU6WupDizo";
    public static Retrofit retrofit=null;
    public static Retrofit getApiClient()
    {
        if(retrofit==null)
        {
            retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();


        }
        return retrofit;
    }


}
