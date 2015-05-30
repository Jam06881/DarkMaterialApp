package com.chummy.jezebel.material.dark.fragments;

import android.content.ComponentName;
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

import com.chummy.jezebel.material.dark.R;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;

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

        ActionBar toolbar = ((ActionBarActivity) context).getSupportActionBar();
        toolbar.setTitle(R.string.app_name);

        TextView playcolorsbtn = (TextView) root.findViewById(R.id.play_button_colors);
        playcolorsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent devPlay = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.play_store_colors_link)));
                Intent intent_colors = getActivity().getPackageManager().getLaunchIntentForPackage("com.chummy.jezebel.darkmaterial.colors");
                if (intent_colors == null) {
                    startActivity(devPlay);
                } else {
                    startActivity(intent_colors);
                }
            }
        });

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

        TextView regression = (TextView) root.findViewById(R.id.regression);
        regression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rate = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.play_store_link_regression)));
                startActivity(rate);
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
                Intent launch_theme = new Intent("android.intent.action.MAIN");
                launch_theme.setComponent(new ComponentName("org.cyanogenmod.theme.chooser", "org.cyanogenmod.theme.chooser.ChooserActivity"));
                launch_theme.putExtra("pkgName", context.getPackageName());
                Intent intent_cyanogenmod = getActivity().getPackageManager().getLaunchIntentForPackage("org.cyanogenmod.theme.chooser");
                Intent intent_settings = getActivity().getPackageManager().getLaunchIntentForPackage("com.android.settings");
                if (intent_cyanogenmod == null) {
                    Toast.makeText(getActivity(), getString(R.string.cm_not_configured), Toast.LENGTH_SHORT).show();
                    startActivity(intent_settings);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.theme_installed), Toast.LENGTH_LONG).show();
                    startActivity(launch_theme);
                }
            }
        });

        return root;
    }

}
