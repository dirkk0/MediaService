package com.ce.del3;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.media.session.MediaButtonReceiver;

public class MediaPlaybackService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private MediaSessionCompat.Callback mediaSessionCompatCallBack = new MediaSessionCompat.Callback()
    {

        @Override
        public boolean onMediaButtonEvent(Intent mediaButtonEvent) {
            String intentAction = mediaButtonEvent.getAction();

            if (Intent.ACTION_MEDIA_BUTTON.equals(intentAction))
            {
                KeyEvent event = mediaButtonEvent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);

                if (event != null)
                {
                    int action = event.getAction();
                    if (action == KeyEvent.ACTION_DOWN) {
                        switch (event.getKeyCode()) {
                            case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
                                // code for fast forward
                                return true;
                            case KeyEvent.KEYCODE_MEDIA_NEXT:
                                // code for next
                                Toast.makeText(getApplication(),"Next Button is pressed!",Toast.LENGTH_SHORT).show();
                                return true;
                            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                                // code for play/pause
                                Toast.makeText(getApplication(),"Play Button is pressed!",Toast.LENGTH_SHORT).show();
                                return true;
                            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                                // code for previous
                                Toast.makeText(getApplication(),"Prev Button is pressed!",Toast.LENGTH_SHORT).show();
                                return true;
                            case KeyEvent.KEYCODE_MEDIA_REWIND:
                                // code for rewind
                                return true;
                            case KeyEvent.KEYCODE_MEDIA_STOP:
                                // code for stop
                                Toast.makeText(getApplication(),"Stop Button is pressed!",Toast.LENGTH_SHORT).show();
                                return true;

                        }
                        return false;

                    }
                    if (action == KeyEvent.ACTION_UP) {

                    }
                }
            }
            return super.onMediaButtonEvent(mediaButtonEvent);
        }
    };

    private MediaSessionCompat mediaSessionCompat;


    @Override
    public void onCreate() {
        Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
        Log.e("SERVICE", "onCreate");

        mediaSessionCompat = new MediaSessionCompat(this, "MEDIA");
        mediaSessionCompat.setCallback(mediaSessionCompatCallBack);
        mediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        PlaybackStateCompat.Builder mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_SKIP_TO_NEXT |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSessionCompat.setPlaybackState(mStateBuilder.build());
        mediaSessionCompat.setActive(true);

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
        Log.e("SERVICE", "onDestroy");
        mediaSessionCompat.release();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
        Log.e("SERVICE_STARTUP", "onStart");

        MediaButtonReceiver.handleIntent(mediaSessionCompat, intent);

        return super.onStartCommand(intent, flags, startId);
    }
}