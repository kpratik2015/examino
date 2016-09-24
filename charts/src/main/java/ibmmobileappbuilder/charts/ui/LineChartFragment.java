/*
 * Copyright 2016.
 * This code is part of IBM Mobile App Builder
 */

package ibmmobileappbuilder.charts.ui;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.Plot;
import com.androidplot.PlotListener;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ibmmobileappbuilder.charts.R;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.Pagination;
import ibmmobileappbuilder.ds.filter.Filter;
import ibmmobileappbuilder.ui.BaseFragment;
import ibmmobileappbuilder.ui.Filterable;
import ibmmobileappbuilder.ui.Refreshable;
import ibmmobileappbuilder.util.StringLabelFormat;

public abstract class LineChartFragment<T> extends BaseFragment
        implements ChartFragment, PlotListener, Filterable, Refreshable {

    private XYPlot plot;
    private Datasource<T> datasource;
    private Map<XYSeries, Integer> xySeriesMap;
    private int maxSeriesValue = 0;

    protected View chartContainer;
    protected View progressContainer;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        datasource = getDatasource();
        //use LinkedHashMap for ordered access
        xySeriesMap = new LinkedHashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_line_chart, container, false);

        plot = (XYPlot) view.findViewById(R.id.xy_plot);
        chartContainer = view.findViewById(R.id.chart_container);
        progressContainer = view.findViewById(R.id.progressContainer);

        // initialize chart
        plot.getTitleWidget().setText("No Data");
        plot.setBorderStyle(XYPlot.BorderStyle.NONE, null, null);
        plot.addListener(this);
        // reduce the number of range labels
        plot.setTicksPerRangeLabel(1);
        plot.setRangeValueFormat(new DecimalFormat("0"));
        plot.getGraphWidget().setDomainLabelOrientation(45);
        plot.getGraphWidget().setDomainLabelVerticalOffset(20);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }

    private void loadData() {
        //set up the listener for charts
        Datasource.Listener dsListener = new Datasource.Listener<List<T>>() {
            @Override
            public void onSuccess(List<T> ts) {
                if (plot != null && ts.size() != 0) {
                    loadChart(ts);
                    drawChart();
                }
                setListShown(true);
            }

            @Override
            public void onFailure(Exception e) {
                setListShown(true);
            }
        };

        if (datasource != null) {
            setListShown(false);
            if (datasource instanceof Pagination) {
                ((Pagination) datasource).getItems(0, dsListener);
            } else {
                datasource.getItems(dsListener);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        plot = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        datasource = null;
    }

    @Override
    public void setChartTitle(String title) {
        plot.getTitleWidget().setText(title);
    }

    public void setXAxisLabel(String label) {
        plot.setDomainLabel(label);
    }

    public void setYAxisLabel(String label) {
        plot.setRangeLabel(label);
    }

    @Override
    public void addSeries(List<Number> series, int seriesColor, String label) {

        // Turn the fiven array into XYSeries':
        XYSeries xySeries = new SimpleXYSeries(
                series,          // SimpleXYSeries takes a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                label);

        xySeriesMap.put(xySeries, seriesColor);
    }

    public void setXAxisValues(List<String> values) {
        StringLabelFormat stringLabelFormat = new StringLabelFormat(values);
        plot.setDomainValueFormat(stringLabelFormat);
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, values.size());
        plot.setDomainStepValue(1);
    }

    @Override
    public void drawChart() {
        for (XYSeries series : xySeriesMap.keySet()) {
            // Create a formatter to use for drawing a series using LineAndPointRenderer
            // and configure it from xml:
            LineAndPointFormatter seriesFormat = new LineAndPointFormatter();
            seriesFormat.setPointLabelFormatter(new PointLabelFormatter());
            seriesFormat.configure(getActivity().getApplicationContext(),
                    R.xml.line_point_formatter);
            seriesFormat.getLinePaint().setColor(xySeriesMap.get(series));
            seriesFormat.getVertexPaint().setColor(xySeriesMap.get(series));

            getXYPlot().addSeries(series, seriesFormat);

            for (int i = 0; i < series.size(); i++) {
                Number yValue = series.getY(i);
                if (yValue != null) {
                    maxSeriesValue = maxSeriesValue > yValue.intValue() ? maxSeriesValue : yValue.intValue();
                }
            }
        }
        if (maxSeriesValue < 10)
            plot.setRangeValueFormat(new DecimalFormat("0.0"));
        //set the plot's range by the range of the values set
        //getXYPlot().setRangeStep(XYStepMode.INCREMENT_BY_VAL, MathUtils.getOrderOfMagnitude(maxSeriesValue));
        plot.redraw();
    }

    public XYPlot getXYPlot() {
        return plot;
    }

    @Override
    public void onBeforeDraw(Plot plot, Canvas canvas) {
    }

    @Override
    public void onAfterDraw(Plot plot, Canvas canvas) {
    }

    @Override
    public void onSearchTextChanged(String s) {
        datasource.onSearchTextChanged(s);
        refresh();
    }

    @Override
    public void addFilter(Filter filter) {
        datasource.addFilter(filter);
    }

    @Override
    public void clearFilters() {
        datasource.clearFilters();
    }

    @Override
    public void refresh() {
        loadData();
    }

    private void setListShown(boolean shown) {
        if (progressContainer != null && chartContainer != null) {
            progressContainer.setVisibility(shown ? View.GONE : View.VISIBLE);
            chartContainer.setVisibility(shown ? View.VISIBLE : View.GONE);
        }
    }

    protected abstract Datasource<T> getDatasource();

    public abstract void loadChart(List<T> items);
}