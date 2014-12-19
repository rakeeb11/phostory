package com.yipl.phostory.service;

import android.app.Notification;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.graphics.Bitmap;
import android.os.PersistableBundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import static com.yipl.phostory.utils.Constants.IMAGES;
import static com.yipl.phostory.utils.Utils.randInt;

import com.yipl.phostory.R;
import com.yipl.phostory.utils.Constants;
import com.yipl.phostory.utils.Utils;

/**
 * Created by rakeeb on 12/19/14.
 */
public class NotificationJobService extends JobService {

    NotificationManagerCompat notificationManager;
    Notification privateNotification;
    Notification publicNotification;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        int id = jobParameters.getJobId();
        PersistableBundle persistableBundle = jobParameters.getExtras();
        String message = persistableBundle.getString("message");
        if (id == Constants.JOB_ID) {
            NotificationCompat.Builder publicBuilder = new NotificationCompat.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setLargeIcon(getRandomBitmap())
                    .setColor(R.attr.colorAccent)
                    .setContentTitle("Update")
                    .setContentText("You have new notification");
            publicNotification = publicBuilder.build();
            NotificationCompat.Builder privateBuilder = new NotificationCompat.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setLargeIcon(getRandomBitmap())
                    .setColor(R.attr.colorAccent)
                    .setContentTitle("Update")
                    .setContentText(message)
                    .setPublicVersion(publicNotification);
            privateNotification = privateBuilder.build();

            notificationManager = NotificationManagerCompat.from(getApplicationContext());
            notificationManager.notify(0, privateBuilder.build());
        }
        jobFinished(jobParameters, false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        notificationManager.cancelAll();
        return false;
    }

    public Bitmap getRandomBitmap() {
        return Utils.Image.getScaledDownBitmap(getResources(), 4, IMAGES[randInt(0, 14)]);
    }
}
