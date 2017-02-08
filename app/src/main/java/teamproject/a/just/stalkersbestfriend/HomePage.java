package teamproject.a.just.stalkersbestfriend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends Activity {
    double longitude = 0;
    double latitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Button getStalkersNow = (Button) findViewById(R.id.getStalkers);
        Button searchForStalkers = (Button) findViewById(R.id.swapActivity);
        Button startStalkerService = (Button) findViewById(R.id.startGetService);
    }
    /*sets an intent to the service ,starting it immediately*/
    public void getStalkers(View v) {
        Intent i = new Intent(this,getStalkersService.class);
        startService(i);
    }
    /*changes activity to the test activity*/
    public void changeActivity(View v) {
        Intent i = new Intent(this,ActivityB.class);
        startActivity(i);
    }
    /*First starts the service immediately and the starts it after 10 mins*/
    public void startGetService(View v){
        Intent i = new Intent(this,getReceiver.class);
        sendBroadcast(i);
        i = new Intent(this,OnBootStartGetReceiver.class);
        sendBroadcast(i);
    }
}
