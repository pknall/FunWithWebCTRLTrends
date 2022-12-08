package com.ccgautomation.trends;

import com.controlj.green.addonsupport.access.trend.TrendAnalogSample;
import com.controlj.green.addonsupport.access.trend.TrendStatusFlag;
import com.controlj.green.addonsupport.access.trend.TrendType;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class MyAnalogTrendSample implements TrendAnalogSample {

    int quality;  // Or an ENUM stating what the problem was
    double dValue;
    float fValue;
    Date timeStamp;
    TrendType tType;
    float specialValue;
    Set<TrendStatusFlag> statusFlags;

    public MyAnalogTrendSample(TrendAnalogSample sample) {
        this.quality = 100;
        this.dValue = sample.doubleValue();
        this.fValue = sample.floatValue();
        this.timeStamp = sample.getTime();
        this.tType = sample.getType();
        this.specialValue = sample.getSpecialValue();
        this.statusFlags = sample.getStatusFlags();
    }
    @Override
    public double doubleValue() {
        return dValue;
    }

    @Override
    public float floatValue() {
        return fValue;
    }

    @NotNull
    @Override
    public Date getTime() {
        return timeStamp;
    }

    @Override
    public long getTimeInMillis() {
        return timeStamp.getTime();
    }

    @NotNull
    @Override
    public TrendType getType() {
        return tType;
    }

    @Override
    public float getSpecialValue() {
        return specialValue;
    }

    @NotNull
    @Override
    public Set<TrendStatusFlag> getStatusFlags() {
        return statusFlags;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(timeStamp.toString());
        sb.append(",");
        sb.append(fValue);
        sb.append(",");
        sb.append(statusFlags);
        sb.append(",");
        sb.append("<!>");

        return sb.toString();
    }
}
