package com.ccgautomation.utilities;

import com.ccgautomation.trends.MyAnalogTrendProcessor;
import com.ccgautomation.trends.MyAnalogTrendSample;
import com.controlj.green.addonsupport.access.*;
import com.controlj.green.addonsupport.access.aspect.AnalogTrendSource;
import com.controlj.green.addonsupport.access.aspect.TrendSource;
import com.controlj.green.addonsupport.access.trend.TrendAnalogSample;
import com.controlj.green.addonsupport.access.trend.TrendData;
import com.controlj.green.addonsupport.access.trend.TrendRange;
import com.controlj.green.addonsupport.access.trend.TrendRangeFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrendTools {
    public List<MyAnalogTrendSample> doWork(final List<String> ids,
                                            final MyAnalogTrendProcessor myAnalogTrendProcessor,
                                            Date startDate,
                                            Date endDate) throws SystemException, ActionExecutionException {
        final TrendRange range = TrendRangeFactory.byDateRange(startDate, endDate);
        final List<MyAnalogTrendSample> results = new ArrayList<>();
        SystemConnection connection = DirectAccess.getDirectAccess().getRootSystemConnection();

        // Converted to lambda:
        // connection.runReadAction(FieldAccessFactory.newFieldAccess(), new ReadAction() {
        //    public void execute(@NotNull SystemAccess access) {
        connection.runReadAction(FieldAccessFactory.newFieldAccess(), access -> {
            Tree geo = access.getTree(SystemTree.Geographic);
            ids.stream().forEach(id -> {
                try {
                    Location location = getTreeLocationByReferenceName(geo, id);
                    TrendSource trendSource = getTrendSourceAtLocation(location);
                    TrendSource.Type type = trendSource.getType();
                    if (type == TrendSource.Type.Analog) {
                        TrendData<TrendAnalogSample> analogData = (((AnalogTrendSource) trendSource).getTrendData(range));
                        results.addAll(getTrendSources(analogData, myAnalogTrendProcessor));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        });
        return results;
    }

    private List<MyAnalogTrendSample> getTrendSources(TrendData<TrendAnalogSample> analogData,
                                                      MyAnalogTrendProcessor myAnalogTrendProcessor){
        try {
            return analogData.process(myAnalogTrendProcessor).getSamples();
        } catch (TrendException e) {
            // Log Error
            throw new RuntimeException("Unable to process Trend Data: " + e);
        }
    }

    private TrendSource getTrendSourceAtLocation(Location location) {
        try {
            return location.getAspect(TrendSource.class);
        } catch (NoSuchAspectException e) {
            // Log Error
            throw new RuntimeException("Unable to retrieve trend source at " + location + " + : " + e);
        }
    }

    private Location getTreeLocationByReferenceName(Tree tree, String id) {
        try {
            return tree.resolve(id.trim());
        } catch (UnresolvableException e) {
            // Log Error
            throw new RuntimeException("Unable to find tree location at " + id + " : " + e);
        }
    }


}
