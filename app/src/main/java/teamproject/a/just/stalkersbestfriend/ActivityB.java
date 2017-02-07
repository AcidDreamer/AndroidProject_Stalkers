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
        String AUTHORITY ="content://teamproject.a.just.stalkersbestfriend/stalkers";
        Uri uri = Uri.parse(AUTHORITY);
        Cursor c = null;
        c = this.getContentResolver().query(uri,null,null,null,null,null);
        TextView atextview = (TextView) findViewById(R.id.tV);
        if(c==null)return;

        try {
            if (!c.moveToFirst()) {
            }
            do {
                atextview.append(c.getString(1));
            } while (c.moveToNext());
            System.out.println(c.getCount() + " abcdefg");
        }catch (NullPointerException e){
            e.printStackTrace();

        }
    }
}
