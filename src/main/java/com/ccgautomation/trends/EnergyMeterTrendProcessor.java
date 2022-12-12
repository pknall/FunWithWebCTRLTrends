package com.ccgautomation.trends;

import com.controlj.green.addonsupport.access.trend.TrendAnalogSample;
import com.controlj.green.addonsupport.access.trend.TrendProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
    Processes a meter trend into a list of per-day totals
 */

public class EnergyMeterTrendProcessor implements TrendProcessor<TrendAnalogSample> {

    private int periodicity;
    // Priodicity inference method
    public EnergyMeterTrendProcessor() {
        this.periodicity = determinePeriodicity();
    }
    List<MyAnalogTrendSample> data = new ArrayList<>();

    public List<MyAnalogTrendSample> getSamples() {
        return data;
    }

    @Override
    public void processStart(@NotNull Date date, @Nullable TrendAnalogSample trendAnalogSample) {
        data.add(new MyAnalogTrendSample(trendAnalogSample));
    }

    @Override
    public void processData(@NotNull TrendAnalogSample trendAnalogSample) {
        data.add(new MyAnalogTrendSample(trendAnalogSample));
    }

    @Override
    public void processEnd(@NotNull Date date, @Nullable TrendAnalogSample trendAnalogSample) {
        data.add(new MyAnalogTrendSample(trendAnalogSample));
    }

    @Override
    public void processHole(@NotNull Date date, @NotNull Date date1) {

    }

    private int determinePeriodicity() {
        return -1;
    }
}
