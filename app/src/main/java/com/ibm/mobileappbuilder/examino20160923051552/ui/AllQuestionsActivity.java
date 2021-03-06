

package com.ibm.mobileappbuilder.examino20160923051552.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ibm.mobileappbuilder.examino20160923051552.R;

import ibmmobileappbuilder.ui.BaseListingActivity;
/**
 * AllQuestionsActivity list activity
 */
public class AllQuestionsActivity extends BaseListingActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.allQuestionsActivity));
    }

    @Override
    protected Class<? extends Fragment> getFragmentClass() {
        return AllQuestionsFragment.class;
    }

}

