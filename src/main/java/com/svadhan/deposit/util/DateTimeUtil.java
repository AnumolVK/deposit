package com.svadhan.deposit.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeUtil {
    public static long getCurrentTimeLong(){
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        ZoneId kolkataZoneId = ZoneId.of("Asia/Kolkata");
        ZonedDateTime nowInKolkata = ZonedDateTime.now(kolkataZoneId);
        Instant instant = zonedDateTime.toInstant();
        return instant.toEpochMilli();

    }
}