package com.sa.restaurant.mvp.home.presenter;

import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.sa.restaurant.mvp.home.model.FavouriteRestaurant;
import com.sa.restaurant.mvp.home.model.Result;

import java.util.List;


public interface RestaurantPresenter
{
   //ArrayList findUnAskedPermissions(Context context,ArrayList arrayList);
    // void  setUpMap(GoogleMap googleMap);
    boolean CheckGooglePlayServices();
    void buildGoogleApiClient();
    boolean checkLocationPermission();
    void displayMap(GoogleMap googleMap);
    List<Result> getResponseList();
    void getResponseList(List<Result> list);
    GoogleMap getGoogleMapInstance( );
        void addFavouriteRestaurant(View view, int position, FavouriteRestaurant resultList);
        List getFavRestaurant();
    void getList(List<Result> result);
    String getRestaurantAddress(String name);


   // void getCurrentLocation();



}
