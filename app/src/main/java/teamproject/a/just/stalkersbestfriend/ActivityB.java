package teamproject.a.just.stalkersbestfriend;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        String AUTHORITY ="content://teamproject.a.just.stalkersbestfriend/stalkers/";
        Uri uri = Uri.parse(AUTHORITY);
        String[] args = new String[1];
        args[0] = "2";
        Cursor c = this.getContentResolver().query(uri,null,null,args,null); //uri,columns to return of each row,selection critiria, selection critiria(arguements) , sort order
        TextView atextview = (TextView) findViewById(R.id.tV);
        if(c==null)return;
        System.out.println(c.getCount()+ " THIS IS THE COUNT");
        try {
            c.moveToFirst();
            do {
                atextview.append(c.getString(0));
            } while (c.moveToNext());
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        c.close();
    }
}
