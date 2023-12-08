package com.ganesh.nimhans;

import android.app.Application;
import android.content.Context;

public class MyNimhans  extends Application {
    String userPhoneNo, walletBalance;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
//        PermissionsManager.init(this);
//        FreeChargePaymentSdk.init(this, FreechargeSdkEnvironment.SANDBOX);
    }

    public static Context getmContext() {
        return mContext;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public void setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
    }

    public String getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }
}
