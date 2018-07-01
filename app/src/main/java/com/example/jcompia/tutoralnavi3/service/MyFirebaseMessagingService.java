package com.example.jcompia.tutoralnavi3.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.jcompia.tutoralnavi3.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;

/**
 * Created by yongbeom on 2018. 6. 21..
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    HashMap<String, Integer> notifyIdMap = new HashMap();
    private int threadNumber = 10;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("pushMesage",remoteMessage.getNotification().getBody());
        super.onMessageReceived(remoteMessage);
    }

    protected void doNoti(RemoteMessage.Notification  notification){

        NotificationCompat.Builder mBuilder = notiBuilder(notification.getTitle(), notification.getBody());

        /*mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_VIBRATE);*/

      /*  Intent resultIntent = new Intent(this, HomeActivity.class);
        mBuilder.setContentIntent(
                getSpeckPendingIntent(resultIntent, MainActivity.class)
        )
                .setAutoCancel(true);*/

        noti(mBuilder, 1);
        //notiThread(mBuilder);
    }



    // 쓰래드를 이용해야 하는 알림의 공통함수
    private void notiThread(final NotificationCompat.Builder mBuilder) {
        final NotificationManager mNotifyManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 쓰래드로 해야 중복 알림없이 1개로 발생
        // When the loop is finished, updates the notification

        // 쓰래드로 통해 실행 됨으로 notiThread 함수는 즉시 종료 된고 쓰래드는 수행된다.
        Thread t1 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                        int id =getNotiId(Thread.currentThread().getId());

                        for (int incr = 0; incr <= 100; incr+=5) {
                            mBuilder.setProgress(100, incr, false);
                            mNotifyManager.notify(id,mBuilder.build());
                            if(incr==100){
                                mNotifyManager.cancel(id);
                            }
                            try {
                                // Sleep for 5 seconds
                                Thread.sleep(1*1000);
                                Log.d("Therad","Thread name: "+Thread.currentThread().getId());
                            } catch (InterruptedException e) {
                                Log.d("Error", "sleep failure");
                            }
                        }

                    }
                }
        );
    }


    protected NotificationCompat.Builder notiBuilder(String title,String text){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher) // 알림창에 표기 아이콘
                        .setContentTitle(title)
                        .setContentText(text)
                        .setAutoCancel(true);
        return mBuilder;
    }

    private void noti(NotificationCompat.Builder notifyBuilder, int notifyID) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(
                notifyID,
                notifyBuilder.build());
    }



    private PendingIntent getSpeckPendingIntent(Intent resultIntent, Class cls) {
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        return notifyPendingIntent;
    }


    // 확장알림내에 표현해야 되는 박스데이터
    protected NotificationCompat.InboxStyle getInBoxContent(){
        NotificationCompat.InboxStyle inBoxStyle =
                new NotificationCompat.InboxStyle();


        String[] events = new String[6];
        inBoxStyle.setBigContentTitle("Event tracker details:");



        for (int i=0; i < events.length; i++) {
            events[i]="숫자"+i;
            inBoxStyle.addLine(events[i]);
        }



        return inBoxStyle;
    }

    // 실행중인 쓰래드의 아이디와
    protected int getNotiId(Long notifyIdLong){
        String notifyId = Long.toString(notifyIdLong);

        if(notifyIdMap.get(notifyId)==null){
            threadNumber++;
            notifyIdMap.put(notifyId,threadNumber);
        }
        return notifyIdMap.get(notifyId);
    }

    protected int getNotiId(int notifyInt){
        String notifyId = Integer.toString(notifyInt);

        if(notifyIdMap.get(notifyId)==null){
            threadNumber++;
            notifyIdMap.put(notifyId,threadNumber);
        }
        return notifyIdMap.get(notifyId);
    }


}
