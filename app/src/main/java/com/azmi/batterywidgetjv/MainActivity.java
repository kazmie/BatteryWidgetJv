package com.azmi.batterywidgetjv;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.azmi.batterywidgetjv.utils.Log;

public class MainActivity extends AppCompatActivity implements IPowerManagementResultListener{

    private TextView tvLevel;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tvLevel = findViewById(R.id.tvLevel);
        tvStatus = findViewById(R.id.tvStatus);
        PowerManagementProvider broadcastReceiver = new PowerManagementProvider(this);
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        this.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void PowerResult(boolean isCharging, Float level) {
        Log.d("PowerResult callback");
        String text = isCharging ? "Charging" : "-";
        tvStatus.setText(text);
        tvLevel.setText(level.intValue() + "%");
    }
}