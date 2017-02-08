package teamproject.a.just.stalkersbestfriend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

public class GpsLocationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //if the intent that came matches with ...
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
            boolean isGpsEnabled;
            //Create a new location manage
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            //Check if the gps is enabled
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            //if it is
            if (isGpsEnabled) {
                //send an intent
                Intent pushIntent = new Intent(context, postReceiver.class);
                context.sendBroadcast(pushIntent);
            }
        }
    }
}
