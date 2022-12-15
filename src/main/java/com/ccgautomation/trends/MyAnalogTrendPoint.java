package com.ccgautomation.trends;

import java.util.Date;

public class MyAnalogTrendPoint {
    private Date date;
    private Float value;

    public MyAnalogTrendPoint(Date date, Float value) {
        this.date = date;
        this.value = value;
    }

    public Date getDate() { return date; }
    public Float getValue() { return value; }
}
