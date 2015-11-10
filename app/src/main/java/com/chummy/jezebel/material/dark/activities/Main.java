package com.chummy.jezebel.material.dark.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chummy.jezebel.material.dark.R;
import com.chummy.jezebel.material.dark.utils.ChangelogAdapter;
import com.chummy.jezebel.material.dark.utils.Preferences;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import eu.chainfire.libsuperuser.Shell;

public class Main extends ActionBarActivity {

    public Drawer.Result result = null;
    public AccountHeader.Result headerResult = null;
    public String thaApp, thaHome, thaPreviews, thaApply, thaWalls, thaRequest, thaCredits, thaTesters, thaWhatIsThemed, thaContactUs, thaLogcat, thaFAQ, thaHelp, thaAbout, thaIconPack, thaFullChangelog, thaBootAnimInstall, thaBootAnimRestore, thaBootAnimBackup;
    public String version, drawerVersion;
    public int currentItem;
    private boolean firstrun, enable_features;
    private Preferences mPrefs;
    private boolean withLicenseChecker = false;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        mPrefs = new Preferences(Main.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        thaApp = getResources().getString(R.string.app_name);
        thaHome = getResources().getString(R.string.section_one);
        thaPreviews = getResources().getString(R.string.section_two);
        thaApply = getResources().getString(R.string.section_three);
        thaWalls = getResources().getString(R.string.section_four);
        thaRequest = getResources().getString(R.string.section_five);
        thaCredits = getResources().getString(R.string.section_seven);
        thaTesters = getResources().getString(R.string.section_eight);
        thaWhatIsThemed = getResources().getString(R.string.section_nine);
        thaContactUs = getResources().getString(R.string.section_ten);
        thaLogcat = getResources().getString(R.string.section_eleven);
        thaFAQ = getResources().getString(R.string.section_twelve);
        thaHelp = getResources().getString(R.string.section_thirteen);
        thaAbout = getResources().getString(R.string.section_fourteen);
        thaIconPack = getResources().getString(R.string.section_fifteen);
        thaFullChangelog = getResources().getString(R.string.section_sixteen);
        thaBootAnimInstall = getResources().getString(R.string.section_seventeen);
        thaBootAnimRestore = getResources().getString(R.string.section_eighteen);
        thaBootAnimBackup = getResources().getString(R.string.section_nineteen);

        drawerVersion = getResources().getString(R.string.version_code);

        currentItem = 1;

        headerResult = new AccountHeader()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withSelectionFirstLine(getResources().getString(R.string.app_name))
                .withSelectionSecondLine(drawerVersion)
                .withSavedInstance(savedInstanceState)
                .withHeightDp(120)
                .build();

        enable_features = mPrefs.isFeaturesEnabled();
        firstrun = mPrefs.isFirstRun();

        result = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withHeaderDivider(false)
                .addDrawerItems(
                        new SectionDrawerItem().withName("Main"),
                        new PrimaryDrawerItem().withName(thaHome).withIcon(GoogleMaterial.Icon.gmd_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName(thaIconPack).withIcon(GoogleMaterial.Icon.gmd_picture_in_picture).withDescription("This applies icon pack 'Whicons'.").withCheckable(false).withIdentifier(11),
                        new PrimaryDrawerItem().withName(thaFullChangelog).withIcon(GoogleMaterial.Icon.gmd_wrap_text).withDescription("Complete changelog of Dark Material.").withCheckable(false).withIdentifier(12),
                        new DividerDrawerItem(),
                        new SectionDrawerItem().withName("Information"),
                        new PrimaryDrawerItem().withName(thaAbout).withIcon(GoogleMaterial.Icon.gmd_info_outline).withDescription("Basic information on the theme.").withIdentifier(10),
                        new PrimaryDrawerItem().withName(thaWhatIsThemed).withIcon(GoogleMaterial.Icon.gmd_warning).withDescription("List of overlaid applications.").withIdentifier(3),
                        new PrimaryDrawerItem().withName(thaFAQ).withIcon(GoogleMaterial.Icon.gmd_question_answer).withDescription("Common questions with answers.").withIdentifier(8),
                        new DividerDrawerItem(),
                        new SectionDrawerItem().withName("Layers Boot Animation Installer"),
                        new PrimaryDrawerItem().withName(thaBootAnimBackup).withIcon(GoogleMaterial.Icon.gmd_backup).withDescription("Do not run this more than once.").withCheckable(false).withBadge("BETA").withIdentifier(15),
                        new PrimaryDrawerItem().withName(thaBootAnimInstall).withIcon(GoogleMaterial.Icon.gmd_file_download).withDescription("If this fails, run it again.").withCheckable(false).withBadge("BETA").withIdentifier(13),
                        new PrimaryDrawerItem().withName(thaBootAnimRestore).withIcon(GoogleMaterial.Icon.gmd_file_upload).withDescription("This can also flash custom anims.").withCheckable(false).withBadge("BETA").withIdentifier(14),
                        new DividerDrawerItem(),
                        new SectionDrawerItem().withName("Tools & Utilities"),
                        new PrimaryDrawerItem().withName(thaLogcat).withIcon(GoogleMaterial.Icon.gmd_bug_report).withDescription("System level log recording.").withCheckable(false).withBadge("ROOT ★").withIdentifier(7),
                        new DividerDrawerItem(),
                        new SectionDrawerItem().withName("The Developers"),
                        new PrimaryDrawerItem().withName(thaCredits).withIcon(GoogleMaterial.Icon.gmd_people).withDescription("chummy development team").withIdentifier(5),
                        new PrimaryDrawerItem().withName(thaTesters).withIcon(GoogleMaterial.Icon.gmd_star).withDescription("The people who keep the team updated.").withIdentifier(4),
                        new DividerDrawerItem(),
                        new SectionDrawerItem().withName("Contact"),
                        new SecondaryDrawerItem().withName(thaContactUs).withIcon(GoogleMaterial.Icon.gmd_mail).withCheckable(false).withIdentifier(6),
                        new SecondaryDrawerItem().withName(thaHelp).withIcon(GoogleMaterial.Icon.gmd_help).withCheckable(false).withIdentifier(9)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {

                        if (drawerItem != null) {

                            switch (drawerItem.getIdentifier()) {
                                case 1:
                                    switchFragment(1, thaApp, "Home");
                                    break;
                                case 2:
                                    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                                    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                                    if (isConnected) {
                                        switchFragment(2, thaWalls, "Wallpapers");
                                    } else {
                                        showNotConnectedDialog();
                                    }
                                    break;
                                case 3:
                                    switchFragment(3, thaWhatIsThemed, "WhatIsThemed");
                                    break;
                                case 4:
                                    switchFragment(4, thaTesters, "Testers");
                                    break;
                                case 5:
                                    switchFragment(5, thaCredits, "Credits");
                                    break;
                                case 6:
                                    StringBuilder emailBuilder = new StringBuilder();
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + getResources().getString(R.string.email_id)));
                                    if (!isAppInstalled("org.cyanogenmod.theme.chooser")) {
                                        if (!isAppInstalled("com.cyngn.theme.chooser")) {
                                            if (!isAppInstalled("com.lovejoy777.rroandlayersmanager")) {
                                                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.email_subject));
                                            } else {
                                                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.email_subject_rro));
                                            }
                                        } else {
                                            intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.email_subject_cos));
                                        }
                                    } else {
                                        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.email_subject_cm));
                                    }
                                    emailBuilder.append("\n \n \nOS Version: " + System.getProperty("os.version") + "(" + Build.VERSION.INCREMENTAL + ")");
                                    emailBuilder.append("\nOS API Level: " + Build.VERSION.SDK_INT + " (" + Build.VERSION.RELEASE + ") " + "[" + Build.ID + "]");
                                    emailBuilder.append("\nDevice: " + Build.DEVICE);
                                    emailBuilder.append("\nManufacturer: " + Build.MANUFACTURER);
                                    emailBuilder.append("\nModel (and Product): " + Build.MODEL + " (" + Build.PRODUCT + ")");
                                    if (!isAppInstalled("org.cyanogenmod.theme.chooser")) {
                                        if (!isAppInstalled("com.cyngn.theme.chooser")) {
                                            if (!isAppInstalled("com.lovejoy777.rroandlayersmanager")) {
                                                emailBuilder.append("\nTheme Engine: Not Available");
                                            } else {
                                                emailBuilder.append("\nTheme Engine: Layers Manager (RRO)");
                                            }
                                        } else {
                                            emailBuilder.append("\nTheme Engine: Cyanogen OS Theme Engine");
                                        }
                                    } else {
                                        emailBuilder.append("\nTheme Engine: CyanogenMod Theme Engine");
                                    }
                                    PackageInfo appInfo = null;
                                    try {
                                        appInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                                    } catch (PackageManager.NameNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    emailBuilder.append("\nApp Version Name: " + appInfo.versionName);
                                    emailBuilder.append("\nApp Version Code: " + appInfo.versionCode);

                                    intent.putExtra(Intent.EXTRA_TEXT, emailBuilder.toString());
                                    startActivity(Intent.createChooser(intent, (getResources().getString(R.string.send_title))));
                                    break;
                                case 7:
                                    if (Shell.SU.available()) {
                                        if (!isAppInstalled("com.nolanlawson.logcat")) {
                                            Intent logcat = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.play_store_link_logcat)));
                                            startActivity(logcat);
                                        } else {
                                            Intent intent_logcat = getPackageManager().getLaunchIntentForPackage("com.nolanlawson.logcat");
                                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.logcat_toast), Toast.LENGTH_LONG);
                                            toast.show();
                                            startActivity(intent_logcat);
                                        }
                                    } else {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Unfortunately, this feature is only available for root users.", Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                    break;
                                case 8:
                                    switchFragment(8, thaFAQ, "FAQ");
                                    break;
                                case 9:
                                    switchFragment(9, thaHelp, "Help");
                                    break;
                                case 10:
                                    switchFragment(10, thaAbout, "About");
                                    break;
                                case 11:
                                    Intent launch_whicons = new Intent("android.intent.action.MAIN");
                                    launch_whicons.setComponent(new ComponentName("org.cyanogenmod.theme.chooser", "org.cyanogenmod.theme.chooser.ChooserActivity"));
                                    launch_whicons.putExtra("pkgName", "com.whicons.iconpack");
                                    Intent devPlay = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.play_store_whicons)));
                                    Intent intent_whicons = getPackageManager().getLaunchIntentForPackage("com.whicons.iconpack");
                                    if (intent_whicons == null) {
                                        startActivity(devPlay);
                                    } else {
                                        if (isAppInstalled("org.cyanogenmod.theme.chooser")) {
                                            startActivity(launch_whicons);
                                        } else {
                                            startActivity(intent_whicons);
                                        }
                                    }
                                    break;
                                case 12:
                                    fullchangelog();
                                    break;
                                case 13:
                                    if (!isAppInstalled("org.cyanogenmod.theme.chooser")) {
                                        if (isAppInstalled("com.lovejoy777.rroandlayersmanager")) {
                                            if (Shell.SU.available()) {
                                                AssetManager assetManager = getAssets();
                                                InputStream in = null;
                                                OutputStream out = null;
                                                try {
                                                    File bootanimDirectory = new File(Environment.getExternalStorageDirectory() + "/DarkMaterial/BootAnimation/");
                                                    bootanimDirectory.mkdirs();
                                                    in = assetManager.open("bootanimation/" + "bootanimation.zip");
                                                    out = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/DarkMaterial/BootAnimation/" + "bootanimation.zip");
                                                    copyFile(in, out);
                                                    out.flush();
                                                    out.close();
                                                    in.close();
                                                    in = null;
                                                    out = null;
                                                } catch (Exception e) {
                                                    Log.e("FileNotFoundException", "Transfer of boot animation zip file from assets folder has failed.", e);
                                                }
                                                String[] commands_mount = {"mount -o remount,rw /system"};
                                                String[] commands_append = {"mv /data/media/0/DarkMaterial/BootAnimation/bootanimation.zip /system/media/bootanimation.zip"};
                                                String[] commands_setperms = {"chmod 644 /system/media/bootanimation.zip"};
                                                String[] commands_close = {"mount -o remount,ro /system"};
                                                RunAsRoot(commands_mount);
                                                Toast toast = Toast.makeText(getApplicationContext(), "Mounting system as R/W", Toast.LENGTH_SHORT);
                                                toast.show();
                                                RunAsRoot(commands_append);
                                                Toast toast1 = Toast.makeText(getApplicationContext(), "Moving from APK file to /system", Toast.LENGTH_SHORT);
                                                toast1.show();
                                                RunAsRoot(commands_setperms);
                                                Toast toast2 = Toast.makeText(getApplicationContext(), "Setting permissions", Toast.LENGTH_SHORT);
                                                toast2.show();
                                                RunAsRoot(commands_setperms);
                                                Toast toast3 = Toast.makeText(getApplicationContext(), "Ensuring permissions correctly set", Toast.LENGTH_SHORT);
                                                toast3.show();
                                                RunAsRoot(commands_close);
                                                Toast toast4 = Toast.makeText(getApplicationContext(), "Mounting system as R/O", Toast.LENGTH_SHORT);
                                                Toast toast5 = Toast.makeText(getApplicationContext(), "Install success!", Toast.LENGTH_SHORT);
                                                toast4.show();
                                                toast5.show();
                                            } else {
                                                Toast toast = Toast.makeText(getApplicationContext(), "Unfortunately, this feature is only available for root users.", Toast.LENGTH_LONG);
                                                toast.show();
                                            }
                                        } else {
                                            Intent intent_settings = getPackageManager().getLaunchIntentForPackage("com.android.settings");
                                            Toast toast_error = Toast.makeText(getApplicationContext(), "Not available to apply boot animation traditionally. Rerouting to Settings.", Toast.LENGTH_SHORT);
                                            toast_error.show();
                                            startActivity(intent_settings);
                                        }
                                    } else {
                                        Toast toast_error_cm = Toast.makeText(getApplicationContext(), "Since your device isn't a Layers based device, you have been rerouted to CM Theme Chooser.", Toast.LENGTH_LONG);
                                        toast_error_cm.show();
                                        Intent launch_cm_te = new Intent("android.intent.action.MAIN");
                                        launch_cm_te.setComponent(new ComponentName("org.cyanogenmod.theme.chooser", "org.cyanogenmod.theme.chooser.ChooserActivity"));
                                        launch_cm_te.putExtra("pkgName", "com.chummy.jezebel.material.dark");
                                        startActivity(launch_cm_te);
                                    }
                                    break;
                                case 14:
                                    if (!isAppInstalled("org.cyanogenmod.theme.chooser")) {
                                        if (isAppInstalled("com.lovejoy777.rroandlayersmanager")) {
                                            if (Shell.SU.available()) {
                                                String[] commands_mount2 = {"mount -o remount,rw /system"};
                                                String[] commands_append2 = {"cp /data/media/0/DarkMaterial/BootAnimation/original_bootanimation.zip /system/media/bootanimation.zip"};
                                                String[] commands_setperms2 = {"chmod 644 /system/media/bootanimation.zip"};
                                                String[] commands_close2 = {"mount -o remount,ro /system"};
                                                File file = new File("/storage/emulated/0/DarkMaterial/BootAnimation/original_bootanimation.zip");
                                                if (file.exists()) {
                                                    RunAsRoot(commands_mount2);
                                                    Toast toast6 = Toast.makeText(getApplicationContext(), "Mounting system as R/W", Toast.LENGTH_SHORT);
                                                    toast6.show();
                                                    RunAsRoot(commands_append2);
                                                    Toast toast7 = Toast.makeText(getApplicationContext(), "Moving backup from internal storage to /system", Toast.LENGTH_SHORT);
                                                    toast7.show();
                                                    RunAsRoot(commands_setperms2);
                                                    Toast toast8 = Toast.makeText(getApplicationContext(), "Setting permissions", Toast.LENGTH_SHORT);
                                                    toast8.show();
                                                    RunAsRoot(commands_setperms2);
                                                    Toast toast9 = Toast.makeText(getApplicationContext(), "Ensuring permissions correctly set", Toast.LENGTH_SHORT);
                                                    toast9.show();
                                                    RunAsRoot(commands_close2);
                                                    Toast toast10 = Toast.makeText(getApplicationContext(), "Mounting system as R/O", Toast.LENGTH_SHORT);
                                                    Toast toast11 = Toast.makeText(getApplicationContext(), "Restore success!", Toast.LENGTH_SHORT);
                                                    toast10.show();
                                                    toast11.show();
                                                } else {
                                                    Log.e("FileNotFoundException", "Original bootanimation backup file not found in '/storage/emulated/0/DarkMaterial/BootAnimation' directory.");
                                                    Toast toast12 = Toast.makeText(getApplicationContext(), "No backup found in '/storage/emulated/0/DarkMaterial/BootAnimation'", Toast.LENGTH_LONG);
                                                    toast12.show();
                                                }
                                            } else {
                                                Toast toast = Toast.makeText(getApplicationContext(), "Unfortunately, this feature is only available for root users.", Toast.LENGTH_LONG);
                                                toast.show();
                                            }
                                        } else {
                                            Intent intent_settings = getPackageManager().getLaunchIntentForPackage("com.android.settings");
                                            Toast toast_error = Toast.makeText(getApplicationContext(), "Not available to apply boot animation traditionally. Rerouting to Settings.", Toast.LENGTH_SHORT);
                                            toast_error.show();
                                            startActivity(intent_settings);
                                        }
                                    } else {
                                        Toast toast_error_cm = Toast.makeText(getApplicationContext(), "Since your device isn't a Layers based device, you have been rerouted to CM Theme Chooser.", Toast.LENGTH_LONG);
                                        toast_error_cm.show();
                                        Intent launch_cm_te = new Intent("android.intent.action.MAIN");
                                        launch_cm_te.setComponent(new ComponentName("org.cyanogenmod.theme.chooser", "org.cyanogenmod.theme.chooser.ChooserActivity"));
                                        launch_cm_te.putExtra("pkgName", "com.chummy.jezebel.material.dark");
                                        startActivity(launch_cm_te);
                                    }
                                    break;
                                case 15:
                                    if (!isAppInstalled("org.cyanogenmod.theme.chooser")) {
                                        if (isAppInstalled("com.lovejoy777.rroandlayersmanager")) {
                                            if (Shell.SU.available()) {
                                                String[] commands_mount3 = {"mount -o remount,rw /system"};
                                                String[] commands_backup = {"cp /system/media/bootanimation.zip /data/media/0/DarkMaterial/BootAnimation/original_bootanimation.zip"};
                                                String[] commands_close3 = {"mount -o remount,ro /system"};
                                                RunAsRoot(commands_mount3);
                                                Toast toast13 = Toast.makeText(getApplicationContext(), "Mounting system as R/W", Toast.LENGTH_SHORT);
                                                toast13.show();
                                                RunAsRoot(commands_backup);
                                                Toast toast_14 = Toast.makeText(getApplicationContext(), "Backing up current boot animation to '/storage/emulated/0/DarkMaterial/BootAnimation'", Toast.LENGTH_SHORT);
                                                toast_14.show();
                                                RunAsRoot(commands_close3);
                                                Toast toast15 = Toast.makeText(getApplicationContext(), "Mounting system as R/O", Toast.LENGTH_SHORT);
                                                Toast toast16 = Toast.makeText(getApplicationContext(), "Backup success!", Toast.LENGTH_SHORT);
                                                toast15.show();
                                                toast16.show();
                                            } else {
                                                Toast toast17 = Toast.makeText(getApplicationContext(), "Unfortunately, this feature is only available for root users.", Toast.LENGTH_LONG);
                                                toast17.show();
                                            }
                                        } else {
                                            Intent intent_settings = getPackageManager().getLaunchIntentForPackage("com.android.settings");
                                            Toast toast_error = Toast.makeText(getApplicationContext(), "Not available to apply boot animation traditionally. Rerouting to Settings.", Toast.LENGTH_SHORT);
                                            toast_error.show();
                                            startActivity(intent_settings);
                                        }
                                    } else {
                                        Toast toast_error_cm = Toast.makeText(getApplicationContext(), "Since your device isn't a Layers based device, you have been rerouted to CM Theme Chooser.", Toast.LENGTH_LONG);
                                        toast_error_cm.show();
                                        Intent launch_cm_te = new Intent("android.intent.action.MAIN");
                                        launch_cm_te.setComponent(new ComponentName("org.cyanogenmod.theme.chooser", "org.cyanogenmod.theme.chooser.ChooserActivity"));
                                        launch_cm_te.putExtra("pkgName", "com.chummy.jezebel.material.dark");
                                        startActivity(launch_cm_te);
                                    }
                                    break;
                            }
                        }
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        result.getListView().setVerticalScrollBarEnabled(false);

        runLicenseChecker();

        if (savedInstanceState == null) {
            result.setSelectionByIdentifier(1);
        }

    }

    private void copyAssets() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) for (String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                File outFile = new File(getExternalFilesDir(null), filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch (IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        Log.e("tag", "Could not close in", e);
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        Log.e("tag", "Could not close out", e);
                    }
                }
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public void RunAsRoot(String[] cmds) {
        try {
            Process p = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            for (String tmpCmd : cmds) {
                os.writeBytes(tmpCmd + "\n");
            }
            os.writeBytes("exit\n");
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void switchFragment(int itemId, String title, String fragment) {
        currentItem = itemId;
        getSupportActionBar().setTitle(title);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        tx.replace(R.id.main, Fragment.instantiate(Main.this, "com.chummy.jezebel.material.dark.fragments." + fragment));
        tx.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else if (result != null && currentItem != 1) {
            result.setSelection(0);
        } else if (result != null) {
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private boolean isAppInstalled(String uri) {
        PackageManager pm = getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Check out " + getResources().getString(R.string.theme_name) + " by " + getResources().getString(R.string.nicholas_short) + "!\n\nDownload it here!: " + getResources().getString(R.string.play_store_link);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, (getResources().getString(R.string.share_title))));
                break;

            case R.id.sendemail:
                StringBuilder emailBuilder = new StringBuilder();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + getResources().getString(R.string.email_id)));
                if (!isAppInstalled("org.cyanogenmod.theme.chooser")) {
                    if (!isAppInstalled("com.cyngn.theme.chooser")) {
                        if (!isAppInstalled("com.lovejoy777.rroandlayersmanager")) {
                            intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.email_subject));
                        } else {
                            intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.email_subject_rro));
                        }
                    } else {
                        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.email_subject_cos));
                    }
                } else {
                    intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.email_subject_cm));
                }
                emailBuilder.append("\n \n \nOS Version: " + System.getProperty("os.version") + "(" + Build.VERSION.INCREMENTAL + ")");
                emailBuilder.append("\nOS API Level: " + Build.VERSION.SDK_INT + " (" + Build.VERSION.RELEASE + ") " + "[" + Build.ID + "]");
                emailBuilder.append("\nDevice: " + Build.DEVICE);
                emailBuilder.append("\nManufacturer: " + Build.MANUFACTURER);
                emailBuilder.append("\nModel (and Product): " + Build.MODEL + " (" + Build.PRODUCT + ")");
                if (!isAppInstalled("org.cyanogenmod.theme.chooser")) {
                    if (!isAppInstalled("com.cyngn.theme.chooser")) {
                        if (!isAppInstalled("com.lovejoy777.rroandlayersmanager")) {
                            emailBuilder.append("\nTheme Engine: Not Available");
                        } else {
                            emailBuilder.append("\nTheme Engine: Layers Manager (RRO)");
                        }
                    } else {
                        emailBuilder.append("\nTheme Engine: Cyanogen OS Theme Engine");
                    }
                } else {
                    emailBuilder.append("\nTheme Engine: CyanogenMod Theme Engine");
                }
                PackageInfo appInfo = null;
                try {
                    appInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                emailBuilder.append("\nApp Version Name: " + appInfo.versionName);
                emailBuilder.append("\nApp Version Code: " + appInfo.versionCode);

                intent.putExtra(Intent.EXTRA_TEXT, emailBuilder.toString());
                startActivity(Intent.createChooser(intent, (getResources().getString(R.string.send_title))));
                break;

            case R.id.changelog:
                changelog();
                break;
        }
        return true;
    }

    public void addItemsToDrawer() {
        IDrawerItem walls = new PrimaryDrawerItem().withName(thaWalls).withIcon(GoogleMaterial.Icon.gmd_landscape).withDescription("Online wallpaper collection.").withIdentifier(2);
        /*IDrawerItem request = new PrimaryDrawerItem().withName(thaRequest).withIcon(GoogleMaterial.Icon.gmd_forum).withIdentifier(5);*/
        if (enable_features) {
            result.addItem(walls, 2);
            /*result.addItem(request, 4);*/
        }
    }

    private void runLicenseChecker() {
        if (firstrun) {
            if (withLicenseChecker) {
                checkLicense();
            } else {
                mPrefs.setFeaturesEnabled(true);
                addItemsToDrawer();
                showChangelogDialog();
            }
        } else {
            if (withLicenseChecker) {
                if (!enable_features) {
                    showNotLicensedDialog();
                } else {
                    addItemsToDrawer();
                    showChangelogDialog();
                }
            } else {
                addItemsToDrawer();
                showChangelogDialog();
            }
        }
    }

    private void changelog() {

        new MaterialDialog.Builder(this)
                .title(R.string.changelog_dialog_title)
                .adapter(new ChangelogAdapter(this, R.array.newchangelog), null)
                .positiveText(R.string.nice)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        mPrefs.setNotFirstrun();
                    }
                })
                .show();
    }

    private void fullchangelog() {

        new MaterialDialog.Builder(this)
                .title(R.string.section_sixteen)
                .adapter(new ChangelogAdapter(this, R.array.fullchangelog), null)
                .positiveText(R.string.nice)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        mPrefs.setNotFirstrun();
                    }
                })
                .show();
    }

    private void showChangelogDialog() {

        String launchinfo = getSharedPreferences("PrefsFile", MODE_PRIVATE).getString("version", "0");
        if (!launchinfo.equals(getResources().getString(R.string.version_code))) {
            changelog();
        }
        storeSharedPrefs();
    }

    protected void storeSharedPrefs() {
        SharedPreferences sharedPreferences = getSharedPreferences("PrefsFile", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("version", getResources().getString(R.string.version_code));
        editor.commit();
    }

    private void showNotConnectedDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.no_conn_title)
                .content(R.string.no_conn_content)
                .positiveText(R.string.ok)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

                        int nSelection = currentItem - 1;
                        if (result != null) {
                            result.setSelection(nSelection);
                        }

                    }
                })
                .show();
    }

    public void checkLicense() {
        String installer = getPackageManager().getInstallerPackageName(getPackageName());
        try {
            if (installer.equals("com.google.android.feedback") ||
                    installer.equals("com.android.vending")) {
                new MaterialDialog.Builder(this)
                        .title(R.string.license_success_title)
                        .content(R.string.license_success)
                        .positiveText(R.string.close)
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                enable_features = true;
                                mPrefs.setFeaturesEnabled(true);
                                addItemsToDrawer();
                                showChangelogDialog();
                            }
                        })
                        .show();
            } else {
                showNotLicensedDialog();
            }
        } catch (Exception e) {
            showNotLicensedDialog();
        }
    }

    private void showNotLicensedDialog() {
        enable_features = false;
        mPrefs.setFeaturesEnabled(false);
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.license_failed_title)
                .content(R.string.license_failed)
                .positiveText(R.string.download)
                .negativeText(R.string.exit)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.play_store_link)));
                        startActivity(browserIntent);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        finish();
                    }
                })
                .show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
    }


}