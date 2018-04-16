package com.demo.yetote.doubleprogressguarddemo;

import android.accounts.Account;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AccountHelper.addAccount(this);
        AccountHelper.autoSync();
    }
}
