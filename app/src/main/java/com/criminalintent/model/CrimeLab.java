package com.criminalintent.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by pccw on 25/3/16.
 * Singleton
 */
public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private Context appContext;
    private ArrayList<Crime> crimes;

    private CrimeLab(Context appContext) {
        this.appContext = appContext;
        crimes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            c.setSolved(i % 2 == 0);
            crimes.add(c);
        }
    }

    //singleton
    public static CrimeLab get(Context c) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes() {
        return crimes;
    }

    public Crime getCrime(UUID id) {
        for(Crime c:crimes) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }
}
