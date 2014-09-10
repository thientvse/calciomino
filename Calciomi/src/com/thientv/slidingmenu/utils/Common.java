package com.thientv.slidingmenu.utils;

import java.util.Hashtable;

import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

import com.thientv.calciomino.MainActivity;
import com.thientv.calciomino.R;

public class Common {
	
	// Dung cho google cloud message
	
	public static final String PROJECT_NUMBER 	= "332773246133";
	
	// give your server registration url here
    static final String SERVER_URL = "http://www.calciomio.fr/PushAppCustom00/register.php"; 
	
	// display message 
	public static final String DISPLAY_MESSAGE_ACTION ="com.thientv.calciomino.pushnotifications.DISPLAY_MESSAGE";
	public static final String EXTRA_MESSAGE = "message";
	// action notification comment
	public static final String NOTI_NEW = "com.thientv.calciomino.noti_comment";
	// action notifiaction like
	public static final String NOTI_LIKE = "com.thientv.calciomino.noti_like";
	public static boolean onApp = false; 
	
	
	

	static Hashtable<Integer, Boolean> listNotificationId = new Hashtable<Integer, Boolean>();
	
	
	static void displayMessage(Context context, String message) {
		/*Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);*/
	}
	
	
	// phan tich message tu gcm
	public static void onReceiveMSG(Context context, String message){
		PreferenceHelper preferenceHelper = new PreferenceHelper(context);
		try {
			
			Log.i("DATA", "NOTI RECEVICED: "+message);
			Common.showNotification(context,message);	
			
			
			/*JSONObject jso = new JSONObject(message);
			Log.i("DATA", "NOTI RECEVICED: "+jso.toString());
			
			String name = jso.getString("name");
			String send_id = jso.getString("send_id");
			String alert = jso.getString("alert");
			int group = jso.getInt("group");
			
			if (!send_id.equals(preferenceHelper.getUserId())){
				Common.showNotification(context, alert, send_id, name, group);	
			} else {
			}*/
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	 /**
     * Issues a notification to inform the user that server has sent a message.
     */
    @SuppressWarnings("deprecation")
	public static void generateNotification(Context context, String message) {
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
        
        String title = context.getString(R.string.app_name);
        
        Intent notificationIntent = new Intent(context, MainActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
        
        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
        Log.e("screen on.................................", ""+isScreenOn);
        if(isScreenOn==false) {
	       WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE,"MyLock"); 
	       wl.acquire(10000);
	       WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyCpuLock");
           wl_cpu.acquire(10000);
        }
        
        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");
        
        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        listNotificationId.put(0, true);
        notificationManager.notify(0, notification);      
    }
    
    @SuppressWarnings({ "unused", "deprecation" })
   	private static void showNotification(Context context, String message) {
    	NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
    	
    	Notification notification = new Notification(R.drawable.logo_launcher, "Calciomio", System.currentTimeMillis());

       
       // The PendingIntent to launch our activity if the user selects this notification
    	Intent i = null;
    	i = new Intent(context, MainActivity.class);
    	i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       
    	PendingIntent contentIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);

       // Set the info for the views that show in the notification panel.
       // msg.length()>15 ? msg : msg.substring(0, 15);
       notification.setLatestEventInfo(context, "Calciomio",
                      						message, 
                      						contentIntent);
       
       notification.defaults |= Notification.DEFAULT_SOUND;
       
       PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
       boolean isScreenOn = pm.isScreenOn();
       Log.e("screen on.................................", ""+isScreenOn);
       if(isScreenOn==false) {
	       WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE,"MyLock"); 
	       wl.acquire(10000);
	       WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyCpuLock");
          wl_cpu.acquire(10000);
       }
       
       //TODO: it can be improved, for instance message coming from same user may be concatenated 
       // next version
       
       // Send the notification.
       // We use a layout id because it is a unique number.  We use it later to cancel.
       // We use a layout id because it is a unique number.  We use it later to cancel.
       notification.flags = Notification.FLAG_ONGOING_EVENT;
       notification.flags = Notification.FLAG_AUTO_CANCEL;
//       notificationManager.notify(("DATA: "+msg).hashCode(), notification);
       notificationManager.notify(message.hashCode(), notification);
    }

    public static long getCurrentTime() {
    	long time = System.currentTimeMillis();
    	return time;
	}
    
}
