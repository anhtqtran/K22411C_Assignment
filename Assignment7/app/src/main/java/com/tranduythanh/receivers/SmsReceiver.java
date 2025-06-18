package com.tranduythanh.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Kiểm tra action của intent có phải là tin nhắn SMS mới không
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                try {
                    // Lỗi gốc là ở dòng này, cần ép kiểu (cast) sang Object[]
                    final Object[] pdusObj = (Object[]) bundle.get("pdus");

                    if (pdusObj != null) {
                        for (Object pdu : pdusObj) {
                            SmsMessage currentMessage;
                            // Xử lý cho các phiên bản Android khác nhau
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                String format = bundle.getString("format");
                                currentMessage = SmsMessage.createFromPdu((byte[]) pdu, format);
                            } else {
                                currentMessage = SmsMessage.createFromPdu((byte[]) pdu);
                            }

                            String senderNum = currentMessage.getDisplayOriginatingAddress();
                            String messageBody = currentMessage.getDisplayMessageBody();

                            Log.d(TAG, "Sender: " + senderNum + "; Message: " + messageBody);

                            // Hiển thị thông báo Toast
                            Toast.makeText(context, "From: " + senderNum + "\nMessage: " + messageBody, Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception in onReceive: " + e);
                }
            }
        }
    }
}
