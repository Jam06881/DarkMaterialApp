package com.chummy.dark.material.layers.fragments;

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

import com.chummy.dark.material.layers.R;

public class Credits extends Fragment {

    private Context context;


    public static Fragment newInstance(Context context) {
        Credits f = new Credits();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.section_credits, null);

        context = getActivity();

        ActionBar toolbar = ((ActionBarActivity) context).getSupportActionBar();
        toolbar.setTitle(R.string.section_seven);

        TextView web = (TextView) root.findViewById(R.id.web_button);
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent devweb = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.dev_link)));
                startActivity(devweb);
            }
        });

        TextView googleplus = (TextView) root.findViewById(R.id.gplus_button);
        googleplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent devgplus = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.dev_gplus_link)));
                startActivity(devgplus);
            }
        });

        TextView web2 = (TextView) root.findViewById(R.id.web_button_bella);
        web2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent devweb = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.dev_link_bella)));
                startActivity(devweb);
            }
        });

        TextView googleplus2 = (TextView) root.findViewById(R.id.facebook_button_bella);
        googleplus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent devgplus = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.facebook_link_bella)));
                startActivity(devgplus);
            }
        });

        TextView web3 = (TextView) root.findViewById(R.id.web_button_khizar);
        web3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent devweb = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.dev_link_khizar)));
                startActivity(devweb);
            }
        });

        TextView googleplus3 = (TextView) root.findViewById(R.id.gplus_button_khizar);
        googleplus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent devgplus = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.dev_gplus_link_khizar)));
                startActivity(devgplus);
            }
        });

        TextView web4 = (TextView) root.findViewById(R.id.web_button_fahad);
        web4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent devweb = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.dev_link_fahad)));
                startActivity(devweb);
            }
        });

        TextView googleplus4 = (TextView) root.findViewById(R.id.gplus_button_fahad);
        googleplus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent devgplus = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.dev_gplus_link_fahad)));
                startActivity(devgplus);
            }
        });

        TextView web5 = (TextView) root.findViewById(R.id.web_button_aditya);
        web5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent devweb = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.dev_link_aditya)));
                startActivity(devweb);
            }
        });

        TextView googleplus5 = (TextView) root.findViewById(R.id.gplus_button_aditya);
        googleplus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent devgplus = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.dev_gplus_link_aditya)));
                startActivity(devgplus);
            }
        });

        return root;
    }

}
