package com.sa.restaurant.mvp.home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sa.restaurant.R;
import com.sa.restaurant.mvp.adapter.FavRestaurantAdapter;
import com.sa.restaurant.mvp.adapter.RestaurantAdapter;
import com.sa.restaurant.mvp.home.model.Result;
import com.sa.restaurant.mvp.home.presenter.RestaurantPresenterImp;
import com.sa.restaurant.mvp.home.view.RestaurantView;

import java.util.List;


public class FavRestaurantfragment extends Fragment implements RestaurantView
{
    private ProgressBar progressBar;

    public static FavRestaurantfragment newInstance() {
        return new FavRestaurantfragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RestaurantPresenterImp restaurantPresenterImp = new RestaurantPresenterImp(getActivity(), this, getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.fragment_restaurant_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(getActivity()!=null)
        {
            recyclerView.setAdapter(new FavRestaurantAdapter(getActivity(), restaurantPresenterImp.getFavRestaurant()));
        }

    }


    @Override
    public void setAdapter(List<Result> resultList) {

    }

    @Override
    public void showProgress()
    {
        progressBar=getActivity().findViewById(R.id.fragment_restaurant_progressbar);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress()
    {
        progressBar.setVisibility(View.GONE);

    }
}
