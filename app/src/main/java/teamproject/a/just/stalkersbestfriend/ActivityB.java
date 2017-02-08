package teamproject.a.just.stalkersbestfriend;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class ActivityB extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        Button getStalker = (Button) findViewById(R.id.getStalker);
        Button getGmps = (Button) findViewById(R.id.getOnGmaps);
        TextView stalkersInfo = (TextView) findViewById(R.id.stalkersInfo);
        stalkersInfo.setVisibility(View.INVISIBLE);
        getGmps.setVisibility(View.INVISIBLE);
    }

    public void getSpecificStalker(View V) {
        Button getGmps = (Button) findViewById(R.id.getOnGmaps);
        getGmps.setVisibility(View.INVISIBLE);
        /*Set Uri*/
        String AUTHORITY = "content://teamproject.a.just.stalkersbestfriend/stalkers/";
        Uri uri = Uri.parse(AUTHORITY);
        /*Set projection*/
        final String[] projection = {DatabaseHelper.col0, DatabaseHelper.col1, DatabaseHelper.col2, DatabaseHelper.col3, DatabaseHelper.col4};
        /*Set selection*/
        Spinner dropdown = (Spinner) findViewById(R.id.selectDropDown);
        String searchBy = dropdown.getSelectedItem().toString();
        /*Set arguements*/
        String[] args = new String[1];
        EditText searchArguement = (EditText) findViewById(R.id.searchBy);
        args[0] = searchArguement.getText().toString();
        /*set the view*/
        TextView stalkersInfo = (TextView) findViewById(R.id.stalkersInfo);
        /*execute the query*/
        Cursor c = this.getContentResolver().query(uri, projection, searchBy + " = ? ", args, null); //uri,columns to return of each row,selection critiria, selection critiria(arguements) , sort order
        /*end if no results returned*/
        if (c.getCount() == 0) {
            stalkersInfo.setText("No stalker found with that ID or userid");
            stalkersInfo.setVisibility(View.VISIBLE);
            return;
        }
        /*Start creating the textview according to the results*/
        stalkersInfo.setText("Stalkers Info \n");
        stalkersInfo.setMovementMethod(new ScrollingMovementMethod());
        try {
            //get the coordinates of the last output, used later
            final String[] coordinates = new String[2];
            /*navigate to first element*/
            c.moveToFirst();
            /*Move and append the corresponding values*/
            do {
                stalkersInfo.append("-----------------------------\n");
                stalkersInfo.append(DatabaseHelper.col0 + " : " + c.getString(c.getColumnIndex(DatabaseHelper.col0)) + "\n");
                stalkersInfo.append(DatabaseHelper.col1 + " : " + c.getString(c.getColumnIndex(DatabaseHelper.col1)) + "\n");
                stalkersInfo.append(DatabaseHelper.col2 + " : " + c.getString(c.getColumnIndex(DatabaseHelper.col2)) + "\n");
                stalkersInfo.append(DatabaseHelper.col3 + " : " + c.getString(c.getColumnIndex(DatabaseHelper.col3)) + "\n");
                stalkersInfo.append(DatabaseHelper.col4 + " : " + c.getString(c.getColumnIndex(DatabaseHelper.col4)) + "\n");
                stalkersInfo.append("-----------------------------\n");
                coordinates[0] = c.getString(c.getColumnIndex(DatabaseHelper.col3)); //get the latitude
                coordinates[1] = c.getString(c.getColumnIndex(DatabaseHelper.col2)); //get the longitude
            } while (c.moveToNext());
            /*set it visible*/
            stalkersInfo.setVisibility(View.VISIBLE);
            /*if the users searches by ID ,allow him to open the location on gmaps*/
            if (searchBy.equalsIgnoreCase("_ID") && !coordinates[0].isEmpty() && !coordinates[1].isEmpty()) {
                /*Make the button visible*/
                getGmps.setVisibility(View.VISIBLE);
                /*Give it an onclick listener*/
                getGmps.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        /*prepare the uri*/
                        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", Float.parseFloat(coordinates[0]),Float.parseFloat(coordinates[1]), "Stalker found!");
                        /*create an intent for gmaps*/
                        Intent GmapsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        /*set the class*/
                        GmapsIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        //Try to open it with google maps
                        try{
                            System.out.println("Sending intent to gmaps");
                            startActivity(GmapsIntent);
                            /*If we couldn't we open it with the browser*/
                        } catch(ActivityNotFoundException ex){
                            System.out.println("No gmaps installed,running it on browser");
                            Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            startActivity(unrestrictedIntent);
                        }
                    }
                });
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        /*close the cursor*/
        c.close();
    }
}
