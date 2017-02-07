package teamproject.a.just.stalkersbestfriend;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Acid on 2/7/2017.
 */

public class getStalkersService extends Service{
    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(print);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!this.isRunning){
            this.isRunning = false;
            this.backgroundThread.start();
        }
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        this.isRunning=false;
    }

    private Runnable print = new Runnable() {
        @Override
        public void run() {
            System.out.println("IM PRINTING.");
            stopSelf();
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }
}
