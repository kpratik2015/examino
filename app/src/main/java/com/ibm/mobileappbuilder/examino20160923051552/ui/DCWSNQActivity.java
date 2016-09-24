

package com.ibm.mobileappbuilder.examino20160923051552.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ibm.mobileappbuilder.examino20160923051552.R;

import ibmmobileappbuilder.ui.BaseListingActivity;
/**
 * DCWSNQActivity list activity
 */
public class DCWSNQActivity extends BaseListingActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.dcwsnqActivity));
    }

    @Override
    protected Class<? extends Fragment> getFragmentClass() {
        return DCWSNQFragment.class;
    }

}

