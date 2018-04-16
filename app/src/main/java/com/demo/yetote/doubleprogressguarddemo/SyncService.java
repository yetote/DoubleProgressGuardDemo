package com.demo.yetote.doubleprogressguarddemo;

import android.accounts.Account;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

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

        @Override
        public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
            Log.e(TAG, "onPerformSync: " + "拉活开始");
        }
    }
}
