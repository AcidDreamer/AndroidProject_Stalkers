package teamproject.a.just.stalkersbestfriend;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class postStalkerService extends Service {
    private LocationManager mLocationManager = null;
    //How often the gps will check , in ms
    private static final int LOCATION_INTERVAL = 1000;
    //How long will the uer have to move for the location change to take effect (this is irrelevant but fun none the less)
    private static final float LOCATION_DISTANCE = 5f;
    final String httpPath = "http://62.217.127.19:8000/location";                                   //our post request URL

    //Implement a LocaitonListener to snatch the location , he will be terminated after that
    private class LocationListener implements android.location.LocationListener {
        //Initialize  the listener
        Location mLastLocation;

        public LocationListener(String provider) {
            mLastLocation = new Location(provider);
        }

        //VERY IMPORTANT , every time the service starts , it get the values here , BUT we must destroy the service after we get the values
        //to make SURE that the onLocationChanged will be called next time
        @Override
        public void onLocationChanged(Location location) {
            Log.e("Toxic:-->", "onLocationChanged: " + location.getLongitude() + " amplitude--> " + location.getLatitude());
            mLastLocation.set(location);
            //we will sent the request
            sentRequest(location);
            stopSelf();
        }

        //In case the gps is disabled ,kill the service
        @Override
        public void onProviderDisabled(String provider) {
            stopSelf();
        }

        //Nothing to do when gps is turned on
        @Override
        public void onProviderEnabled(String provider) {
        }

        //Nothing to do when gps is turning on and off as well
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    //Create a new object of our newly created type
    LocationListener mLocationListener = new LocationListener(LocationManager.GPS_PROVIDER);


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        startLocationManager();
        try {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, mLocationListener);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationManager != null) {
            try {
                //noinspection MissingPermission
                mLocationManager.removeUpdates(mLocationListener);      //Remove the listener , needs special handling for android API 23
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //Initialize the location manager
    private void startLocationManager() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    private void sentRequest(Location location) {
        /*Make a json object*/
        final JSONObject myCoordinates = new JSONObject();
        /*Create a calendar item*/
        Calendar rightNow = Calendar.getInstance();
        /*create a formatted the date string*/
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd;HH:mm:ss");
        /*Fill the date*/
        String formattedDate = df.format(rightNow.getTime());
        try {
            /*add json objects*/
            myCoordinates.put("userid", "it21458");
            myCoordinates.put("longitude", location.getLongitude());
            myCoordinates.put("latitude", location.getLatitude());
            myCoordinates.put("dt", formattedDate);
            System.out.println(myCoordinates.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*Create a new thread to execute networking*/
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(httpPath);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    /*Set the method to POST and the content type  to json*/
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/json");
                    /*we want to gget the output*/
                    con.setDoOutput(true);
                    /*assign variable to the stream*/
                    DataOutputStream dStream = new DataOutputStream(con.getOutputStream());
                    /*Sent the request*/
                    dStream.writeBytes(myCoordinates.toString());
                    /*Flush the input we provided*/
                    dStream.flush();
                    /*Close the stream*/
                    dStream.close();
                    System.out.println(con.getResponseCode() + " <-----> " +con.getResponseMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*Stop the thread for better or worse*/
                Thread.currentThread().interrupt();
            }
        });
        thread.start();


    }
}
