package com.chummy.jezebel.material.dark.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chummy.jezebel.material.dark.R;
import com.melnykov.fab.FloatingActionButton;

import eu.chainfire.libsuperuser.Shell;

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

        CardView whicons = (CardView) root.findViewById(R.id.whicons);
        whicons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent devPlay = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.play_store_whicons)));
                Intent intent_whicons = getActivity().getPackageManager().getLaunchIntentForPackage("com.whicons.iconpack");
                if (intent_whicons == null) {
                    startActivity(devPlay);
                } else {
                    startActivity(intent_whicons);
                }
            }
        });

        CardView play_dev = (CardView) root.findViewById(R.id.play_dev);
        play_dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent devPlay = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.play_store_dev_link)));
                startActivity(devPlay);
            }
        });

        CardView paypal = (CardView) root.findViewById(R.id.paypal);
        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appone = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.app_one_link)));
                startActivity(appone);
            }
        });

        CardView xda = (CardView) root.findViewById(R.id.xda);
        xda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent apptwo = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.app_two_link)));
                startActivity(apptwo);
            }
        });

        CardView gplus = (CardView) root.findViewById(R.id.gplus);
        gplus.setOnClickListener(new View.OnClickListener() {
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

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.cm_shortcut);
        FloatingActionButton fab2 = (FloatingActionButton) root.findViewById(R.id.restart_systemui);
        FloatingActionButton fab3 = (FloatingActionButton) root.findViewById(R.id.reboot_device);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launch_cm_theme = new Intent("android.intent.action.MAIN");
                launch_cm_theme.setComponent(new ComponentName("org.cyanogenmod.theme.chooser", "org.cyanogenmod.theme.chooser.ChooserActivity"));
                launch_cm_theme.putExtra("pkgName", context.getPackageName());
                Intent intent_cyanogenmod = getActivity().getPackageManager().getLaunchIntentForPackage("org.cyanogenmod.theme.chooser");
                Intent intent_rrolayers = getActivity().getPackageManager().getLaunchIntentForPackage("com.lovejoy777.rroandlayersmanager");
                Intent intent_settings = getActivity().getPackageManager().getLaunchIntentForPackage("com.android.settings");
                if (intent_cyanogenmod == null) {
                    if (intent_rrolayers == null) {
                        Toast.makeText(getActivity(), getString(R.string.cm_not_configured), Toast.LENGTH_SHORT).show();
                        startActivity(intent_settings);
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.theme_installed_rro), Toast.LENGTH_LONG).show();
                        startActivity(intent_rrolayers);
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.theme_installed_cm), Toast.LENGTH_LONG).show();
                    startActivity(launch_cm_theme);
                }
            }
        });


        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new StartUp()).setContext(v.getContext()).execute("sysui");
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new StartUp()).setContext(v.getContext()).execute("reboot");
            }
        });

        return root;
    }

    private class StartUp extends AsyncTask<String, Void, Void> {


        boolean suAvailable = false;
        private Context context = null;

        public StartUp setContext(Context context) {
            this.context = context;
            return this;
        }

        @Override
        protected Void doInBackground(String... params) {
            suAvailable = Shell.SU.available();
            if (suAvailable) {
                switch (params[0]) {
                    case "reboot":
                        Shell.SU.run("reboot");
                        break;
                    case "recov":
                        Shell.SU.run("reboot recovery");
                        break;
                    case "shutdown":
                        Shell.SU.run("reboot -p");
                        break;
                    case "sysui":
                        Shell.SU.run("pkill com.android.systemui");
                        break;
                }
            }

            return null;
        }

    }

}
