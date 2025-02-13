package com.mgears.navigation.tmap;

import androidx.car.app.model.Distance;

import java.util.Locale;

public class TmapUtil {
    public static Distance createDistance(int dist) {
        double unitDistance;
        if (dist < 0) {
            return Distance.create(0, Distance.UNIT_METERS);
        }

        int distanceUnit = Distance.UNIT_METERS;
        if (dist < 1000) {
            unitDistance = dist;
        } else {
            distanceUnit = Distance.UNIT_KILOMETERS;
            if (dist >= 10000) {
                unitDistance = dist / 1000;
            } else if (dist >= 1000) {
                unitDistance = Double.parseDouble(String.format(Locale.KOREA, "%.1f", dist / 1000.0d));
                if (unitDistance != ((int) unitDistance)) {
                    distanceUnit = Distance.UNIT_KILOMETERS_P1;
                }
            } else {
                unitDistance = 0.0d;
            }
        }
        return Distance.create(unitDistance, distanceUnit);
    }

    public static int getManeuverType(int turnCode) {
        switch (turnCode) {
            case 12:
                return 7;
            case 13:
            case 15:
                return 8;
            case 14:
                return 11;
            case 16:
                return 9;
            case 17:
                return 5;
            case 18:
                return 6;
            case 19:
                return 10;
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
            case 136:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
                return 35;
            case 43:
                return 4;
            case 44:
                return 3;
            case 200:
                return 1;
            case 201:
                return 39;
            case 6:
            case 73:
            case 74:
            case 117:
            case 123:
            case 124:
                return 26;
            case 7:
            case 75:
            case 76:
            case 118:
                return 25;
            case 101:
            case 111:
                return 14;
            case 102:
            case 112:
                return 13;
            case 104:
            case 114:
                return 22;
            case 105:
            case 115:
                return 21;
            default:
                return 36;
        }
    }

    public static int getRoundaboutExitAngle(int turnCode) {
        switch (turnCode) {
            case 31:
            case 131:
                return 150;
            case 32:
            case 132:
                return 120;
            case 33:
            case 133:
                return 90;
            case 34:
            case 134:
                return 60;
            case 35:
            case 135:
                return 30;
            case 36:
            case 136:
                return 360;
            case 37:
            case 137:
                return 330;
            case 38:
            case 138:
                return 300;
            case 39:
            case 139:
                return 270;
            case 40:
            case 140:
                return 240;
            case 41:
            case 141:
                return 210;
            default:
                return 180;
        }
    }

    public static int getRoundaboutExitNumber(int turnCode) {
        if (turnCode == 31 || turnCode == 32 || turnCode == 131 || turnCode == 132) {
            return 2;
        }
        switch (turnCode) {
            case 36:
            case 37:
            case 38:
            case 136:
            case 137:
            case 138:
                return 4;
            case 39:
            case 40:
            case 41:
            case 139:
            case 140:
            case 141:
                return 3;
            case 42:
            case 142:
                return 2;
            default:
                return 1;
        }
    }
}
