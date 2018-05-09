package edu.kiet.www.blackbox.networking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import edu.kiet.www.blackbox.interfaces.smsInterface;
import edu.kiet.www.blackbox.models.smsPOJO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sooraj on 12/8/17.
 */


class IncomingSms extends BroadcastReceiver {
    // Get the object of SmsManager
                final SmsManager sms = SmsManager.getDefault();

            public void onReceive(final Context context, Intent intent) {
            final Bundle bundle = intent.getExtras();
            try {

            if (bundle != null) {

            final Object[] pdusObj = (Object[]) bundle.get("pdus");

            for (int i = 0; i < pdusObj.length; i++) {

            SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
            String phoneNumber = currentMessage.getDisplayOriginatingAddress();

            String senderNum = phoneNumber;
            String message = currentMessage.getDisplayMessageBody();

            //Log.e("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);
            int duration = Toast.LENGTH_LONG;
           Toast toast = Toast.makeText(context,"senderNum: "+ senderNum + ", message: " + (message.charAt(0)+"").toLowerCase(), duration);
            toast.show();
                    smsInterface smsInterface1= ServiceGenerator.createService(smsInterface.class);
                    Call<ResponseBody> call = smsInterface1.getResponse(senderNum,(message.charAt(0)+"").toLowerCase());

                    call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    Toast.makeText(context, response.body().toString(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                 //   Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                                    t.printStackTrace();
                            }
                    });

            } // end for loop
        } // bundle is null

} catch (Exception e) {
Log.e("SmsReceiver", "Exception smsReceiver" +e);

}
}
}