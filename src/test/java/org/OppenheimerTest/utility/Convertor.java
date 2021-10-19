package org.OppenheimerTest.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Convertor {

    public static double roundHalfUp(double value, int scale) {

        BigDecimal bd = new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

    public static String maskNatId(String natId, int startFromIndex){

        String mask = "";

        for(int x = startFromIndex; x < natId.length(); x++){

            mask = mask + "$";
        }

        return natId.substring(0, startFromIndex) + mask;
    }

    public static double roundDown(double value, int scale) {

        BigDecimal bd = new BigDecimal(value).setScale(scale, RoundingMode.DOWN);

        return bd.doubleValue();
    }
}
