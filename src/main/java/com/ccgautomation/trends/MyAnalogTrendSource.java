package com.ccgautomation.trends;

import com.controlj.green.addonsupport.access.Location;
import com.controlj.green.addonsupport.access.TrendException;
import com.controlj.green.addonsupport.access.aspect.AnalogTrendSource;
import com.controlj.green.addonsupport.access.trend.TrendAnalogSample;
import com.controlj.green.addonsupport.access.trend.TrendData;
import com.controlj.green.addonsupport.access.trend.TrendProcessor;
import com.controlj.green.addonsupport.access.trend.TrendRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.Iterator;

public class MyAnalogTrendSource implements AnalogTrendSource {

    TrendData<TrendAnalogSample> data = new TrendData<TrendAnalogSample>() {
        @Nullable
        @Override
        public TrendAnalogSample getStartBookend() {
            return null;
        }

        @Nullable
        @Override
        public TrendAnalogSample getEndBookend() {
            return null;
        }

        @NotNull
        @Override
        public Iterator<TrendAnalogSample> getSamples() throws TrendException {
            return null;
        }

        @NotNull
        @Override
        public <P extends TrendProcessor<TrendAnalogSample>> P process(@NotNull P p) throws TrendException {
            return null;
        }

        @NotNull
        @Override
        public Type getSourceType() {
            return null;
        }
    };

    public MyAnalogTrendSource(AnalogTrendSource trend) {

    }

    @NotNull
    @Override
    public TrendData<TrendAnalogSample> getTrendData(@NotNull TrendRange trendRange) {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @NotNull
    @Override
    public Type getType() {
        return null;
    }

    @Override
    public boolean isInterval() {
        return false;
    }

    @Override
    public long getSampleInterval() throws TrendException {
        return 0;
    }

    @Override
    public boolean isCOV() {
        return false;
    }

    @Override
    public boolean isWire() {
        return false;
    }

    @Override
    public float getCOVIncrement() throws TrendException {
        return 0;
    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public boolean isStopWhenFull() {
        return false;
    }

    @Override
    public boolean isHistorianEnabled() {
        return false;
    }

    @Override
    public int getHistorianTrigger() {
        return 0;
    }

    @NotNull
    @Override
    public Date getOldestSampleTimeInDatabase() {
        return null;
    }

    @NotNull
    @Override
    public Date getLatestSampleTimeInDatabase() {
        return null;
    }

    @Override
    public int getNumberOfSamplesInDatabase() {
        return 0;
    }

    @Override
    public boolean isHistorianExpirationSystemDefault() {
        return false;
    }

    @Override
    public int getHistorianExpirationDays() {
        return 0;
    }

    @Override
    public int getSystemDefaultExpirationDays() {
        return 0;
    }

    @NotNull
    @Override
    public String getUnits() {
        return null;
    }

    @NotNull
    @Override
    public Location getLocation() {
        return null;
    }
}
