package com.chummy.darkmaterial_layers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, com.chummy.darkmaterial_layers.activities.Main.class);
        startActivity(intent);
    }
}
