package com.ayush.bitmath.Utils;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;

/**
 * Created by ayush on 27/5/17.
 */

public class RemoteConfig {
    FirebaseRemoteConfig firebaseRemoteConfig;

    void RemoteConfig(Activity activity) {
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseRemoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build());
        HashMap<String, Object> defaults = new HashMap<>();
        defaults.put("score_text", "this is a text");
        firebaseRemoteConfig.setDefaults(defaults);
       final Task<Void> fetch = firebaseRemoteConfig.fetch(0);
        fetch.addOnSuccessListener(activity, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                firebaseRemoteConfig.activateFetched();
//                Constants.AUTO_LOGIN_FALSE_CASE="D";
            }
        });
    }
}

