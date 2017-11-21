package com.sa.restaurant.mvp.home.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sa.restaurant.mvp.db.DBUser;
import com.sa.restaurant.mvp.home.model.FavouriteRestaurant;
import com.sa.restaurant.mvp.home.model.Result;
import com.sa.restaurant.mvp.home.view.RestaurantView;
import com.sa.restaurant.mvp.webservice.PlaceResponseAsync;

import java.util.List;


public class RestaurantPresenterImp extends DBUser implements RestaurantPresenter,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener {

    private GoogleMap googleMap;
    private GoogleApiClient mGoogleApiClient;
    private Context context;
    private Activity activity;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private RestaurantView restaurantView;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private List<Result> resultList;

    @Override
    public void getList(List<Result> result)
    {
        this.resultList=result;

    }



    public RestaurantPresenterImp(Context context, RestaurantView restaurantView, Activity activity)
    {
        super(context);
        this.restaurantView=restaurantView;
        this.activity=activity;
        this.context=context;

    }



    @Override
    public boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(context);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(activity, result,
                        0).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void buildGoogleApiClient( )
    {

        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        getGoogleMapInstance();

    }

    @Override
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void displayMap(GoogleMap googleMap)
    {
        this.googleMap=googleMap;
//        restaurantView.getResultList(resultList);
        new PlaceResponseAsync(restaurantView,this).execute(context);

    }





    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onLocationChanged(Location location)
    {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(11));
       // build_retrofit_and_get_response("restaurant",latLng.latitude,latLng.longitude,googleMap);
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void getResponseList(List<Result> list) {
        this.resultList=list;
        Log.e("LIST",resultList.toString());
      //  restaurantView.getResultList(resultList);
    }

    @Override
    public List<Result> getResponseList() {
        return resultList;
    }

    @Override
    public GoogleMap getGoogleMapInstance() {
        return googleMap;
    }

    @Override
    public void addFavouriteRestaurant(View view, int position, FavouriteRestaurant favouriteRestaurant)
    {
        final  String restaurantName=favouriteRestaurant.getRestaurantName();
        final String restaurantAddress=favouriteRestaurant.getRestaurantAddress();
        final String restaurantPhotoreference=favouriteRestaurant.getRestaurantPhotoreference();
        if(getFavRestaurantAddress(restaurantName)==null ||!getFavRestaurantAddress(restaurantName).equals(restaurantAddress) )
        {
            boolean value= addFavRestaurant( restaurantName, restaurantAddress,restaurantPhotoreference);
            Toast.makeText(context,value+"",Toast.LENGTH_LONG).show();
        }
      else
        {
            Toast.makeText(context,"Already added",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public List getFavRestaurant()
    {
           return getFavRestaurantFromDb();
    }

    @Override
    public String getRestaurantAddress(String name) {
       return getFavRestaurantAddress(name);

    }
}
