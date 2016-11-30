package com.jared.proj2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sam on 11/30/2016.
 */
public class EncryptedBroadcastReceiver extends BroadcastReceiver {
    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent){
        Bundle extras = intent.getExtras();

        if(extras != null){
            Object[] sms = (Object[]) extras.get(SMS_BUNDLE);
            String encryptedMessage = "";

            for(int i = 0; i < sms.length; ++i){
                SmsMessage message = SmsMessage.createFromPdu((byte[]) sms[i]);

                String encryptedBody = message.getMessageBody().toString();
                String address = message.getOriginatingAddress();
                long timeMillis = message.getTimestampMillis();

                Date date = new Date(timeMillis);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
                String dateString = simpleDateFormat.format(date);

                encryptedMessage += address +" at " + "\t" + dateString + "\n";
                encryptedMessage += encryptedBody + "\n";
            }
            Toast.makeText(context, encryptedMessage, Toast.LENGTH_LONG).show();

            MessageList inst = MessageList.instance();
            inst.updateList(encryptedMessage);
        }
    }
}
