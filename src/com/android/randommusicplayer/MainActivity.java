/*   
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.randommusicplayer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/** 
 * Main activity: shows media player buttons. This activity shows the media player buttons and
 * lets the user click them. No media handling is done here -- everything is done by passing
 * Intents to our {@link MusicService}.
 * */
public class MainActivity extends Activity implements OnClickListener {
    /**
     * The URL we suggest as default when adding by URL. This is just so that the user doesn't
     * have to find an URL to test this sample.
     */
    final String SUGGESTED_URL = "http://www.vorbis.com/music/Epoq-Lepidoptera.ogg";
    static final int EQUALIZER = 0;
    static final int TUNER = 1;

    Button mPlayButton;
    Button mPauseButton;
    Button mSkipButton;
    Button mStopButton;
    
    Button mEqualizerButton;
    Button mTunerButton;

    /**
     * Called when the activity is first created. Here, we simply set the event listeners and
     * start the background service ({@link MusicService}) that will handle the actual media
     * playback.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Intent intent = getIntent();
        Log.i("MainActivity","startService for " + intent.getType());
        
        if (intent.getType() != null) {
       	  Log.i("MainActivity","startService for " + intent.getAction());
       	  Uri uri = intent.getData();
       	  Log.i("MainActivity",uri.toString());
          Intent playUrl = new Intent(MusicService.ACTION_URL);
          playUrl.setData(uri);
          startService(playUrl);
        }

        mPlayButton = (Button) findViewById(R.id.playbutton);
        mPauseButton = (Button) findViewById(R.id.pausebutton);
        mSkipButton = (Button) findViewById(R.id.skipbutton);
        mStopButton = (Button) findViewById(R.id.stopbutton);

        mEqualizerButton = (Button) findViewById(R.id.equalizer);
        mTunerButton = (Button) findViewById(R.id.tuner);

        mPlayButton.setOnClickListener(this);
        mPauseButton.setOnClickListener(this);
        mSkipButton.setOnClickListener(this);
        mStopButton.setOnClickListener(this);
        
        mEqualizerButton.setOnClickListener(this);
        mTunerButton.setOnClickListener(this);
        

    }

    public void onClick(View target) {
        // Send the correct intent to the MusicService, according to the button that was clicked
        if (target == mPlayButton)
            startService(new Intent(MusicService.ACTION_PLAY));
        else if (target == mPauseButton)
            startService(new Intent(MusicService.ACTION_PAUSE));
        else if (target == mSkipButton)
            startService(new Intent(MusicService.ACTION_SKIP));
        else if (target == mStopButton)
            startService(new Intent(MusicService.ACTION_STOP));
        else if (target == mTunerButton)
        	startActivity(new Intent(this, Tuner.class));
        else if (target == mEqualizerButton) {
    		startActivityForResult(new Intent(this, Equalizer.class), EQUALIZER);
        } 
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
            case KeyEvent.KEYCODE_HEADSETHOOK:
                startService(new Intent(MusicService.ACTION_TOGGLE_PLAYBACK));
                return true;
            case KeyEvent.KEYCODE_MEDIA_NEXT:
                startService(new Intent(MusicService.ACTION_SKIP));
                return true;
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                // TODO: ensure that doing this in rapid succession actually plays the
                // previous song
                startService(new Intent(MusicService.ACTION_REWIND));
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == EQUALIZER) {
    		if (resultCode == RESULT_OK) {
    			// Set the returned audio effect to the music player
    			short EQ = data.getShortExtra("EQ", (short)5);
    			Log.i("onActivityResult", "returned data = " + EQ);
    			Intent intent = new Intent(MusicService.ACTION_PLAY);
    			intent.putExtra("EQ", EQ);
    			intent.putExtra("EQREQUEST", true);
    			startService(intent);
    		}
    	}
    }    
}
