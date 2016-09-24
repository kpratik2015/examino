

package com.ibm.mobileappbuilder.examino20160923051552.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import ibmmobileappbuilder.ui.BaseDetailActivity;

/**
 * QuestionsDSItemFormActivity form activity
 */
public class QuestionsDSItemFormActivity extends BaseDetailActivity {
  	
  	@Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    
    @Override
    protected Class<? extends Fragment> getFragmentClass() {
        return QuestionsDSItemFormFragment.class;
    }
}


