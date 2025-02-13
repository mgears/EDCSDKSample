package com.example.edcsdksample;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "EDC.MainActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (findViewById(R.id.btnStart)).setOnClickListener(this);
        (findViewById(R.id.btnStop)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnStart) {
            startMapService();
        }
        else if (id == R.id.btnStop) {
            stopMapService();
        }
    }

    @SuppressLint("NewApi")
    private void startMapService() {
        Log.d(TAG, "startMapService");
        startForegroundService(getMainServiceIntent());
    }

    private void stopMapService() {
        Log.d(TAG, "stopMapService");
        stopService(getMainServiceIntent());
    }

    private Intent getMainServiceIntent() {
        return new Intent(this, MainService.class);
    }
}
