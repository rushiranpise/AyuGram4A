/*
 * This is the source code of Telegram for Android v. 1.3.x.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013-2018.
 */

package org.telegram.messenger;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.exteragram.messenger.ExteraConfig;

import java.util.Random;

public class NotificationsService extends Service {

    private static final String[] notifications = new String[]{
            "Don't swipe me!",
            "Letting you receive notifications…",
            "Helping you receive pushes…",
            "dontkillmyapp.com",
            "⊂(◉‿◉)つ",
            "(｡◕‿‿◕｡)",
            "¯\\_(ツ)_/¯",
            "\\(^-^)/",
            "＼(＾O＾)／",
            "ԅ(≖‿≖ԅ)"
    };

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationLoader.postInitApplication();

        if (ExteraConfig.keepAliveService && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "ayugram_push";
            NotificationChannelCompat channel = new NotificationChannelCompat.Builder(CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_DEFAULT)
                    .setName("AyuGram Push Service")
                    .setLightsEnabled(false)
                    .setVibrationEnabled(false)
                    .setSound(null, null)
                    .build();

            var funnyText = notifications[new Random().nextInt(notifications.length)];

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.createNotificationChannel(channel);
            startForeground(9999,
                    new NotificationCompat.Builder(this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.msg_premium_badge)
                            .setShowWhen(false)
                            .setOngoing(true)
                            .setContentText(funnyText)
                            .setCategory(NotificationCompat.CATEGORY_STATUS)
                            .build());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        super.onDestroy();
        SharedPreferences preferences = MessagesController.getGlobalNotificationsSettings();
        if (preferences.getBoolean("pushService", true)) {
            Intent intent = new Intent("org.telegram.start");
            sendBroadcast(intent);
        }
    }
}
