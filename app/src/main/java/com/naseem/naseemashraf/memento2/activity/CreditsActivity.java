package com.naseem.naseemashraf.memento2.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.naseem.naseemashraf.memento2.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }

    public void linkedin(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/naseemashraf/"));
        startActivity(browserIntent);
    }

    public void gitrepo(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/t8dgzb"));
        startActivity(browserIntent);
    }

}
