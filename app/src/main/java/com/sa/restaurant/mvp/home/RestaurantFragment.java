package com.sa.restaurant.mvp.home;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sa.restaurant.MainActivity;
import com.sa.restaurant.R;
import com.sa.restaurant.mvp.adapter.RestaurantAdapter;
import com.sa.restaurant.mvp.home.model.FavouriteRestaurant;
import com.sa.restaurant.mvp.home.model.Place;
import com.sa.restaurant.mvp.home.model.Result;
import com.sa.restaurant.mvp.home.presenter.RestaurantPresenterImp;
import com.sa.restaurant.mvp.home.view.RestaurantView;
import com.sa.restaurant.mvp.service.LocationService;
import com.sa.restaurant.mvp.webservice.ApiClient;
import com.sa.restaurant.mvp.webservice.ApiInterface;
import com.sa.restaurant.mvp.webservice.PlaceResponseAsync;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;


public class RestaurantFragment extends Fragment implements RestaurantView,RestaurantAdapter.OnItemClickListener{

    RecyclerView restaurantListview;
    private GoogleApiClient googleApiClient;
    private RestaurantPresenterImp restaurantPresenterImp;
    private GoogleMap googleMap;
  private String latitude,longitude;
    private View displayView;
    private LocalBroadcastManager manager;
    public static RestaurantFragment newInstance() {
        return new RestaurantFragment();
    }
    private static final int PAGE_START = 0;
    private ProgressBar progressBar;
    private View rootview;


    @Override
    public void setAdapter(List<Result> resultList)
    {
        restaurantListview.setLayoutManager(new LinearLayoutManager(getActivity()));
        restaurantListview.setAdapter(new RestaurantAdapter(getActivity(),getActivity(),resultList,this));


    }

    @Override
    public void showProgress()
    {
        progressBar=getActivity().findViewById(R.id.fragment_restaurant_progressbar);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(View view, int position, FavouriteRestaurant favouriteRestaurant)
    {

        restaurantPresenterImp.addFavouriteRestaurant(view,position,favouriteRestaurant);
    }

    interface ResultList
    {
        void getResultList(List<Result> resultList);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {

        if(rootview==null)
        {
            rootview= inflater.inflate(R.layout.fragment_restaurant, container, false);

        }

        return rootview;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        restaurantPresenterImp = new RestaurantPresenterImp(getActivity(),this, getActivity());
        restaurantListview = view.findViewById(R.id.fragment_restaurant_recycler_view);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.prefName, Context.MODE_PRIVATE);
         latitude = sharedPreferences.getString("LATITUDE", "");
         longitude = sharedPreferences.getString("LONGITUDE", "");


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(info.isConnected())
        {
            if(longitude!=null && latitude!=null)
            {
                new PlaceResponseAsync(this,restaurantPresenterImp).execute(getActivity());
            }
            else {
                showProgress();
            }


        }

    }




}
