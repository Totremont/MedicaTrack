package com.example.medicatrack.utilities;

import java.time.ZonedDateTime;

public final class FechaFormat
{
    private FechaFormat(){};

    public static String formattedHora(ZonedDateTime date)
    {
        StringBuilder text = new StringBuilder();
        if(date.getHour() < 10) text.append("0");
        text.append(date.getHour()).append(":");
        if(date.getMinute() < 10) text.append("0");
        text.append(date.getMinute());
        return text.toString();
    }

}
