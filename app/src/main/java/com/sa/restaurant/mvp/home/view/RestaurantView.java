package com.sa.restaurant.mvp.home.view;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.sa.restaurant.mvp.home.model.Location;
import com.sa.restaurant.mvp.home.model.Result;

import java.util.List;


public interface RestaurantView
{

 void setAdapter(List<Result> resultList);
 void showProgress();
 void hideProgress();
   // void getResultList(List<Result> resultList);
}
