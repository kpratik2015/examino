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
import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.xy.XYPlot;

import java.util.List;

import ibmmobileappbuilder.charts.R;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.Pagination;
import ibmmobileappbuilder.ds.filter.Filter;
import ibmmobileappbuilder.ui.BaseFragment;
import ibmmobileappbuilder.ui.Filterable;
import ibmmobileappbuilder.ui.Refreshable;

public abstract class PieChartFragment<T> extends BaseFragment
        implements ChartFragment, PlotListener, Filterable, Refreshable {

    private PieChart plot;
    private Datasource<T> datasource;

    protected View chartContainer;
    protected View progressContainer;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        datasource = getDatasource();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);

        plot = (PieChart) view.findViewById(R.id.PieChart);

        chartContainer = view.findViewById(R.id.chart_container);
        progressContainer = view.findViewById(R.id.progressContainer);

        // initialize chart
        plot.setBorderStyle(XYPlot.BorderStyle.NONE, null, null);
        plot.getTitleWidget().setText("No Data");
        plot.addListener(this);

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
    public void setChartTitle(String title) {
        plot.getTitleWidget().setText(title);
    }

    @Override
    public void addSeries(List<Number> series, int seriesColor, String label) {

        Segment s1 = new Segment(label, series.get(0));
        SegmentFormatter sf = new SegmentFormatter(seriesColor, seriesColor);
        sf.configure(getActivity().getApplicationContext(), R.xml.pie_segment_formatter);
        plot.addSeries(s1, sf);
    }

    @Override
    public void drawChart() {
        PieRenderer renderer = plot.getRenderer(PieRenderer.class);
        if (renderer != null) {
            renderer.setDonutSize(0,
                    PieRenderer.DonutMode.PERCENT);
        }
        plot.redraw();
    }

    public PieChart getXYPlot() {
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
}