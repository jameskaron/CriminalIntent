package com.bignerdranch.android.criminalintent.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;

import com.bignerdranch.android.criminalintent.R;

import java.util.Date;

/**
 * Created by pccw on 10/6/16.
 */
public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIME = "com.bignerdranch.android.criminalintent.time";

    public static TimePickerFragment newInstance(Date date) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_TIME, date);

        TimePickerFragment timePickerFragment = new TimePickerFragment();

        timePickerFragment.setArguments(bundle);

        return timePickerFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);

        TimePicker timePicker = (TimePicker) v.findViewById(R.id.dialog_time_timePicker);


        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok,null)
                .create();
    }
}
