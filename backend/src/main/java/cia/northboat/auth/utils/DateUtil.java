package cia.northboat.auth.utils;

import java.util.Date;

public class DateUtil {

    public static int getDayGap(Date start){
        long gap = new Date().getTime()-start.getTime();
        return (int)gap/24/60/60/1000;
    }
}