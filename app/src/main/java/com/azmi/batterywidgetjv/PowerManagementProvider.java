package com.azmi.batterywidgetjv;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.azmi.batterywidgetjv.utils.Log;
import com.azmi.batterywidgetjv.widget.BatteryWidget;

public class PowerManagementProvider extends BroadcastReceiver {
    IPowerManagementResultListener listener;
    public static String EXTRA_INTENT_BATTERY_ACTION = "com.azmi.batterywidgetjv.UPDATE_WIDGET";
    public static String EXTRA_INTENT_BATTERY_LEVEL = "com.azmi.batterywidgetjv.level";
    public static String EXTRA_INTENT_BATTERY_CHARGING = "com.azmi.batterywidgetjv.charging";

    @Override
    public void onReceive(Context context, Intent intent) {

        // Are we charging / charged?
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        Float batteryPct = (level * 100 / (float)scale);

        if (listener !=null) {
            listener.PowerResult(isCharging, batteryPct);
        }

        Intent updateIntent = new Intent(context, BatteryWidget.class);
        updateIntent.setAction(EXTRA_INTENT_BATTERY_ACTION);
        updateIntent.putExtra(EXTRA_INTENT_BATTERY_LEVEL, batteryPct);
        updateIntent.putExtra(EXTRA_INTENT_BATTERY_CHARGING, isCharging);

        // Send the broadcast to the AppWidgetProvider
        context.sendBroadcast(updateIntent);
    }

    public void setListener(IPowerManagementResultListener listener) {
        this.listener = listener;
    }
}
