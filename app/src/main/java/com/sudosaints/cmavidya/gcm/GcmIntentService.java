package com.sudosaints.cmavidya.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/*import com.google.android.gms.gcm.GoogleCloudMessaging;*/

/**
 * Created by Akshay on 18/6/15.
 */
public class GcmIntentService/* extends IntentService*/ {
   /* public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle=intent.getExtras();
        GoogleCloudMessaging gcm=GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!bundle.isEmpty())
        {
            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {

            }
        }


    }*/
}
