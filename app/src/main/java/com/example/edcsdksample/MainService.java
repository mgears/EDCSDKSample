package com.example.edcsdksample;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MainService extends Service {
    private static final String TAG = "EDC.MainService";

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "test_channel_01";
    private static final String CHANNEL_NAME = "test_channel_01";

    private boolean mRunning = false;
    private EdcManager mEdcManager;

    public MainService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        mEdcManager = new EdcManager(this);
        mEdcManager.init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

        if (mEdcManager != null) {
            mEdcManager.deinit();
            mEdcManager = null;
        }
    }

    @SuppressLint("ForegroundServiceType")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mRunning) {
            return START_STICKY;
        }

        createNotificationChannel();
        startForeground(NOTIFICATION_ID, getNotification("MyService is running"));

        mRunning = true;
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = getBaseContext().getSystemService(NotificationManager.class);

            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW
            );
            manager.createNotificationChannel(serviceChannel);
        }
    }

    public Notification getNotification(String message) {
        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setOngoing(true)
                .setContentTitle("EdcService")
                .setContentText(message)
                .build();
    }
}