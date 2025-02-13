package com.mgears.navigation.tmap;

import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.HashMap;

public class EDCData {
    public String currentRoadName;
    public TBTInfo firstTBTInfo;
    public TBTInfo secondTBTInfo;
    public int laneCount;
    public int laneDistance;
    public int[] laneTurnInfo;
    public int[] laneAvailableInfo;
    public String destinationName;
    public int remainDistanceToDestinationInMeter;
    public int remainTimeToDestinationInSec;

    public static EDCData parse(Bundle bundle) {
        EDCData edcData = new EDCData();

        if (bundle.containsKey("currentRoadName")) edcData.currentRoadName = bundle.getString("currentRoadName");
        if (bundle.containsKey("firstTBTInfo")) {
            HashMap<String, Object> object = (HashMap<String, Object>) bundle.get("firstTBTInfo");
            edcData.firstTBTInfo = TBTInfo.parse(object);
        }
        if (bundle.containsKey("secondTBTInfo")) {
            HashMap<String, Object> object = (HashMap<String, Object>) bundle.get("secondTBTInfo");
            edcData.secondTBTInfo = TBTInfo.parse(object);
        }
        if (bundle.containsKey("laneCount")) edcData.laneCount = bundle.getInt("laneCount");
        if (bundle.containsKey("laneDistance")) edcData.laneDistance = bundle.getInt("laneDistance");
        if (bundle.containsKey("laneTurnInfo")) edcData.laneTurnInfo = bundle.getIntArray("laneTurnInfo");
        if (bundle.containsKey("laneAvailableInfo")) edcData.laneAvailableInfo = bundle.getIntArray("laneAvailableInfo");

        if (bundle.containsKey("destinationName")) edcData.destinationName = bundle.getString("destinationName");
        if (bundle.containsKey("remainDistanceToDestinationInMeter")) edcData.remainDistanceToDestinationInMeter = bundle.getInt("remainDistanceToDestinationInMeter");
        if (bundle.containsKey("remainTimeToDestinationInSec")) edcData.remainTimeToDestinationInSec = bundle.getInt("remainTimeToDestinationInSec");
        return edcData;
    }

    @NonNull
    @Override
    public String toString() {
        return "EDCData{" + "currentRoadName=" + currentRoadName +
                ", " + "firstTBTInfo=" + firstTBTInfo +
                ", " + "secondTBTInfo=" + secondTBTInfo +
                ", " + "laneCount=" + laneCount +
                ", " + "laneDistance=" + laneDistance +
                ", " + "laneTurnInfo=" + Arrays.toString(laneTurnInfo) +
                ", " + "laneAvailableInfo=" + Arrays.toString(laneAvailableInfo) +
                ", " + "destinationName=" + destinationName +
                ", " + "remainDistanceToDestinationInMeter=" + remainDistanceToDestinationInMeter +
                ", " + "remainTimeToDestinationInSec=" + remainTimeToDestinationInSec +
                "}";
    }
}
