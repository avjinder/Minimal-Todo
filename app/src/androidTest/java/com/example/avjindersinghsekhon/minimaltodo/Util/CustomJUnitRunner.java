package com.example.avjindersinghsekhon.minimaltodo.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.runner.AndroidJUnitRunner;
import androidx.test.runner.lifecycle.ActivityLifecycleCallback;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.POWER_SERVICE;
import static android.view.WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
import static android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
import static android.view.WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;

public class CustomJUnitRunner extends AndroidJUnitRunner {

    @SuppressLint("MissingPermission")
    @Override public void onStart() {
        Log.d("TEST", "OnStart");

        ActivityLifecycleMonitorRegistry.getInstance().addLifecycleCallback(new ActivityLifecycleCallback() {
            @Override public void onActivityLifecycleChanged(Activity activity, Stage stage) {

                Context app = getTargetContext().getApplicationContext();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {

                    activity.setShowWhenLocked(true);
                    activity.setTurnScreenOn(true);
                    // Unlock the device so that the tests can input keystrokes.
                    KeyguardManager keyguardManager = (KeyguardManager) app.getSystemService(KEYGUARD_SERVICE);
                    assert keyguardManager != null;

                    if (keyguardManager.isKeyguardLocked()) {
                        keyguardManager.requestDismissKeyguard(
                                activity, new MinimalKeyguardDismissCallback(keyguardManager));
                    }

                } else if (stage == Stage.PRE_ON_CREATE) {
                    activity.getWindow().addFlags(FLAG_DISMISS_KEYGUARD | FLAG_SHOW_WHEN_LOCKED
                            | FLAG_TURN_SCREEN_ON | FLAG_KEEP_SCREEN_ON);
                }

                PowerManager powerManager = (PowerManager) app.getSystemService(POWER_SERVICE);
                boolean isScreenAwake = powerManager != null && powerManager.isInteractive();
                Log.d("TEST", "isScreenAwake: " + isScreenAwake);
            }
        });

        super.onStart();
    }

    public class MinimalKeyguardDismissCallback extends KeyguardManager.KeyguardDismissCallback {

        KeyguardManager mKeyguardManager;

        MinimalKeyguardDismissCallback(@NonNull KeyguardManager keyguardManager) {
            mKeyguardManager = keyguardManager;
        }

        @Override
        public void onDismissSucceeded() {
            super.onDismissSucceeded();
            boolean isPhoneLocked = mKeyguardManager.isDeviceLocked();
            Log.d("TEST", "onDismissSucceeded isPhoneLocked: " + isPhoneLocked);
        }

        @Override
        public void onDismissError() {
            Log.d("TEST", "onDismissError");
        }

        @Override
        public void onDismissCancelled() {
            Log.d("TEST", "onDismissCancelled");
            boolean isPhoneLocked = mKeyguardManager.isDeviceLocked();
            Log.d("TEST", "onDismissCancelled isPhoneLocked: " + isPhoneLocked);
        }
    }
}
