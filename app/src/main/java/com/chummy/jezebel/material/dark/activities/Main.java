package com.chummy.jezebel.material.dark.activities;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chummy.jezebel.material.dark.AsyncResponse;
import com.chummy.jezebel.material.dark.MainActivity;
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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;

import com.azeesoft.lib.colorpicker.ColorPickerDialog;

import eu.chainfire.libsuperuser.Shell;

public class Main extends ActionBarActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String[] aaptUrls = {
            "https://www.dropbox.com/s/au7ccu1gtroqvzt/aapt_x86?dl=1",
            "https://www.dropbox.com/s/5x2fpgw6ojyao2d/aapt_arm?dl=1"};

    private static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    public Drawer.Result result = null;
    public AccountHeader.Result headerResult = null;
    public String thaApp, thaHome, thaPreviews, thaApply, thaWalls, thaRequest, thaCredits, thaTesters, thaWhatIsThemed, thaContactUs, thaLogcat, thaFAQ, thaHelp, thaAbout, thaIconPack, thaFullChangelog, thaRebuild;
    public String version, drawerVersion;
    public int currentItem;
    private boolean firstrun, enable_features;
    private Preferences mPrefs;
    private boolean withLicenseChecker = false;
    public Context context;
    public String color_picked;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
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
        thaRebuild = getResources().getString(R.string.section_seventeen);

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
                .withDrawerWidthDp(400)
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
                        new SectionDrawerItem().withName("Tools & Utilities"),
                        new PrimaryDrawerItem().withName(thaRebuild).withIcon(GoogleMaterial.Icon.gmd_sync).withDescription("A rebuild a day keeps the RRs away!").withCheckable(false).withBadge("ROOT ★").withIdentifier(13),
                        new PrimaryDrawerItem().withName(thaLogcat).withIcon(GoogleMaterial.Icon.gmd_bug_report).withDescription("System level log recording.").withCheckable(false).withBadge("ROOT ★").withIdentifier(7),
                        new DividerDrawerItem(),
                        new SectionDrawerItem().withName("The Developers"),
                        new PrimaryDrawerItem().withName(thaCredits).withIcon(GoogleMaterial.Icon.gmd_people).withDescription("chummy development team").withIdentifier(5),
                        new PrimaryDrawerItem().withName(thaTesters).withIcon(GoogleMaterial.Icon.gmd_star).withDescription("The people who keep the team updated.").withIdentifier(4),
                        new DividerDrawerItem(),
                        new SectionDrawerItem().withName("Contact"),
                        new SecondaryDrawerItem().withName(thaContactUs).withIcon(GoogleMaterial.Icon.gmd_mail).withCheckable(true).withIdentifier(6),
                        new SecondaryDrawerItem().withName(thaHelp).withIcon(GoogleMaterial.Icon.gmd_help).withCheckable(true).withIdentifier(9),
                        new SecondaryDrawerItem().withName("TEST COMPILE").withIcon(GoogleMaterial.Icon.gmd_query_builder).withCheckable(true).withIdentifier(14)
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
                                        if (!isAppInstalled("com.tooleap.logcat")) {
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
                                            Intent intent_tooleap = getPackageManager().getLaunchIntentForPackage("com.tooleap.logcat");
                                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.logcat_toast), Toast.LENGTH_LONG);
                                            toast.show();
                                            startActivity(intent_tooleap);
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
                                    Intent launch_whicons_cos = new Intent("android.intent.action.MAIN");
                                    launch_whicons_cos.setComponent(new ComponentName("com.cyngn.theme.chooser", "com.cyngn.theme.chooser.ChooserActivity"));
                                    launch_whicons_cos.putExtra("pkgName", "com.whicons.iconpack");
                                    Intent devPlay = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.play_store_whicons)));
                                    Intent intent_whicons = getPackageManager().getLaunchIntentForPackage("com.whicons.iconpack");
                                    if (intent_whicons == null) {
                                        startActivity(devPlay);
                                    } else {
                                        if (isAppInstalled("org.cyanogenmod.theme.chooser")) {
                                            startActivity(launch_whicons);
                                        } else {
                                            if (isAppInstalled("com.cyngn.theme.chooser")) {
                                                Toast toast = Toast.makeText(getApplicationContext(), "Select Dark Material, click Customize and locate Default Icons. Then select Whicons and click Apply.", Toast.LENGTH_LONG);
                                                toast.show();
                                                startActivity(launch_whicons_cos);
                                            } else {
                                                startActivity(intent_whicons);
                                            }
                                        }
                                    }
                                    break;
                                case 12:
                                    fullchangelog();
                                    break;
                                case 13:
                                    if (Shell.SU.available()) {
                                        rebuildThemeCache();
                                    } else {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Unfortunately, this feature is only available for root users.", Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                    break;
                                case 14:
                                    pickColor();
                                    break;
                            }
                        }
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        result.getListView().setVerticalScrollBarEnabled(false);

        // Check for permissions first so that we don't have any issues down the road
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            // permission already granted, allow the program to continue running
            runLicenseChecker();
            createTempFolder();
        } else {
            // permission not granted, request it from the user
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
        if (savedInstanceState == null) {
            result.setSelectionByIdentifier(1);
        }

    }
    public void pickColor(){

        final ColorPickerDialog colorPickerDialog = ColorPickerDialog.createColorPickerDialog(Main.this, ColorPickerDialog.DARK_THEME);
        colorPickerDialog.setHexaDecimalTextColor(Color.parseColor("#ffffff"));
        colorPickerDialog.hideOpacityBar(); // wtf FUCK THE README
        colorPickerDialog.setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
            @Override
            public void onColorPicked(int color, String hexVal) {
                color_picked = colorPickerDialog.getCurrentColorAsHexa();
                createXMLfile();
            }
        });
        colorPickerDialog.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission already granted, allow the program to continue running
                    runLicenseChecker();
                } else {
                    // permission was not granted, show closing dialog
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.permission_not_granted_dialog_title)
                            .setMessage(R.string.permission_not_granted_dialog_message)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .show();
                    return;
                }
                break;
            }
        }
    }

    private void createXMLfile() {
        try{
            File root = new File(getFilesDir(), "/res/values-v23/cdt_colors.xml");
            if (!root.exists()) {
                root.createNewFile();
            }
            FileWriter fw = new FileWriter(root);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String xmlTags = ("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "\n");
            String xmlRes1 = ("<resources>" + "\n");
            String xmlFile = ("    <color name="+ "\"" + "theme_color_accent" + "\">"  + color_picked + "</color>" + "\n");
            String xmlRes2 = ("</resources>");
            pw.write(xmlTags);
            pw.write(xmlRes1);
            pw.write(xmlFile);
            pw.write(xmlRes2);
            pw.close();
            bw.close();
            fw.close();
            try {
                compileAndSign();
            } catch (Exception e) {
                //
            }
        } catch (IOException e) {
            Log.e("FileWriter Error", "Failed to create new file.");
        }

    }

    private void createTempFolder() {
        copyAssetFolder(getAssets(), "aapt",

                getFilesDir().toString());
    }

    private static boolean copyAssetFolder(AssetManager assetManager,
                                           String fromAssetPath, String toPath) {
        try {
            String[] files = assetManager.list(fromAssetPath);
            new File(toPath).mkdirs();
            boolean res = true;
            for (String file : files) {
                 if (file.contains(".")) {
                    res &= copyAsset(assetManager,
                            fromAssetPath + "/" + file,
                            toPath + "/" + file);
                } if (file.contains("aapt")) {
                    res &= copyAsset(assetManager,
                            fromAssetPath + "/" + file,
                            toPath + "/" + file);
                } else {
                    res &= copyAssetFolder(assetManager,
                            fromAssetPath + "/" + file,
                            toPath + "/" + file);
                }
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean copyAsset(AssetManager assetManager,
                                     String fromAssetPath, String toPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(fromAssetPath);
            new File(toPath).createNewFile();
            out = new FileOutputStream(toPath);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            return true;
        } catch(Exception e) {
            Log.e("TAG", "ERROR, CANNOT PUSH FILES");
            e.printStackTrace();
            return false;
        }
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    private void deleteTempFolder() {
        File dir = getFilesDir();
        deleteRecursive(dir);
    }

    private void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
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
    protected void onDestroy() {
        super.onDestroy();
        try {
            deleteTempFolder();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
            result.setSelection(1);
        } else if (result != null) {
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.hide_launcher).setChecked(!isLauncherIconEnabled());
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

            case R.id.hide_launcher:
                boolean checked = item.isChecked();
                if (!checked) {
                    new MaterialDialog.Builder(this)
                            .title(R.string.warning)
                            .content(R.string.hide_action)
                            .positiveText(R.string.nice)
                            .show();
                }
                item.setChecked(!checked);
                setLauncherIconEnabled(checked);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public boolean isLauncherIconEnabled() {
        PackageManager pm = getPackageManager();
        return (pm.getComponentEnabledSetting(new ComponentName(this, com.chummy.jezebel.material.dark.LauncherActivity.class)) != PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
    }

    public void setLauncherIconEnabled(boolean enabled) {
        int newState;
        PackageManager pm = getPackageManager();
        if (enabled) {
            newState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        } else {
            newState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        }
        pm.setComponentEnabledSetting(new ComponentName(this, com.chummy.jezebel.material.dark.LauncherActivity.class), newState, PackageManager.DONT_KILL_APP);
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

    private void rebuildThemeCache() {

        new MaterialDialog.Builder(this)
                .title("Rebuild Theme Engine Cache")
                .content(getResources().getString(R.string.rebuild_disclaimer))
                .positiveText("PROCEED")
                .negativeText("CANCEL")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        String[] commands_mount3 = {"mount -o remount,rw /system"};
                        String[] commands_backup = {"rm -r /data/resource-cache"};
                        String[] commands_close3 = {"mount -o remount,ro /system"};
                        RunAsRoot(commands_mount3);
                        Toast toast13 = Toast.makeText(getApplicationContext(), "Mounting system as R/W", Toast.LENGTH_SHORT);
                        toast13.show();
                        RunAsRoot(commands_backup);
                        Toast toast_14 = Toast.makeText(getApplicationContext(), "Destroying all theme caches made by CM Theme Engine...", Toast.LENGTH_SHORT);
                        toast_14.show();
                        RunAsRoot(commands_close3);
                        Toast toast15 = Toast.makeText(getApplicationContext(), "Mounting system as R/O", Toast.LENGTH_SHORT);
                        Toast toast16 = Toast.makeText(getApplicationContext(), "All caches cleared!", Toast.LENGTH_SHORT);
                        toast15.show();
                        toast16.show();
                        showThreadCloserDialogReboot();
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

    public void showThreadCloserDialogReboot() {
        new MaterialDialog.Builder(context)
                .title("A reboot is necessary...")
                .content("Please wait for all the toasts to completely vanish from the bottom of your screen, then click Proceed.")
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

    private void setPermissionsAAPT() throws IOException {
        String command = new String ("chmod 777 /data/data/com.chummy.jezebel.material.dark/files/aapt");
        Process nativeApp = Runtime.getRuntime().exec(command);

    }

    private void compileAndSign() throws Exception {

        setPermissionsAAPT();
        Log.e("STEP 5.3", "PASS");

        File aapt = new File(context.getFilesDir(), "/aapt");
        Log.e("STEP 5.5", aapt.getAbsolutePath().toString());
        String commands = new String ("cd /data/data/com.chummy.jezebel.material.dark/files/\n" +
                "aapt p -M /data/data/com.chummy.jezebel.material.dark/files/AndroidManifest.xml -S /data/data/com.chummy.jezebel.material.dark/files/res -I /data/data/com.chummy.jezebel.material.dark/files/builder.jar -F /data/data/com.chummy.jezebel.material.dark/files/common-resources.apk\n");
        Log.e("STEP 5.7", "PASS");

        Process nativeApp = Runtime.getRuntime().exec(commands);
        IOUtils.toString(nativeApp.getInputStream());
        IOUtils.toString(nativeApp.getErrorStream());

        nativeApp.waitFor();

        Log.e("STEP 6", "PASS");

        /*
        IOUtils.toString(nativeApp.getInputStream());
        IOUtils.toString(nativeApp.getErrorStream());

        nativeApp.waitFor();
*/
        Log.d("Signing start", "");
        /*

        ZipSigner zipSigner = new ZipSigner();
        zipSigner.setKeymode("testkey");
        zipSigner.signZip(unsignedApp.getAbsolutePath(), signedApp.getAbsolutePath());
        */
        Log.d("Signing end", "");

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
    public static class InstallOverlaysBetterWay extends AsyncTask<Void, String, Void> {

        Context context;

        public InstallOverlaysBetterWay(Context context) {
            this.context = context;
        }

        @Override
        public Void doInBackground(Void... params) {
            File aapt = new File(context.getFilesDir().toString());
            if (context.getFilesDir() == null){
                Log.e("SSSSSSSSSSSSSS", "FUCKED!");
            }
            for (String url : aaptUrls) {

                try {
                    FileUtils.copyURLToFile(new URL(url), aapt);

                    Process checkAapt = Runtime.getRuntime().exec(new String[]{
                            aapt.getAbsolutePath(), "v"});

                    String data = IOUtils.toString(checkAapt.getInputStream());
                    String error = IOUtils.toString(checkAapt.getErrorStream());

                    checkAapt.waitFor();

                    if (StringUtils.isEmpty(error)) {
                        Log.d("AAPT", data);
                        break;
                    }


                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }


            }


            return null;
        }
    }

}