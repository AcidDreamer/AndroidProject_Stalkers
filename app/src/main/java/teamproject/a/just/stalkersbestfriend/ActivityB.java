package teamproject.a.just.stalkersbestfriend;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ActivityB extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        Button getStalker = (Button) findViewById(R.id.getStalker);
        Spinner dropdown =  (Spinner) findViewById(R.id.selectDropDown);

        TextView stalkersInfo = (TextView) findViewById(R.id.stalkersInfo);
        stalkersInfo.setVisibility(View.INVISIBLE);
        EditText searchArguement = (EditText) findViewById(R.id.searchBy);
    }
    public void getSpecificStalker(View V){
        /*Set Uri*/
        String AUTHORITY ="content://teamproject.a.just.stalkersbestfriend/stalkers/";
        Uri uri = Uri.parse(AUTHORITY);
        /*Set projection*/
        final String[] projection = {DatabaseHelper.col0,DatabaseHelper.col1,DatabaseHelper.col2,DatabaseHelper.col3,DatabaseHelper.col4};
        /*Set selection*/
        Spinner dropdown =  (Spinner) findViewById(R.id.selectDropDown);
        String searchBy = dropdown.getSelectedItem().toString();
        /*Set arguements*/
        String[] args = new String[1];
        EditText searchArguement = (EditText) findViewById(R.id.searchBy);
        args[0] = searchArguement.getText().toString();
        /*set the view*/
        TextView stalkersInfo = (TextView) findViewById(R.id.stalkersInfo);
        /*execute the query*/
        Cursor c = this.getContentResolver().query(uri,projection,searchBy + " = ? ",args,null); //uri,columns to return of each row,selection critiria, selection critiria(arguements) , sort order
        /*end if no results returned*/
        if(c.getCount() == 0){
            stalkersInfo.setText("No stalker found with that ID or userid");
            stalkersInfo.setVisibility(View.VISIBLE);
            return;
        }
        /*Start creating the textview according to the results*/
        stalkersInfo.setText("Stalkers Info \n");
        stalkersInfo.setMovementMethod(new ScrollingMovementMethod());
        try {
            /*navigate to first element*/
            c.moveToFirst();
            /*Move and append the corresponding values*/
            do {
                stalkersInfo.append("-----------------------------\n");
                stalkersInfo.append(DatabaseHelper.col0 + " : "+ c.getString(c.getColumnIndex(DatabaseHelper.col0)) + "\n");
                stalkersInfo.append(DatabaseHelper.col1 + " : "+ c.getString(c.getColumnIndex(DatabaseHelper.col1)) + "\n");
                stalkersInfo.append(DatabaseHelper.col2 + " : "+ c.getString(c.getColumnIndex(DatabaseHelper.col2)) + "\n");
                stalkersInfo.append(DatabaseHelper.col3 + " : "+ c.getString(c.getColumnIndex(DatabaseHelper.col3)) + "\n");
                stalkersInfo.append(DatabaseHelper.col4 + " : "+ c.getString(c.getColumnIndex(DatabaseHelper.col4)) + "\n");
                stalkersInfo.append("-----------------------------\n");
            } while (c.moveToNext());
            /*set it visible*/
            stalkersInfo.setVisibility(View.VISIBLE);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        /*close the cursor*/
        c.close();
    }
}
