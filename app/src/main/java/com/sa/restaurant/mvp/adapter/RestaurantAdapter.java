package com.sa.restaurant.mvp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.sa.restaurant.R;
import com.sa.restaurant.mvp.home.model.FavouriteRestaurant;
import com.sa.restaurant.mvp.home.model.Place;
import com.sa.restaurant.mvp.home.model.Restaurant;
import com.sa.restaurant.mvp.home.model.Result;
import com.sa.restaurant.mvp.home.presenter.RestaurantPresenterImp;
import com.sa.restaurant.mvp.home.view.RestaurantView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> implements RestaurantView
{
    private List<Result> restaurantList;
    private Context context;
    private boolean[] isClick;
    private Activity activity;
    private List favRestaurantList;
    private  OnItemClickListener onItemClickListener;
    private RestaurantPresenterImp restaurantPresenterImp;
    private boolean isLoadingAdded = false;

    @Override
    public void setAdapter(List<Result> resultList) {

    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, FavouriteRestaurant resultList);
    }

    public RestaurantAdapter(Activity activity, Context context, List<Result> restaurantList, OnItemClickListener listener) {
        this.restaurantList = restaurantList;
        this.context = context;
        this.activity=activity;
        isClick=new boolean[restaurantList.size()];
        restaurantPresenterImp=new RestaurantPresenterImp(this.context,this,activity);
        this.onItemClickListener=listener;

    }
//

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_cardview_row,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        final Result result=restaurantList.get(position);
      holder.txtRestaurantName.setText(result.getName());
        holder.txtRestaurantAddress.setText(result.getVicinity());
        final List   photos=result.getPhotos();

        if(result.getVicinity().equals(restaurantPresenterImp.getRestaurantAddress(result.getName())))
        {
            holder.imgFavorite.setImageResource(R.drawable.ic_faved);

        }
        else
        {
            holder.imgFavorite.setImageResource(R.drawable.ic_fav);
        }
        for(int i=0;i<photos.size();i++)
        {
            String url = "https://maps.googleapis.com/maps/api/place/photo?";
            String key = "key=AIzaSyAFbPQneTsCKP8LhKmpJ89ND4jJm_5OESo";
            String maxWidth="maxwidth=" + 500;
            String photoReference = "photoreference="+result.getPhotos().get(i).getPhotoReference();
            url = url + maxWidth+"&"+photoReference+"&"+key;
            Log.e("url",url);
            Picasso.with(context).load(url)
                    .placeholder(R.drawable.ic_image_place_holder)
                    .into(holder.imgRestaurant);
        }
        if (isClick[position])
            holder.imgLike.setImageResource(R.drawable.ic_liked);
        else
            holder.imgLike.setImageResource(R.drawable.ic_like);
            holder.imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                isClick[position] = !isClick[position];
               notifyDataSetChanged();
                if (isClick[position])
                    holder.imgLike.setImageResource(R.drawable.ic_liked);
                else
                    holder.imgLike.setImageResource(R.drawable.ic_like);
//
                result.setChecked(!result.isChecked());

            }
        });

        holder.imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                holder.imgFavorite.setImageResource(R.drawable.ic_faved);
                final FavouriteRestaurant favouriteRestaurant=new FavouriteRestaurant();
                favouriteRestaurant.setRestaurantName(restaurantList.get(holder.getAdapterPosition()).getName());
                favouriteRestaurant.setRestaurantAddress(restaurantList.get(holder.getAdapterPosition()).getVicinity());
                for (int i=0;i<photos.size();i++)
                {
                    favouriteRestaurant.setRestaurantPhotoreference(restaurantList.get(holder.getAdapterPosition()).getPhotos()
                            .get(i).getPhotoReference());
                }



              onItemClickListener.onItemClick(view,holder.getAdapterPosition(),favouriteRestaurant);

            }
        });



    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imgRestaurant;
        private ImageView imgFavorite,imgShare,imgLike;
        private   TextView txtRestaurantName,txtRestaurantAddress;
        public MyViewHolder(View itemView) {
            super(itemView);
            imgRestaurant = itemView.findViewById(R.id.restaurant_cardview_row_img_restaurant);
            imgFavorite=itemView.findViewById(R.id.restaurant_cardview_row_img_fav);
            imgShare=itemView.findViewById(R.id.restaurant_cardview_row_img_share);
            imgLike=itemView.findViewById(R.id.restaurant_cardview_row_img_like);
            txtRestaurantName=itemView.findViewById(R.id.restaurant_cardview_row_txt_restaurant_name);
            txtRestaurantAddress=itemView.findViewById(R.id.restaurant_cardview_row_txt_restaurant_address);

        }
    }




}
