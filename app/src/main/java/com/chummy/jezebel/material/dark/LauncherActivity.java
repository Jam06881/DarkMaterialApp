package com.chummy.jezebel.material.dark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, com.chummy.jezebel.material.dark.MainActivity.class);
        startActivity(intent);
        finish();
    }
}

