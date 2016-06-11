package com.bignerdranch.android.criminalintent.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.bignerdranch.android.criminalintent.fragment.CrimeFragment;
import com.bignerdranch.android.criminalintent.R;
import com.criminalintent.model.Crime;
import com.criminalintent.model.CrimeLab;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by pccw on 21/4/16.
 */
public class CrimePagerActivity extends FragmentActivity {

    private ViewPager viewPager;
    private ArrayList<Crime> crimes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewPager = new ViewPager(this);
        viewPager.setId(R.id.viewPage);
        //set layout
        setContentView(viewPager);

        crimes = CrimeLab.get(this).getCrimes();

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                //default the first one
                Crime crime = crimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return crimes.size();
            }
        });

        UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);

        for (int i = 0;i<crimes.size();i++){
            if (crimes.get(i).getId().equals(crimeId)) {
                setTitle(crimes.get(i).getTitle());
                viewPager.setCurrentItem(i);

                break;
            }
        }


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Crime crime = crimes.get(position);
                if (crime.getTitle() != null) {
                    setTitle(crime.getTitle());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }//oncreate


}
