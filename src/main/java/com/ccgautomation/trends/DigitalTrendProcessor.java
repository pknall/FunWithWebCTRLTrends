package com.ccgautomation.trends;

import com.controlj.green.addonsupport.access.trend.TrendDigitalSample;
import com.controlj.green.addonsupport.access.trend.TrendProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class DigitalTrendProcessor implements TrendProcessor<TrendDigitalSample> {
    @Override
    public void processStart(@NotNull Date date, @Nullable TrendDigitalSample trendDigitalSample) {

    }

    @Override
    public void processData(@NotNull TrendDigitalSample trendDigitalSample) {

    }

    @Override
    public void processEnd(@NotNull Date date, @Nullable TrendDigitalSample trendDigitalSample) {

    }

    @Override
    public void processHole(@NotNull Date date, @NotNull Date date1) {

    }
}
