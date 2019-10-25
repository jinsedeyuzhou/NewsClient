package com.ebrightmoon.doraemonkit.ui.realtime.datasource;

import com.ebrightmoon.doraemonkit.kit.common.PerformanceDataManager;
import com.ebrightmoon.doraemonkit.ui.realtime.widget.LineChart;

public class CpuDataSource implements IDataSource {
    @Override
    public LineChart.LineData createData() {
        float rate = PerformanceDataManager.getInstance().getLastCpuRate();
        return LineChart.LineData.obtain(rate, Math.round(rate) + "%");
    }
}
