package teamproject.a.just.stalkersbestfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {
    double longitude = 0;
    double latitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Button hiwary = (Button) findViewById(R.id.getStalkers);
        Button changegActivity = (Button) findViewById(R.id.swapActivity);

    }

    public void getStalkers(View v) {
        Intent i = new Intent(this,getStalkersOnDemand_Service.class);
        startService(i);
    }
    public void changeActivity(View v) {
        Intent i = new Intent(this,ActivityB.class);
        startActivity(i);
    }
}
