package
        com.sa.restaurant.mvp.weather;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sa.restaurant.MainActivity;
import com.sa.restaurant.R;





public class WeatherFragment extends Fragment
{
    private TextView txtCity,txtWeatherCondition,txtTemprature
            ,txtHumidity,txtPressure,txtWindSpeed;
     SharedPreferences sharedPreferences;
    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        return inflater.inflate(R.layout.fragment_weather,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        initializeView(view);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    private void initializeView(View view)
    {

        txtCity=view.findViewById(R.id.fragment_weather_city);
        txtWeatherCondition=view.findViewById(R.id.fragment_weather_weather_condition);
        txtTemprature=view.findViewById(R.id.fragment_weather_temp);
        txtHumidity=view.findViewById(R.id.fragment_weather_humidity);
        txtPressure=view.findViewById(R.id.fragment_weather_pressure);
        txtWindSpeed=view.findViewById(R.id.fragment_weather_windspeed);
        sharedPreferences = getActivity().getSharedPreferences(MainActivity.prefName, Context.MODE_PRIVATE);
        setWeatherData();


    }

    private void setWeatherData()
    {
        txtCity.setText(sharedPreferences.getString("NAME",""));
        txtTemprature.setText(sharedPreferences.getString("TEMP","")+" "+getString(R.string.celsius));
        txtHumidity.setText(getString(R.string.humidity)+ "  "+sharedPreferences.getString("HUMIDITY","")+"%");
        txtPressure.setText(getString(R.string.pressure)+"  "+sharedPreferences.getString("PRESSURE","")+"mb");
        txtWindSpeed.setText(getString(R.string.wind_speed)+"  "+sharedPreferences.getString("WINDSPEED","")+"m/s");
        txtWeatherCondition.setText(sharedPreferences.getString("CONDITION",""));
    }


}