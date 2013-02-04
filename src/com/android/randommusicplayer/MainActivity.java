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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
    static final int SOUNDEFFECTREQ = 0;
    static final int TUNER = 1;

    Button mPlayButton;
    Button mPauseButton;
    Button mSkipButton;
    Button mRewindButton;
    Button mStopButton;
    Button mEjectButton;

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
        mRewindButton = (Button) findViewById(R.id.rewindbutton);
        mStopButton = (Button) findViewById(R.id.stopbutton);
        mEjectButton = (Button) findViewById(R.id.ejectbutton);

        mPlayButton.setOnClickListener(this);
        mPauseButton.setOnClickListener(this);
        mSkipButton.setOnClickListener(this);
        mRewindButton.setOnClickListener(this);
        mStopButton.setOnClickListener(this);
        mEjectButton.setOnClickListener(this);
        

    }

    public void onClick(View target) {
        // Send the correct intent to the MusicService, according to the button that was clicked
        if (target == mPlayButton)
            startService(new Intent(MusicService.ACTION_PLAY));
        else if (target == mPauseButton)
            startService(new Intent(MusicService.ACTION_PAUSE));
        else if (target == mSkipButton)
            startService(new Intent(MusicService.ACTION_SKIP));
        else if (target == mRewindButton)
            startService(new Intent(MusicService.ACTION_REWIND));
        else if (target == mStopButton)
            startService(new Intent(MusicService.ACTION_STOP));
        else if (target == mEjectButton) {
            showUrlDialog();
        }
    }

    /** 
     * Shows an alert dialog where the user can input a URL. After showing the dialog, if the user
     * confirms, sends the appropriate intent to the {@link MusicService} to cause that URL to be
     * played.
     */
    void showUrlDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Manual Input");
        alertBuilder.setMessage("Enter a URL");
        final EditText input = new EditText(this);
        alertBuilder.setView(input);

        input.setText(SUGGESTED_URL);

        alertBuilder.setPositiveButton("Play!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int whichButton) {
                // Send an intent with the URL of the song to play. This is expected by
                // MusicService.
                Intent i = new Intent(MusicService.ACTION_URL);
                Uri uri = Uri.parse(input.getText().toString());
                i.setData(uri);
                startService(i);
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int whichButton) {}
        });

        alertBuilder.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
            case KeyEvent.KEYCODE_HEADSETHOOK:
                startService(new Intent(MusicService.ACTION_TOGGLE_PLAYBACK));
                return true;
            case KeyEvent.KEYCODE_FOCUS:
            	startService(new Intent(MusicService.ACTION_SKIP));
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
    public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.menu, menu);
      return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case R.id.soundeffect:
    		startActivityForResult(new Intent(this, SoundEffect.class), SOUNDEFFECTREQ);
    		return true;
    	case R.id.tuner:
    		startActivity(new Intent(this, Tuner.class));
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    	
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == SOUNDEFFECTREQ) {
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
