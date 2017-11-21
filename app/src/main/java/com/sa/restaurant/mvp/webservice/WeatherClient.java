package
        com.sa.restaurant.mvp.webservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherClient
{

    public  static Retrofit getInstance()
    {
        return new Retrofit.Builder()
                     .addConverterFactory(GsonConverterFactory.create())
                     .baseUrl(WeatherAPI.BASE_URL)
                     .build();

    }
}