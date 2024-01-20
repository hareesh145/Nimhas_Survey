package com.ganesh.nimhans.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceConnector {

    public static final String PREF_NAME = "PEOPLE_PREFERENCES";
    public static final int MODE = Context.MODE_PRIVATE;
    public static final String USERID = "USERID";
    public static final String USERCODE = "USERCODE";
    public static final String SUPERVISOR = "SUPERVISOR";
    public static final String TOKEN = "TOKEN";
    public static final String GAMECATEGORYNAME = "GAMECATEGORYNAME";

    public static final String GUESTID = "GUESTID";
    public static final String CART_ID = "CART_ID";
    public static final String STORE_ID = "STORE_ID";
    public static final String CUST_ID = "CUST_ID";
    public static final String LOCATION = "LOCATION";
    public static final String LOC_ID = "LOC_ID";
    public static String LOGIN_ID="LOGIN_ID";

    public static final String TOTAL_AMOUNT = "TOTAL_AMOUNT";
    public static final String NAME = "NAME";
//    public static final String EMAIL = "EMAIL";
    public static final String MOBILE = "MOBILE";

    public static final String DISTRICT="DISTRICT";
    public static final String VILLAGE="VILLAGE";
    public static final String TALUKA="TALUKA";

    public static final String NAME_OF_RESPONDENT="NAME_OF_RESPONDENT";


    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);

    }

    public static Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static boolean readBoolean(Context context, String key, boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();
    }

    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeString(Context context, String key, String value) {

        getEditor(context).putString(key, value).apply();
    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }


    public static void writeFloat(Context context, String key, Float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    public static Float readFloat(Context context, String key, Float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }

    public static void writeLong(Context context, String key, Long value) {
        getEditor(context).putLong(key, value).commit();
    }

    public static Long readLong(Context context, String key, Long defValue) {
        return getPreferences(context).getLong(key, defValue);
    }
}
