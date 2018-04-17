package com.demo.yetote.doubleprogressguarddemo;

import android.accounts.Account;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * com.demo.yetote.doubleprogressguarddemo
 *
 * @author Swg
 * @date 2018/4/16 21:44
 */
public class SyncService extends Service {
    private SyncAdapter syncAdapter;
    private static final String TAG = "SyncService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        syncAdapter = new SyncAdapter(this, true);
    }

    class SyncAdapter extends AbstractThreadedSyncAdapter {
        public SyncAdapter(Context context, boolean autoInitialize) {
            super(context, autoInitialize);
        }

        @TargetApi(Build.VERSION_CODES.O)
        @Override
        public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Log.e(TAG, "onPerformSync: " + "拉活开始");
            NotificationChannel channel = manager.getNotificationChannel("chat");
            if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                Intent i = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                i.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                startActivity(i);
                Toast.makeText(getApplicationContext(), "请手动将通知打开", Toast.LENGTH_SHORT).show();
            }
            Notification chat = new NotificationCompat.Builder(getApplicationContext(), "chat")
                    .setContentTitle("收到拉活消息")
                    .setContentText("拉活成功...")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
                    .setAutoCancel(true)
                    .build();
            manager.notify(1, chat);

        }
    }
}
