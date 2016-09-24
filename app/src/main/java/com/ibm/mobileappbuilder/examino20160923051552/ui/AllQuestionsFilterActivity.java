

package com.ibm.mobileappbuilder.examino20160923051552.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Arrays;

import com.ibm.mobileappbuilder.examino20160923051552.R;

import ibmmobileappbuilder.ui.BaseFragment;
import ibmmobileappbuilder.ui.FilterActivity;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;

import com.ibm.mobileappbuilder.examino20160923051552.ds.QuestionsDS;
import ibmmobileappbuilder.dialogs.ValuesSelectionDialog;
import ibmmobileappbuilder.views.ListSelectionPicker;
import java.util.ArrayList;

/**
 * AllQuestionsFilterActivity filter activity
 */
public class AllQuestionsFilterActivity extends FilterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // set title
        setTitle(R.string.allQuestionsFilterActivity);
    }

    @Override
    protected Fragment getFragment() {
        return new PlaceholderFragment();
    }

    public static class PlaceholderFragment extends BaseFragment {
        private SearchOptions.Builder searchOptionsBuilder = SearchOptions.Builder.searchOptions();
        private SearchOptions searchOptions;

        // filter field values
            
    ArrayList<String> subject_values;
    
    ArrayList<String> type_values;

        public PlaceholderFragment() {
              searchOptions = SearchOptions.Builder.searchOptions().build();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.allquestions_filter, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            // Get saved values
            Bundle bundle = savedInstanceState;
            if(bundle == null) {
                bundle = getArguments();
            }
            // get initial data
                        
            subject_values = bundle.getStringArrayList("subject_values");
            
            type_values = bundle.getStringArrayList("type_values");

            // bind pickers
                        
            final ListSelectionPicker subject_view = (ListSelectionPicker) view.findViewById(R.id.subject_filter);
            ValuesSelectionDialog subject_dialog = (ValuesSelectionDialog) getFragmentManager().findFragmentByTag("subject");
            if (subject_dialog == null)
                subject_dialog = new ValuesSelectionDialog();
            
            // configure the dialog
            subject_dialog.setColumnName("subject")
                .setDatasource(QuestionsDS.getInstance(searchOptions))
                .setSearchOptions(searchOptions)
                .setTitle("Subject")
                .setHaveSearch(true)
                .setMultipleChoice(true);
            
            // bind the dialog to the picker
            subject_view.setSelectionDialog(subject_dialog)
                .setTag("subject")
                .setSelectedValues(subject_values)
                .setSelectedListener(new ListSelectionPicker.ListSelectedListener() {
                @Override
                public void onSelected(ArrayList<String> selected) {
                    subject_values = selected;
                }
            });
            
            final ListSelectionPicker type_view = (ListSelectionPicker) view.findViewById(R.id.type_filter);
            ValuesSelectionDialog type_dialog = (ValuesSelectionDialog) getFragmentManager().findFragmentByTag("type");
            if (type_dialog == null)
                type_dialog = new ValuesSelectionDialog();
            
            // configure the dialog
            type_dialog.setColumnName("type")
                .setDatasource(QuestionsDS.getInstance(searchOptions))
                .setSearchOptions(searchOptions)
                .setTitle("Type")
                .setHaveSearch(true)
                .setMultipleChoice(true);
            
            // bind the dialog to the picker
            type_view.setSelectionDialog(type_dialog)
                .setTag("type")
                .setSelectedValues(type_values)
                .setSelectedListener(new ListSelectionPicker.ListSelectedListener() {
                @Override
                public void onSelected(ArrayList<String> selected) {
                    type_values = selected;
                }
            });

            // Bind buttons
            Button okBtn = (Button) view.findViewById(R.id.ok);
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();

                    // send filter result back to caller
                                        
                    intent.putStringArrayListExtra("subject_values", subject_values);
                    
                    intent.putStringArrayListExtra("type_values", type_values);

                    getActivity().setResult(RESULT_OK, intent);
                    getActivity().finish();
                }
            });

            Button cancelBtn = (Button) view.findViewById(R.id.reset);
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Reset values
                                        
                    subject_values = new ArrayList<String>();
                    subject_view.setSelectedValues(null);
                    
                    type_values = new ArrayList<String>();
                    type_view.setSelectedValues(null);
                }
            });
        }

        @Override
        public void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);

            // save current status
                        
            bundle.putStringArrayList("subject_values", subject_values);
            
            bundle.putStringArrayList("type_values", type_values);
        }
    }

}

