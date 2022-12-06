package com.ccgautomation.trends;

import com.controlj.green.addonsupport.access.trend.TrendAnalogSample;
import com.controlj.green.addonsupport.access.trend.TrendProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class AnalogTrendProcessor implements TrendProcessor<TrendAnalogSample> {
    @Override
    public void processStart(@NotNull Date date, @Nullable TrendAnalogSample trendAnalogSample) {

    }

    @Override
    public void processData(@NotNull TrendAnalogSample trendAnalogSample) {

    }

    @Override
    public void processEnd(@NotNull Date date, @Nullable TrendAnalogSample trendAnalogSample) {

    }

    @Override
    public void processHole(@NotNull Date date, @NotNull Date date1) {

    }
}
