package com.chummy.jezebel.material.dark.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.chummy.jezebel.material.dark.R;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import eu.chainfire.libsuperuser.Shell;

public class Home extends Fragment {

    private Context context;
    private View famBg;

    public static Fragment newInstance(Context context) {
        Home f = new Home();
        return f;
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.section_home, null);

        context = getActivity();

        famBg = root.findViewById(R.id.famBg);
        famBg.setVisibility(View.GONE);

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

        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) root.findViewById(R.id.multiple_actions);
        menuMultipleActions.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                famBg.setVisibility(famBg.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onMenuCollapsed() {
                famBg.setVisibility(famBg.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });

        final com.getbase.floatingactionbutton.FloatingActionButton actionA = (com.getbase.floatingactionbutton.FloatingActionButton) root.findViewById(R.id.action_a);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launch_cm_theme = new Intent("android.intent.action.MAIN");
                launch_cm_theme.setComponent(new ComponentName("org.cyanogenmod.theme.chooser", "org.cyanogenmod.theme.chooser.ChooserActivity"));
                launch_cm_theme.putExtra("pkgName", context.getPackageName());
                Intent launch_cos_theme = new Intent("android.intent.action.MAIN");
                launch_cos_theme.setComponent(new ComponentName("com.cyngn.theme.chooser", "com.cyngn.theme.chooser.ChooserActivity"));
                launch_cos_theme.putExtra("pkgName", context.getPackageName());
                Intent intent_cyanogenmod = getActivity().getPackageManager().getLaunchIntentForPackage("org.cyanogenmod.theme.chooser");
                Intent intent_rrolayers = getActivity().getPackageManager().getLaunchIntentForPackage("com.lovejoy777.rroandlayersmanager");
                Intent intent_settings = getActivity().getPackageManager().getLaunchIntentForPackage("com.android.settings");
                if (intent_cyanogenmod == null) {
                    if (!isAppInstalled(getActivity(), "com.cyngn.theme.chooser")) {
                        if (intent_rrolayers == null) {
                            Toast.makeText(getActivity(), getString(R.string.cm_not_configured), Toast.LENGTH_SHORT).show();
                            startActivity(intent_settings);
                        } else {
                            actionA.setTitle("Layers Manager");
                            actionA.setIcon(R.drawable.layers);
                            Toast.makeText(getActivity(), getString(R.string.theme_installed_rro), Toast.LENGTH_LONG).show();
                            startActivity(intent_rrolayers);
                        }
                    } else {
                        actionA.setTitle("App Themer");
                        actionA.setIcon(R.drawable.theme_chooser);
                        Toast.makeText(getActivity(), getString(R.string.theme_installed_cm), Toast.LENGTH_LONG).show();
                        startActivity(launch_cos_theme);
                    }
                } else {
                    actionA.setTitle("Theme Engine");
                    actionA.setIcon(R.drawable.theme_chooser);
                    Toast.makeText(getActivity(), getString(R.string.theme_installed_cm), Toast.LENGTH_LONG).show();
                    startActivity(launch_cm_theme);
                }
            }


        });
        final com.getbase.floatingactionbutton.FloatingActionButton actionB = (com.getbase.floatingactionbutton.FloatingActionButton) root.findViewById(R.id.action_b);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Shell.SU.available()) {
                    showThreadCloserDialogSysUI();
                } else {
                    Toast toast1 = Toast.makeText(getActivity(), "Unfortunately, this feature is only available for root users.", Toast.LENGTH_LONG);
                    toast1.show();
                }
            }
        });
        final com.getbase.floatingactionbutton.FloatingActionButton actionC = (com.getbase.floatingactionbutton.FloatingActionButton) root.findViewById(R.id.action_c);
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Shell.SU.available()) {
                    showThreadCloserDialogReboot();
                } else {
                    Toast toast2 = Toast.makeText(getActivity(), "Unfortunately, this feature is only available for root users.", Toast.LENGTH_LONG);
                    toast2.show();
                }
            }
        });
        return root;
    }

    public void showThreadCloserDialogSysUI() {
        new MaterialDialog.Builder(getActivity())
                .title("Restart SystemUI")
                .content(R.string.systemui_restart)
                .positiveText("Proceed")
                .negativeText("Cancel")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        (new StartUp()).setContext(dialog.getContext()).execute("sysui");
                    }
                })
                .show();
    }

    public void showThreadCloserDialogReboot() {
        new MaterialDialog.Builder(getActivity())
                .title("System Reboot")
                .content(R.string.reboot)
                .positiveText("Proceed")
                .negativeText("Cancel")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        (new StartUp()).setContext(dialog.getContext()).execute("reboot");
                    }
                })
                .show();
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
