package com.example.edcsdksample;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.car.app.model.DateTimeWithZone;
import androidx.car.app.navigation.model.Destination;
import androidx.car.app.navigation.model.Lane;
import androidx.car.app.navigation.model.LaneDirection;
import androidx.car.app.navigation.model.Maneuver;
import androidx.car.app.navigation.model.Step;
import androidx.car.app.navigation.model.TravelEstimate;
import androidx.car.app.navigation.model.Trip;

import com.example.edcsdksample.util.BroadcastHelper;
import com.mgears.navigation.tmap.EDCData;
import com.mgears.navigation.tmap.TBTInfo;
import com.mgears.navigation.tmap.TmapUtil;
import com.tmapmobility.tmap.tmapsdk.edc.EDCConst;
import com.tmapmobility.tmap.tmapsdk.edc.TmapEDCSDK;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import com.mgears.mglib.sdk.NavigationManager;

/**
 * https://tmapapi.tmapmobility.com/main.html#androidEDCSDK/guide/androidGuide.sample1
 * https://tmapapi.tmapmobility.com/main.html#androidEDCSDK/sample/androidSample.sdk_download
 */
public class EdcManager {
    private static final String TAG = "EDC.EdcManager";

    private final Context mContext;
    private final NavigationManager mNavigationManager = new NavigationManager();

    private final String CLIENT_ID = "";

    //발급 받은 API KEY
    private final String API_KEY = BuildConfig.API_KEY; //발급 받은 API KEY
    private final String USER_KEY = "";
    private final String DEVICE_KEY = "";

    private TmapEDCSDK.Companion edc;
    private TmapEDCSDK.Companion.EDCAuthData edcAuthData;

    private boolean isRegister = false;
    private boolean isTargetTest = false;

    public EdcManager(Context context) {
        mContext = context;
    }

    public void init() {
        Log.d(TAG, "init");

        edcAuthData = new TmapEDCSDK.Companion.EDCAuthData(CLIENT_ID, API_KEY, mContext.getPackageName(), USER_KEY, DEVICE_KEY);

        edc = TmapEDCSDK.Companion;
        edc.initialize(mContext, edcAuthData, workListener, isTargetTest);

        mNavigationManager.init(mContext, new NavigationManager.Listener() {
            @Override
            public void onStopNavigation() {

            }
        });
    }

    public void deinit() {
        Log.d(TAG, "deinit");

        if (edc!=null) {
            edc.finish();
        }

        mNavigationManager.deinit();
    }

    private final TmapEDCSDK.Companion.EDCWorkListener workListener = new TmapEDCSDK.Companion.EDCWorkListener() {
        @Override
        public void onRouteStarted(@NonNull Bundle bundle) {
            int totalDistanceInMeter = bundle.getInt("totalDistanceInMeter");
            int totalTimeInSec = bundle.getInt("totalTimeInSec");
            int tollFree = bundle.getInt("tollFee");

            String log = "거리 " + totalDistanceInMeter + "m / 시간 " + totalTimeInSec + "초 / 요금 " + tollFree + "원";
            Log.e(TAG, "onRouteStarted! " + log);

            mNavigationManager.navigationStarted();
            BroadcastHelper.sendNavigationStarted(mContext);
        }

        @Override
        public void onRouteFinished(@NonNull Bundle bundle) {
            int driveDistanceInMeter = bundle.getInt("driveDistanceInMeter");
            int driveTimeInSec = bundle.getInt("driveTimeInSec");

            String log = "주행 거리 " + driveDistanceInMeter + "m / 주행 시간 " + driveTimeInSec + "초";
            Log.e(TAG, "onRouteFinished! " + log);

            mNavigationManager.navigationEnded();
            BroadcastHelper.sendNavigationEnded(mContext);
        }

        @Override
        public void onInitSuccessEDC() {
            Log.e(TAG, "onInitSuccessEDC");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    registCallback();
                }
            },500);
        }

        @Override
        public void onFinishedEDC() {
            Log.e(TAG, "onFinishedEDC");
            unregistCallback();
        }

        @Override
        public void onHostAppStarted() {
            Log.e(TAG, "onHostAppStarted");
        }

        @Override
        public void onResult(@NonNull EDCConst.CommandState commandState, @NonNull Object o) {
            Log.e(TAG, "onResult / " + commandState.getValue() + " / " + commandState.toString() + " / " + o.toString());

            if (commandState.equals(EDCConst.CommandState.COMMAND_GET_INFO)) {
                Bundle data = (Bundle) o;
                Log.d(TAG, "onResult! COMMAND_GET_INFO: " + data);
            } else {
                if (commandState.equals(EDCConst.CommandState.COMMAND_TMAP_VERSION)) {
                    Log.d(TAG, "onResult! COMMAND_TMAP_VERSION : ");
                } else if (commandState.equals(EDCConst.CommandState.COMMAND_REG_CALLBACK)) {
                    Log.d(TAG, "onResult! COMMAND_REG_CALLBACK : ");
                } else if (commandState.equals(EDCConst.CommandState.COMMAND_UNREG_CALLBACK)) {
                    Log.d(TAG, "onResult! COMMAND_UNREG_CALLBACK : ");
                } else if (commandState.equals(EDCConst.CommandState.COMMAND_IS_RUNNING)) {
                    Log.d(TAG, "onResult! COMMAND_IS_RUNNING : ");
                } else if (commandState.equals(EDCConst.CommandState.COMMAND_USING_BLACKBOX)) {
                    Log.d(TAG, "onResult! COMMAND_USING_BLACKBOX : ");
                } else if (commandState.equals(EDCConst.CommandState.COMMAND_DRIVEMODE)) {
                    Log.d(TAG, "onResult! COMMAND_DRIVEMODE : ");
                } else if (commandState.equals(EDCConst.CommandState.COMMAND_IS_ROUTE)) {
                    Log.d(TAG, "onResult! COMMAND_IS_ROUTE : ");
                } else if (commandState.equals(EDCConst.CommandState.COMMAND_ADDRESS)) {
                    Log.d(TAG, "onResult! COMMAND_ADDRESS : ");
                } else if (commandState.equals(EDCConst.CommandState.COMMAND_SET_STATUS)) {
                    Log.d(TAG, "onResult! COMMAND_SET_STATUS : ");
                } else {
                    Log.e(TAG, "onResult! unknown command : " + commandState);
                }
            }
        }

        @Override
        public void onFail(@NonNull EDCConst.CommandState commandState, int i, String s) {
            Log.e(TAG, "onFail / " + commandState.getValue() + " " + commandState.name() + " / " + i + " / " + s);
        }
    };

    private void registCallback() {
        if (!isRegister) {
            Log.e(TAG, "call -- registerDataCallback");
            edc.registerDataCallback(new TmapEDCSDK.Companion.TMAPDataListener() {
                @Override
                public void onReceive(@Nullable Bundle bundle) {
                    convertToData(bundle);
                }
            });
            isRegister = true;
        }
    }

    private void unregistCallback() {
        if (isRegister) {
            Log.e(TAG, "call -- unregisterDataCallback");
            edc.unregisterDataCallback();
            isRegister = false;
        }
    }

    private void convertToData(Bundle bundle) {
        if (bundle == null) return;
        BroadcastHelper.sendEDCData(mContext, bundle);

        Trip.Builder builder = new Trip.Builder();

        EDCData edcData = EDCData.parse(bundle);

        if (!TextUtils.isEmpty(edcData.currentRoadName)) {
            builder.setCurrentRoad(edcData.currentRoadName);
        }

        if (edcData.firstTBTInfo != null) {
            TBTInfo tbtInfo = edcData.firstTBTInfo;

            Step step = getStep(tbtInfo, edcData);

            TravelEstimate travelEstimate = new TravelEstimate.Builder(
                TmapUtil.createDistance(tbtInfo.nTBTDist),
                DateTimeWithZone.create(TimeUnit.SECONDS.toMillis(tbtInfo.nTBTTime) + System.currentTimeMillis(), TimeZone.getTimeZone(TimeZone.getDefault().getID()))
            )
            .setRemainingTimeSeconds(edcData.remainTimeToDestinationInSec)
            .build();

            builder.addStep(step, travelEstimate);
        }

        if (edcData.secondTBTInfo != null) {
            TBTInfo tbtInfo = edcData.secondTBTInfo;

            Step step = getStep(tbtInfo, null);

            TravelEstimate travelEstimate = new TravelEstimate.Builder(
                TmapUtil.createDistance(tbtInfo.nTBTDist),
                DateTimeWithZone.create(TimeUnit.SECONDS.toMillis(tbtInfo.nTBTTime) + System.currentTimeMillis(), TimeZone.getTimeZone(TimeZone.getDefault().getID()))
            )
            .setRemainingTimeSeconds(edcData.remainTimeToDestinationInSec)
            .build();

            builder.addStep(step, travelEstimate);
        }

        if (!TextUtils.isEmpty(edcData.destinationName)) {
            Destination destination = new Destination.Builder().setName(edcData.destinationName).build();

            TravelEstimate travelEstimate = new TravelEstimate.Builder(
                TmapUtil.createDistance(edcData.remainDistanceToDestinationInMeter),
                DateTimeWithZone.create(TimeUnit.SECONDS.toMillis(edcData.remainTimeToDestinationInSec) + System.currentTimeMillis(), TimeZone.getTimeZone(TimeZone.getDefault().getID()))
            )
            .setRemainingTimeSeconds(edcData.remainTimeToDestinationInSec)
            .build();

            builder.addDestination(destination, travelEstimate);
        }

        Trip trip = builder.build();
        Log.d(TAG, "trip: " + trip);
        mNavigationManager.updateTrip(trip);
    }

    private Step getStep(TBTInfo tbtInfo, EDCData edcData) {
        Step.Builder stepBuilder = new Step.Builder();

        if (!TextUtils.isEmpty(tbtInfo.szTBTMainText)) {
            stepBuilder.setCue(tbtInfo.szTBTMainText);
        }

        if (!TextUtils.isEmpty(tbtInfo.szRoadName)) {
            stepBuilder.setRoad(tbtInfo.szRoadName);
        }

        stepBuilder.setManeuver(getManeuver(tbtInfo));

        if (edcData != null && edcData.laneCount > 0) {
            Lane.Builder laneBuilder = new Lane.Builder();
            for(int i=0;i<edcData.laneCount;i++) {
                laneBuilder.addDirection(getLaneDirection(edcData.laneTurnInfo[i], edcData.laneAvailableInfo[i] > 0));
            }
            stepBuilder.addLane(laneBuilder.build());
        }

        return stepBuilder.build();
    }

    private LaneDirection getLaneDirection(int turnInfo, boolean available) {
        switch (turnInfo) {
            case 1: return LaneDirection.create(LaneDirection.SHAPE_U_TURN_LEFT, available);
            case 2: return LaneDirection.create(LaneDirection.SHAPE_SLIGHT_LEFT, available);
            case 4: return LaneDirection.create(LaneDirection.SHAPE_NORMAL_LEFT, available);
            case 8: return LaneDirection.create(LaneDirection.SHAPE_STRAIGHT, available);
            case 16: return LaneDirection.create(LaneDirection.SHAPE_SLIGHT_RIGHT, available);
            case 32: return LaneDirection.create(LaneDirection.SHAPE_NORMAL_RIGHT, available);
        }
        return LaneDirection.create(LaneDirection.SHAPE_UNKNOWN, available);
    }

    private Maneuver getManeuver(TBTInfo tbtInfo) {
        Maneuver.Builder builder = new Maneuver.Builder(TmapUtil.getManeuverType(tbtInfo.nTBTTurnType));
        if (tbtInfo.nTBTTurnType == Maneuver.TYPE_ROUNDABOUT_ENTER_AND_EXIT_CCW_WITH_ANGLE) {
            builder.setRoundaboutExitAngle(TmapUtil.getRoundaboutExitAngle(tbtInfo.nTBTTurnType));
            builder.setRoundaboutExitNumber(TmapUtil.getRoundaboutExitNumber(tbtInfo.nTBTTurnType));
        }
        return builder.build();
    }
}
