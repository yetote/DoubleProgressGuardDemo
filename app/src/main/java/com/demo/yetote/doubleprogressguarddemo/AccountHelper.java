package com.demo.yetote.doubleprogressguarddemo;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;

/**
 * com.demo.yetote.doubleprogressguarddemo
 *
 * @author Swg
 * @date 2018/4/16 21:34
 */
public class AccountHelper {
    public static final String ACCOUNT_TYPE = "com.yetote.authenticator";
    public static final String ACCOUNT_PROVIDER="com.yetote.sync_provider";
    private static Account account;


    public static void addAccount(Context context) {
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
        if (accounts.length > 0) {
            return;
        }
        account = new Account("swg", ACCOUNT_TYPE);

        accountManager.addAccountExplicitly(account, "123", new Bundle());
    }

    public static void autoSync() {
     Account  account=new Account("swg",ACCOUNT_TYPE);
     ContentResolver.setIsSyncable(account,ACCOUNT_PROVIDER,1);
     ContentResolver.setSyncAutomatically(account,ACCOUNT_PROVIDER,true);
     ContentResolver.addPeriodicSync(account,ACCOUNT_PROVIDER,new Bundle(),1);
    }
}
