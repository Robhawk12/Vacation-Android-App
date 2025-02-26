package d308.robertjohnson.vacationplanner.UI;

import static d308.robertjohnson.vacationplanner.UI.MainActivity.notiId;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import d308.robertjohnson.vacationplanner.R;

public class VacationBCReceiver extends BroadcastReceiver {
    String channelId = "vacation";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getStringExtra("vackey"), Toast.LENGTH_LONG).show();
        setNotificationChannel(context, channelId);
        Notification n = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(intent.getStringExtra("vackey"))
                .setContentTitle("VACATION ALERT!").build();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notiId++, n);
    }

    private void setNotificationChannel(Context context, String CHANNEL_ID) {
        CharSequence name = "vacationchannelname";
        String description = "vacationchanneldescpription";
        int importanceSetting = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importanceSetting);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);


    }
}