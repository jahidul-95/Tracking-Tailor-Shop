package com.mist.jahidulislam.toptailors;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.plus.PlusShare;

import java.net.URL;

public class ShareActivity extends AppCompatActivity {
    EditText etComment;
    Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray_orange)));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etComment=(EditText)findViewById(R.id.editTextComment);
        btnSubmit=(Button)findViewById(R.id.buttonShare);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = etComment.getText().toString();

                String appPackageName = "com.mist.jahidulislam.toptailors";
                String url = "https://play.google.com/store/apps/details?id=" + appPackageName;

               Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(android.content.Intent.EXTRA_TEXT, comment + "\n" + Uri.parse(url));
                startActivity(Intent.createChooser(share, "Share App..."));

            }
        });


    }

}
