
package com.ibm.mobileappbuilder.examino20160923051552.ui;
import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.util.ColorUtils;
import ibmmobileappbuilder.util.StringUtils;

import android.widget.TextView;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.ibm.mobileappbuilder.examino20160923051552.ds.QuestioncountDSItem;
import com.ibm.mobileappbuilder.examino20160923051552.ds.QuestioncountDS;

import static ibmmobileappbuilder.analytics.injector.PageViewBehaviorInjector.pageViewBehavior;

public class QuestionsCountFragment extends ibmmobileappbuilder.charts.ui.PieChartFragment<QuestioncountDSItem> {

    private Datasource<QuestioncountDSItem> datasource;
    private SearchOptions searchOptions;

    public static QuestionsCountFragment newInstance(Bundle args){
        QuestionsCountFragment fragment = new QuestionsCountFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public QuestionsCountFragment(){
        super();
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
      protected Datasource<QuestioncountDSItem> getDatasource() {
        if (datasource != null) {
          return datasource;
        }
          searchOptions = SearchOptions.Builder.searchOptions().build();
         datasource = QuestioncountDS.getInstance(searchOptions);
            return datasource;
      }

    @Override
    public void loadChart(List<QuestioncountDSItem> items) {
        if (items.size() != 0) {

            setChartTitle("Questions Count");
            // PieChart addSerie method works with lists filled with only 1 value.
            // Multiples series are not allowed.
            for (int i = 0; i < items.size(); i++) {
                List<Number> segment = new ArrayList<Number>();
                QuestioncountDSItem item = items.get(i);

                //StringToNumber is used to maintain backward compatibility with older apps
                Number value = StringUtils.StringToNumber(item.questionsCount.toString());

                if (value != null){
                    segment.add(value);

                    String[] defaultColors = ColorUtils.getDefaultPalette();
                    int color = Color.parseColor(defaultColors[(i % defaultColors.length)]);
                    addSeries(segment, color, value.toString());
                }
            }
        }
    }
}

