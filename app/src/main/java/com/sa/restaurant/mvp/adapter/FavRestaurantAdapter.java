package com.sa.restaurant.mvp.adapter;

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
import com.sa.restaurant.mvp.home.model.Result;
import com.squareup.picasso.Picasso;

import java.util.List;


public class FavRestaurantAdapter extends RecyclerView.Adapter<FavRestaurantAdapter.MyViewHolder>
{
    private Context context;
    private List<FavouriteRestaurant> favouriteRestaurantList;

    public FavRestaurantAdapter(Context context, List<FavouriteRestaurant> favouriteRestaurantList) {
        this.context = context;
        this.favouriteRestaurantList = favouriteRestaurantList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {


        private ImageView imgRestaurant;
        private ImageView imgFavorite,imgShare,imgLike;
        private TextView txtRestaurantName,txtRestaurantAddress;
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

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        final FavouriteRestaurant favouriteRestaurant=favouriteRestaurantList.get(position);
        holder.txtRestaurantName.setText(favouriteRestaurant.getRestaurantName());
        holder.txtRestaurantAddress.setText(favouriteRestaurant.getRestaurantAddress());
        if(favouriteRestaurant.getFavRestaurantStatus().equals("YES"))
        {
            holder.imgFavorite.setImageResource(R.drawable.ic_faved);
        }
        else {
            holder.imgFavorite.setImageResource(R.drawable.ic_fav);
        }
        for(int i=0;i<favouriteRestaurantList.size();i++)
        {
            String url = "https://maps.googleapis.com/maps/api/place/photo?";
            String key = "key=AIzaSyAFbPQneTsCKP8LhKmpJ89ND4jJm_5OESo";
            String maxWidth="maxwidth=" + 500;
            String photoReference = "photoreference="+favouriteRestaurant.getRestaurantPhotoreference();;
            url = url + maxWidth+"&"+photoReference+"&"+key;
            Log.e("url",url);
            Picasso.with(context).load(url)
                    .placeholder(R.drawable.ic_image_place_holder)
                    .into(holder.imgRestaurant);
        }

    }

    @Override
    public int getItemCount() {
        return favouriteRestaurantList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_cardview_row,parent,false);
        return new FavRestaurantAdapter.MyViewHolder(view);
    }
}
