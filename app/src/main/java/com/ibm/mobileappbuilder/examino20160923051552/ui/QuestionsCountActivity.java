
package com.ibm.mobileappbuilder.examino20160923051552.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ibm.mobileappbuilder.examino20160923051552.R;

import ibmmobileappbuilder.ui.BaseDetailActivity;
/**
 * QuestionsCountActivity chart screen
 */
public class QuestionsCountActivity extends BaseDetailActivity {


    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);        
        
        getSupportActionBar ().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.questionsCountActivity));
    }   

    // DetailActivity interface

    @Override
    protected Class<? extends Fragment> getFragmentClass() {
        return QuestionsCountFragment.class;
    }

}


