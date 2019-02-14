package com.mohit.voodoo.bipe_ems.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private static final String LOCAL_STORAGE = "drone_image";
    private static SharedPreference gardenSharedPrfs;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreference(Context context) {
        this.sharedPreferences = context.getSharedPreferences(LOCAL_STORAGE, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public static SharedPreference getsharedprefInstance(Context con) {
        if (gardenSharedPrfs == null)
            gardenSharedPrfs = new SharedPreference(con);
        return gardenSharedPrfs;
    }

    public void setLogin(boolean status) {
        editor.putBoolean("LOGIN_STATUS", status);
        editor.commit();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean("LOGIN_STATUS", false);
    }

    public void set_User_Data(String user_data) {
        editor.putString("USER_DATA", user_data);
        editor.commit();
    }

    public String get_User_Data() {
        return sharedPreferences.getString("USER_DATA", null);
    }
}
