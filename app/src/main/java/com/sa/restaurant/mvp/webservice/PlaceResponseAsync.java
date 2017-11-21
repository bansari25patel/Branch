package com.sa.restaurant.mvp.webservice;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.common.api.Response;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sa.restaurant.MainActivity;
import com.sa.restaurant.mvp.adapter.RestaurantAdapter;
import com.sa.restaurant.mvp.home.RestaurantFragment;
import com.sa.restaurant.mvp.home.model.FavouriteRestaurant;
import com.sa.restaurant.mvp.home.model.Location;
import com.sa.restaurant.mvp.home.model.Place;
import com.sa.restaurant.mvp.home.model.Restaurant;
import com.sa.restaurant.mvp.home.model.Result;
import com.sa.restaurant.mvp.home.presenter.RestaurantPresenterImp;
import com.sa.restaurant.mvp.home.view.RestaurantView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;


public class PlaceResponseAsync extends AsyncTask<Context,Void,List<Result>>
{
    private RestaurantView restaurantView;
    private Context context;
    private ProgressBar progressBar;
    private RestaurantPresenterImp restaurantPresenterImp;

    public PlaceResponseAsync(RestaurantView restaurantView,  RestaurantPresenterImp restaurantPresenterImp) {
        this.restaurantView = restaurantView;
        this.restaurantPresenterImp = restaurantPresenterImp;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       restaurantView.showProgress();

    }

    @Override
    protected List<Result> doInBackground(Context... contexts)
    { context =  contexts[0];
        SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.prefName, Context.MODE_PRIVATE);
        String latitude = sharedPreferences.getString("LATITUDE", "");
        String longitude = sharedPreferences.getString("LONGITUDE", "");

        Log.e("TAG", latitude + longitude);
        ApiInterface apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
        Call<Place> placeCall=apiInterface.getNearbyPlaces("restaurant", latitude +","+ longitude,2000,1,10);
        Place place = null;
        try {
            place = placeCall.execute().body();
            Log.e("RESPONSE PAGE",""+place.getResults().size());

        } catch (IOException e) {
            e.printStackTrace();
        }
        assert place != null;

        return place.getResults();

    }

    @Override
    protected void onPostExecute(List<Result> results)
    {
        super.onPostExecute(results);
        restaurantView.hideProgress();
        restaurantView.setAdapter(results);

        //     restaurantPresenterImp.getList(results);


    }




}