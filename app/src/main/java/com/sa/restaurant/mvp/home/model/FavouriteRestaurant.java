package com.sa.restaurant.mvp.home.model;



public class FavouriteRestaurant
{
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantPhotoreference;

    public String getFavRestaurantStatus() {
        return favRestaurantStatus;
    }

    public void setFavRestaurantStatus(String favRestaurantStatus) {
        this.favRestaurantStatus = favRestaurantStatus;
    }

    private String favRestaurantStatus;


    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getRestaurantPhotoreference() {
        return restaurantPhotoreference;
    }

    public void setRestaurantPhotoreference(String restaurantPhotoreference) {
        this.restaurantPhotoreference = restaurantPhotoreference;
    }
}
