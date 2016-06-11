package com.bignerdranch.android.criminalintent.activity;

import com.bignerdranch.android.criminalintent.fragment.CrimeListFragment;

/**
 * Created by pccw on 28/3/16.
 */
public class CrimeListActivity extends SingleFragmentActivity {


    @Override
    protected android.support.v4.app.Fragment createFragment() {
        return new CrimeListFragment();
    }
}
