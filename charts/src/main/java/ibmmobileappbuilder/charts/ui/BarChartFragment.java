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
import com.androidplot.ui.SeriesRenderer;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
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

public abstract class BarChartFragment<T> extends BaseFragment
        implements ChartFragment, PlotListener, Filterable, Refreshable {

    private XYPlot plot;
    private Datasource<T> datasource;
    private List<String> xAxisValues;
    private Map<XYSeries, Integer> xySeriesMap;
    private int maxSeriesValue = 0;

    protected View chartContainer;
    protected View progressContainer;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        //use LinkedHashMap for ordered access
        xySeriesMap = new LinkedHashMap<>();
        datasource = getDatasource();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bar_chart, container, false);

        plot = (XYPlot) view.findViewById(R.id.xy_plot_bar);

        chartContainer = view.findViewById(R.id.chart_container);
        progressContainer = view.findViewById(R.id.progressContainer);

        // intialize chart
        // remove border and background
        plot.setBorderStyle(XYPlot.BorderStyle.NONE, null, null);
        plot.getTitleWidget().setText("No Data");
        plot.addListener(this);
        // reduce the number of range labels
        plot.setTicksPerRangeLabel(1);
        plot.setRangeValueFormat(new DecimalFormat("0"));
        plot.getGraphWidget().setDomainLabelOrientation(45);
        plot.setRangeLowerBoundary(0, BoundaryMode.FIXED);
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
                series, // SimpleXYSeries takes a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                label);

        xySeriesMap.put(xySeries, seriesColor);
    }

    public void setXAxisValues(List<String> values) {
        xAxisValues = values;
        StringLabelFormat stringLabelFormat = new StringLabelFormat(values);
        plot.setDomainValueFormat(stringLabelFormat);
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, values.size());
        plot.setDomainStepValue(1);
    }

    @Override
    public void drawChart() {
        for (XYSeries series : xySeriesMap.keySet()) {
            //set the color in the format
            MyBarFormatter seriesFormat = new MyBarFormatter(xySeriesMap.get(series), xySeriesMap.get(series));
            getXYPlot().addSeries(series, seriesFormat);

            for (int i = 0; i < series.size(); i++) {
                Number yValue = series.getY(i);
                if (yValue != null) {
                    maxSeriesValue = maxSeriesValue > yValue.intValue() ? maxSeriesValue : yValue.intValue();
                }
            }
        }
        //the lower boundary is always -1 because the XAxis's always begin in 0 internally
        getXYPlot().setDomainBoundaries(-1, xAxisValues.size(), BoundaryMode.FIXED);
        if (maxSeriesValue < 10)
            plot.setRangeValueFormat(new DecimalFormat("0.0"));
        //set the plot's range by the range of the values set
        //getXYPlot().setRangeStep(XYStepMode.INCREMENT_BY_VAL, MathUtils.getOrderOfMagnitude(maxSeriesValue));
        getXYPlot().redraw();
    }

    public int getSeriesNumber() {
        return xySeriesMap.keySet().size();
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

    // Delegates
    protected abstract Datasource<T> getDatasource();

    protected abstract void loadChart(List<T> items);

    class MyBarFormatter extends BarFormatter {
        public MyBarFormatter(int fillColor, int borderColor) {
            super(fillColor, borderColor);
        }

        @Override
        public Class<? extends SeriesRenderer> getRendererClass() {
            return MyBarRenderer.class;
        }

        @Override
        public SeriesRenderer getRendererInstance(XYPlot plot) {
            return new MyBarRenderer(plot);
        }
    }

    class MyBarRenderer extends BarRenderer<MyBarFormatter> {

        public MyBarRenderer(XYPlot plot) {
            super(plot);
            setBarRenderStyle(BarRenderStyle.SIDE_BY_SIDE);
            setBarWidth(getSeriesNumber() * 30);
        }

        /**
         * Implementing this method to allow us to inject our
         * special selection formatter.
         *
         * @param index  index of the point being rendered.
         * @param series XYSeries to which the point being rendered belongs.
         * @return the formatter
         */
        @Override
        public MyBarFormatter getFormatter(int index, XYSeries series) {
            return getFormatter(series);
        }
    }

}
