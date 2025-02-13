package com.example.edcsdksample.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.car.app.serialization.Bundleable;
import androidx.car.app.serialization.BundlerException;

public class BroadcastHelper {
    private static final String ACTION_NAVIGATION_MANAGER = "com.mgears.apiservice.NavigationManager";
    private static final String EXTRA_CMD = "cmd";
    private static final String EXTRA_TYPE = "type";

    public static void sendNavigationStarted(Context context) {
        Intent intent = getNavigationIntent("navigationStarted");
        intent.putExtra(EXTRA_TYPE, "edc");
        context.sendBroadcast(intent);
    }

    public static void sendNavigationEnded(Context context) {
        context.sendBroadcast(getNavigationIntent("navigationEnded"));
    }

    public static void sendEDCData(Context context, Bundle bundle) {
        Intent intent = getNavigationIntent("updateTrip");
        try {
            intent.putExtra("edc", Bundleable.create(bundle));
            context.sendBroadcast(intent);
        } catch (BundlerException ignored) {
        }
    }

    private static Intent getNavigationIntent(String cmd) {
        Intent intent = new Intent(ACTION_NAVIGATION_MANAGER);
        intent.putExtra(EXTRA_CMD, cmd);
        return intent;
    }
}
