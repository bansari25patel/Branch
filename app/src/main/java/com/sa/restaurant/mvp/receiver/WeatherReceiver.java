package
        com.sa.restaurant.mvp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sa.restaurant.mvp.service.WeatherService;


public class WeatherReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
       // Toast.makeText(context, "Alarm received! " , Toast.LENGTH_LONG).show();
        Intent background = new Intent(context, WeatherService.class);
        Log.e("WEATHER","receiver call after 15 minute");
        context.startService(background);

    }
}