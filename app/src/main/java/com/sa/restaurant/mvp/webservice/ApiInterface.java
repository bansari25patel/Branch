package com.sa.restaurant.mvp.webservice;

import com.sa.restaurant.mvp.home.model.Place;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface
{
    @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyACI8arhAghHd0_QL0S9dzYSxOQJdWkJrs")
    Call<Place> getNearbyPlaces(@Query("type") String type,
                                @Query("location") String location,
                                @Query("radius") int radius,
                                @Query("per_page") int perPage,
                                @Query("limit") int limit);



}
