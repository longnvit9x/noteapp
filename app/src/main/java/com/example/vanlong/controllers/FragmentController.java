package com.example.vanlong.controllers;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

/**
 * Created by vanlong on 4/13/2017.
 */

public class FragmentController {
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Activity activity;

    public FragmentController(Activity activity) {
        this.activity = activity;
    }

    public void addFragmentContent(int id, Fragment fragment) {
        mFragmentManager = activity.getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(id, fragment);
        mFragmentTransaction.commit();
    }

    public void hideFragmentContent(Fragment fragment) {
        mFragmentManager = activity.getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.hide(fragment);
        mFragmentTransaction.commit();
    }

    public void removeFragmentContent(Fragment fragment) {
        mFragmentManager = activity.getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.remove(fragment);
        mFragmentTransaction.commit();
    }

    public void showFragmentContent(Fragment fragment) {
        mFragmentManager = activity.getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.show(fragment);
        mFragmentTransaction.commit();
    }
}
