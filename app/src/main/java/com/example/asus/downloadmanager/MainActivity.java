package com.example.asus.downloadmanager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {
    EditText url;
    Button download;
    ProgressBar progres;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
    ActivityCompat.requestPermissions(this,
            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
            1500);
        }



        bindview();
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent downloaderservice=new Intent(MainActivity.this,downloadservice.class);
                downloaderservice.putExtra("url",url.getText().toString());
                startService(downloaderservice);
            }
        });
    }

    private void bindview() {

        url=(EditText) findViewById(R.id.url);
        download=(Button) findViewById(R.id.download);
        progres=(ProgressBar) findViewById(R.id.progres);
        result=(TextView) findViewById(R.id.result);

    }
    @Subscribe
    public void updateprogres(downloadmodel model)
    {

        progres.setProgress(model.getPercent());
        result.setText(model.getPercent()+""+"%");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
