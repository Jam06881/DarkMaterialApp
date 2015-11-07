package com.chummy.jezebel.material.dark.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chummy.jezebel.material.dark.R;

/**
 * Created by Nicholas on 11/07/2015.
 */
public class WhatIsThemed extends Fragment {

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.section_whatisthemed, null);

        context = getActivity();

        ActionBar toolbar = ((ActionBarActivity) context).getSupportActionBar();
        toolbar.setTitle(R.string.section_nine);
        return root;
    }

}
