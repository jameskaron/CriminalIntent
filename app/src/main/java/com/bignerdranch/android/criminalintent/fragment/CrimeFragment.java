package com.bignerdranch.android.criminalintent.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.bignerdranch.android.criminalintent.R;
import com.criminalintent.model.Crime;
import com.criminalintent.model.CrimeLab;

import java.util.Date;
import java.util.UUID;

/**
 * Created by pccw on 7/3/16.
 */
public class CrimeFragment extends android.support.v4.app.Fragment {

    public static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";

    private static final String DIALOG_DATE = "date";

    private static final String DIALOG_TIME = "time";

    private static final int REQUEST_DATE = 0;

    private Crime crime;
    private Date date;


    private EditText titleField;
    private Button dateButton;
    private Button timeButton;
    private CheckBox solvedCheckBox;


    public static CrimeFragment newInstance(UUID crimeId) {

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID,crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
        crime = CrimeLab.get(getActivity()).getCrime(crimeId);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        titleField = (EditText) v.findViewById(R.id.crime_title);
        dateButton = (Button) v.findViewById(R.id.crime_date);
        timeButton = (Button) v.findViewById(R.id.crime_time);
        solvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);

        titleField.setText(crime.getTitle());
        solvedCheckBox.setChecked(crime.isSolved());

        titleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                crime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setSolved(isChecked);
            }
        });

        updateDate();

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(crime.getDate());

                dialog.setTargetFragment(CrimeFragment.this,REQUEST_DATE);

                dialog.show(fm,DIALOG_DATE);
            }
        });


        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(crime.getDate());

                dialog.show(fm,DIALOG_TIME);
            }
        });
        return v;
    }


    public void returnResult() {
        getActivity().setResult(Activity.RESULT_OK,null);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);

            crime.setDate(date);
            updateDate();

        }
    }

    public void updateDate() {
        date = crime.getDate();
        DateFormat dateFormat = new DateFormat();
        dateButton.setText(dateFormat.format("EEEE,MMM dd,yyyy",date));

        timeButton.setText(dateFormat.format("HH：mm",date));
    }
}
