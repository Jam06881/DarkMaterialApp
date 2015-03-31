package com.jahirfiquitiva.paperboard.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;
import com.chummy.jezebel.material.dark.R;

public class Home extends Fragment {

    private Context context;

    public static Fragment newInstance(Context context) {
        Home f = new Home();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.section_home, null);

        context = getActivity();

        ActionBar toolbar = ((ActionBarActivity)context).getSupportActionBar();
        toolbar.setTitle(R.string.app_name);

        TextView playbtn = (TextView) root.findViewById(R.id.play_button);
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent devPlay = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.play_store_dev_link)));
                startActivity(devPlay);
            }
        });

        TextView apponebtn = (TextView) root.findViewById(R.id.appone_button);
        apponebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appone = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.app_one_link)));
                startActivity(appone);
            }
        });

        TextView apptwobtn = (TextView) root.findViewById(R.id.apptwo_button);
        apptwobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent apptwo = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.app_two_link)));
                startActivity(apptwo);
            }
        });

        TextView appthreebtn = (TextView) root.findViewById(R.id.appthree_button);
        appthreebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appthree = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.app_three_link)));
                startActivity(appthree);
            }
        });

        TextView ratebtn = (TextView) root.findViewById(R.id.rate_button);
        ratebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rate = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.play_store_link)));
                startActivity(rate);
            }
        });

        ObservableScrollView view = (ObservableScrollView) root.findViewById(R.id.scrollView1);
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.cm_shortcut);
        fab.attachToScrollView(view);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("org.cyanogenmod.theme.chooser");
                if (intent == null) {
                    Toast.makeText(getActivity(), getString(R.string.cm_not_installed), Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(intent);
                }
            }
        });

        return root;
    }

}
