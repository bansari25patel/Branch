package com.sa.restaurant.mvp.home;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sa.restaurant.R;
import com.sa.restaurant.mvp.home.model.Result;
import com.sa.restaurant.mvp.home.presenter.RestaurantPresenterImp;
import com.sa.restaurant.mvp.home.view.RestaurantView;

import java.util.List;


public class MapFragment extends Fragment implements RestaurantView,OnMapReadyCallback,RestaurantFragment.ResultList {
    private MapView mapView;
    private GoogleMap googleMap;
    private RestaurantPresenterImp restaurantPresenterImp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeComponent(view, savedInstanceState);
        restaurantPresenterImp = new RestaurantPresenterImp(getActivity(),this,getActivity());

    }

    private void initializeComponent(View view, Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.fragment_map_mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }





    @Override
    public void onMapReady(GoogleMap googleMap)
    {

        if(restaurantPresenterImp.CheckGooglePlayServices())
        {
            this.googleMap=googleMap;
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            restaurantPresenterImp .buildGoogleApiClient( );
            restaurantPresenterImp.displayMap(this.googleMap);

        }

    }


    @Override
    public void getResultList(List<Result> resultList)
    {

    }

    @Override
    public void setAdapter(List<Result> resultList)
    {
        for (int i=0;i<resultList.size();i++)
        {
            Log.e("SIZE",""+resultList.size());

          final   MarkerOptions markerOptions = new MarkerOptions();
            double lat=resultList.get(i).getGeometry().getLocation().getLat();
            double lng=resultList.get(i).getGeometry().getLocation().getLng();
            String address=resultList.get(i).getVicinity();
            String name=resultList.get(i).getName();
             final  LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);


            if(address.equals(restaurantPresenterImp.getRestaurantAddress(resultList.get(i).getName())))
            {
                markerOptions.title(name+","+lat+","+lng);

            }else
            {
                markerOptions.title(lat+","+lng);
            }

            googleMap.addMarker(markerOptions);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(11));


        }


    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }


}
