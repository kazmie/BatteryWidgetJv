package com.azmi.batterywidgetjv.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.RemoteViews;

import com.azmi.batterywidgetjv.IPowerManagementResultListener;
import com.azmi.batterywidgetjv.PowerManagementProvider;
import com.azmi.batterywidgetjv.R;
import com.azmi.batterywidgetjv.utils.Log;

/**
 * Implementation of App Widget functionality.
 */
public class BatteryWidget extends AppWidgetProvider {

    private Context context;
    PowerManagementProvider broadcastReceiver = new PowerManagementProvider(null);
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Float level, boolean isCharging) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.battery_widget);
        if (level != null) {
            Float lvlFloat = level;
            views.setTextViewText(R.id.appwidget_text, lvlFloat.intValue() + "%");
        }
        else
            views.setTextViewText(R.id.appwidget_text,   "-");

        if(isCharging)
            views.setViewVisibility(R.id.imgCharging, View.VISIBLE);
        else views.setViewVisibility(R.id.imgCharging, View.INVISIBLE);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, null, false);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        // Check if the intent action matches your custom action
        if (intent.getAction().equals(PowerManagementProvider.EXTRA_INTENT_BATTERY_ACTION)) {
            // Get data from the intent
            float level = intent.getFloatExtra(PowerManagementProvider.EXTRA_INTENT_BATTERY_LEVEL, 0f);
            boolean isCharging = intent.getBooleanExtra(PowerManagementProvider.EXTRA_INTENT_BATTERY_CHARGING, false);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisWidget = new ComponentName(context, BatteryWidget.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId, level, isCharging);
            }
        }
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {

    }

}