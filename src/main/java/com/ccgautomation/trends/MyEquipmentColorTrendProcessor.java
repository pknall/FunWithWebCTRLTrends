package com.ccgautomation.trends;

import com.controlj.green.addonsupport.access.trend.TrendEquipmentColorSample;
import com.controlj.green.addonsupport.access.trend.TrendProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class MyEquipmentColorTrendProcessor  implements TrendProcessor<TrendEquipmentColorSample> {
    @Override
    public void processStart(@NotNull Date date, @Nullable TrendEquipmentColorSample trendEquipmentColorSample) {

    }

    @Override
    public void processData(@NotNull TrendEquipmentColorSample trendEquipmentColorSample) {

    }

    @Override
    public void processEnd(@NotNull Date date, @Nullable TrendEquipmentColorSample trendEquipmentColorSample) {

    }

    @Override
    public void processHole(@NotNull Date date, @NotNull Date date1) {

    }
}
