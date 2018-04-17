package com.demo.yetote.doubleprogressguarddemo;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn1, btn2;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AccountHelper.addAccount(this);
        AccountHelper.autoSync();

        btn1 = findViewById(R.id.important);
        btn2 = findViewById(R.id.normal);

        //重要通知
        String channelId = "chat";
        String channelName = "聊天消息";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        createNotificationChannel(channelId, channelName, importance);

        //一般通知
        channelId = "subscribe";
        channelName = "订阅消息";
        importance = NotificationManager.IMPORTANCE_DEFAULT;
        createNotificationChannel(channelId, channelName, importance);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);


    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) manager.createNotificationChannel(channel);

    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        switch (v.getId()) {
            case R.id.important:
                NotificationChannel channel = manager.getNotificationChannel("chat");
                if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                    Intent i = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                    i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                    i.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                    startActivity(i);
                    Toast.makeText(MainActivity.this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
                }
                Notification chat = new NotificationCompat.Builder(this, "chat")
                        .setContentTitle("收到一条聊天消息")
                        .setContentText("今天中午吃什么？")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
                        .setAutoCancel(true)
                        .build();
                manager.notify(1, chat);
                break;
            case R.id.normal:
                Notification notification = new NotificationCompat.Builder(this, "chat")
                        .setContentTitle("收到一条订阅消息")
                        .setContentText("衣服减价")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
                        .setAutoCancel(true)
                        .build();
                manager.notify(2, notification);
                break;
            default:
                break;
        }
    }
}
