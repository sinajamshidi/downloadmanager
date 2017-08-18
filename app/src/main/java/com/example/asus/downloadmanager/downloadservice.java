package com.example.asus.downloadmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Asus on 8/19/2017.
 */


public class downloadservice extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {

        String url=intent.getStringExtra("url");

        download(url);

        return super.onStartCommand(intent, flags, startId);
    }

    private void download(final String url) {

        AsyncHttpClient client=new AsyncHttpClient();
        client.get(url, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                int percent=(int) ((bytesWritten *100.0f)/totalSize);

                downloadmodel model=new downloadmodel();
                model.setName(url);
                model.setPercent(percent);

                EventBus.getDefault().post(model);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(downloadservice.this, "error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                String path=file.getAbsolutePath();
                Toast.makeText(downloadservice.this,"your file saved in:"+""+ path, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
