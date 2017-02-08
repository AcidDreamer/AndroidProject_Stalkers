package teamproject.a.just.stalkersbestfriend;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Acid on 2/7/2017.
 */

public class getStalkersService extends IntentService {
    private  final String AUTHORITY = "content://teamproject.a.just.stalkersbestfriend/stalkers";
    private Uri uri = Uri.parse(AUTHORITY);
    final String httpPath = "http://62.217.127.19:8000/location";                                   //our get request URL
    DatabaseHelper myDBH;

    public getStalkersService() {
        super("MyWorkerThread");                                                                    //Naming our baby
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {                              //When we start the service/thread
        Toast.makeText(this, "Getting other users location...", Toast.LENGTH_LONG).show();          //Inform the user
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {                                                                       //When the user stops the service
        super.onDestroy();
        Toast.makeText(this, "Stalker ended...", Toast.LENGTH_LONG).show();                       //He gets informed
    }

    protected void onHandleIntent(Intent intent) {
        try {
            URL httpClient = new URL(httpPath);                                                 //Create a new url with the path we want to get
            HttpURLConnection connection = (HttpURLConnection) httpClient.openConnection();     //We open the connection
            InputStream is = connection.getInputStream();                                       //We get the string
            BufferedReader br = new BufferedReader(new InputStreamReader(is));                  //Initializing a buffered reader with InputStream as a parameter
            StringBuilder stringBuilder = new StringBuilder();                                  //Initializing a StringBuilder
            String line = null;                                                                 //Get an empty line
            while ((line = br.readLine()) != null) {                                            //While the line hasn't reach the end of the buffered reader readLine ,do staff,and move to the next
                stringBuilder.append(line + "n");                                    //Append the line to our string builder
            }
            is.close();
            JSONArray stalkersJson = new JSONArray(stringBuilder.toString());                    //convert the string to json
            for(int i = 0 ; i<stalkersJson.length();i++){                                        //for each entity in the json we received
                ContentValues cV = new ContentValues();                                          //create content value variables
                /*Enrich the content value with the values gotten from the json*/
                cV.put("_id",stalkersJson.getJSONObject(i).getString("id"));
                cV.put("_userid",stalkersJson.getJSONObject(i).getString("userid"));
                cV.put("_longitude",stalkersJson.getJSONObject(i).getString("longitude"));
                cV.put("_latitude",stalkersJson.getJSONObject(i).getString("latitude"));
                cV.put("_dt",stalkersJson.getJSONObject(i).getString("dt"));
                this.getContentResolver().insert(uri,cV);                                        //communicate to the content provider at the uri , and ask him to insert the values
                /*Used for debugging*/
                if(i==stalkersJson.length()-1){
                    System.out.println("Reached the end ,number of objects passed to the database : " + i);
                }
            }

        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    /* PREVIOUS IMPLIMENTATION WITH SQLITE , WE DO NOT DESTROY THE PAST ,WE LEARN FROM IT
    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this) {
            for (; ; ) {
                try {
                    URL httpClient = new URL(httpPath);                                                 //Create a new url with the path we want to get
                    HttpURLConnection connection = (HttpURLConnection) httpClient.openConnection();     //We open the connection
                    InputStream is = connection.getInputStream();                                       //We get the string
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));                  //Initializing a buffered reader with InputStream as a parameter
                    StringBuilder stringBuilder = new StringBuilder();                                  //Initializing a StringBuilder
                    String line = null;                                                                 //Get an empty line
                    while ((line = br.readLine()) != null) {                                            //While the line hasn't reach the end of the buffered reader readLine ,do staff,and move to the next
                        stringBuilder.append(line.toString() + "n");                                    //Append the line to our string builder
                    }
                    is.close();
                    JSONArray mainObject = new JSONArray(stringBuilder.toString());                     //convert the string to json
                    System.out.println("Entries : " + mainObject.length() + " Last entry : " + mainObject.get(mainObject.length() - 1));
                    myDBH = new DatabaseHelper(this);
                    myDBH.insertStalker(mainObject);
                    wait(1 * 2 * 1000);                                                               //minutes * seconds * milliseconds
                    System.out.println("started12345");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    */
