package com.mgears.navigation.tmap;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class TBTInfo {
    public boolean isAfterWhilePlayed;
    public int nSvcLinkDist;
    public int nTBTDist;
    public int nTBTNextRoadWidth;
    public int nTBTTime;
    public int nTBTTurnType;
    public int nTollFee;
    public String szCrossName;
    public String szFarDirName;
    public String szMidDirName;
    public String szNearDirName;
    public String szRoadName;
    public String szTBTMainText;
    public double vpTBTPointLat;
    public double vpTBTPointLon;
    public short nExtcVoiceCode = 0;
    public boolean hasNvx = false;

    public static TBTInfo parse(HashMap<String, Object> map) {
        TBTInfo tbtInfo = new TBTInfo();

        if (map.containsKey("nTBTTurnType")) tbtInfo.nTBTTurnType = (int) map.get("nTBTTurnType");
        if (map.containsKey("nTBTDist")) tbtInfo.nTBTDist = (int) map.get("nTBTDist");
        if (map.containsKey("nTBTTime")) tbtInfo.nTBTTime = (int) map.get("nTBTTime");
        if (map.containsKey("szTBTMainText")) tbtInfo.szTBTMainText = (String) map.get("szTBTMainText");
        if (map.containsKey("szRoadName")) tbtInfo.szRoadName = (String) map.get("szRoadName");

        return tbtInfo;
    }

    @NonNull
    @Override
    public String toString() {
        return "TBTInfo{" + "nTBTDist=" + nTBTDist +
                ", " + "nTBTTime=" + nTBTTime +
                ", " + "nTBTTurnType=" + nTBTTurnType +
                ", " + "szRoadName=" + szRoadName +
                ", " + "szTBTMainText=" + szTBTMainText +
                "}";
    }
}
