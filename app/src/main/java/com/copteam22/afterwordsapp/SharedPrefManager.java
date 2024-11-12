// src/main/java/com/copteam22/afterwordsapp/utils/SharedPrefManager.java

package com.copteam22.afterwordsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.copteam22.afterwordsapp.User;
import com.copteam22.afterwordsapp.LoginActivity;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private static final String KEY_ID = "key_id";
    private static final String KEY_FIRST_NAME = "key_first_name";
    private static final String KEY_LAST_NAME = "key_last_name";
    private static final String KEY_USERNAME = "key_username";
    private static final String KEY_EMAIL = "key_email";
    private static final String KEY_CHECKIN_FREQ = "key_checkin_freq";
    private static final String KEY_VERIFIED = "key_verified";
    private static final String KEY_DECEASED = "key_deceased";
    private static final String KEY_CREATED_AT = "key_created_at";
    private static final String KEY_LAST_LOGIN = "key_last_login";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context context){
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void saveUser(User user){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_FIRST_NAME, user.getFirstName());
        editor.putString(KEY_LAST_NAME, user.getLastName());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putInt(KEY_CHECKIN_FREQ, user.getCheckInFreq());
        editor.putBoolean(KEY_VERIFIED, user.isVerified());
        editor.putBoolean(KEY_DECEASED, user.isDeceased());
        editor.putString(KEY_CREATED_AT, user.getCreatedAt());
        editor.putString(KEY_LAST_LOGIN, user.getLastLogin());

        editor.apply();
    }

    public User getUser(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getInt(KEY_ID, -1) != -1){
            User user = new User();
            user.setId(sharedPreferences.getInt(KEY_ID, -1));
            user.setFirstName(sharedPreferences.getString(KEY_FIRST_NAME, null));
            user.setLastName(sharedPreferences.getString(KEY_LAST_NAME, null));
            user.setUsername(sharedPreferences.getString(KEY_USERNAME, null));
            user.setEmail(sharedPreferences.getString(KEY_EMAIL, null));
            user.setCheckInFreq(sharedPreferences.getInt(KEY_CHECKIN_FREQ, 0));
            user.setVerified(sharedPreferences.getBoolean(KEY_VERIFIED, false));
            user.setDeceased(sharedPreferences.getBoolean(KEY_DECEASED, false));
            user.setCreatedAt(sharedPreferences.getString(KEY_CREATED_AT, null));
            user.setLastLogin(sharedPreferences.getString(KEY_LAST_LOGIN, null));

            return user;
        }

        return null;
    }

    public void logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(mCtx, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);
    }
}
