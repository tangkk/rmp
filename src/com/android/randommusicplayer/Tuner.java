/*   
 * Copyright 2013 tangkk
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.Controller;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Tuner extends Activity implements OnClickListener{
	Button E4;
	Button B3;
	Button G3;
	Button D3;
	Button A2;
	Button E2;
	
	public final static int NoteE4 = 64;
	public final static int NoteB3 = 59;
	public final static int NoteG3 = 55;
	public final static int NoteD3 = 50;
	public final static int NoteA2 = 45;
	public final static int NoteE2 = 40;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.tuner);
		
		E4 = (Button) findViewById(R.id.E4);
		B3 = (Button) findViewById(R.id.B3);
		G3 = (Button) findViewById(R.id.G3);
		D3 = (Button) findViewById(R.id.D3);
		A2 = (Button) findViewById(R.id.A2);
		E2 = (Button) findViewById(R.id.E2);
		
		E4.setOnClickListener(this);
		B3.setOnClickListener(this);
		G3.setOnClickListener(this);
		D3.setOnClickListener(this);
		A2.setOnClickListener(this);
		E2.setOnClickListener(this);
	}
	
	@Override
	public void onClick (View target) {
		Log.i("Tuner", "Onclick");
		if (target == E4) {
			Log.i("Tuner", "E4");
			playNote(NoteE4);
		} else if (target == B3) {
			Log.i("Tuner", "B3");
			playNote(NoteB3);
		} else if (target == G3) {
			Log.i("Tuner", "G3");
			playNote(NoteG3);
		} else if (target == D3) {
			playNote(NoteD3);
		} else if (target == A2) {
			playNote(NoteA2);
		} else if (target == E2) {
			playNote(NoteE2);
		}
	}
	
	// This function reuse a lot of code from the example code provided in com.leff.midi package
	public void playNote(int noteNum) {
		// 1. Create some MidiTracks
		MidiTrack tempoTrack = new MidiTrack();
		MidiTrack noteTrack = new MidiTrack();
		
		// 2. Add events to the tracks
		// 2a. Track 0 is typically the tempo map
		TimeSignature ts = new TimeSignature();
		ts.setTimeSignature(4, 4, TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION);
		
		Tempo t = new Tempo();
		t.setBpm(228);
		
		tempoTrack.insertEvent(ts);
		tempoTrack.insertEvent(t);
		
		int channel = 0, pitch = noteNum, velocity = 120, volume = 127;
		noteTrack.insertNote(channel, pitch, velocity, 0, 300);
		noteTrack.insertEvent(new Controller(0, channel, 0x7, volume));
		
		
		// 3. Create a MidiFile with the tracks we created
		ArrayList<MidiTrack> tracks = new ArrayList<MidiTrack>();
		tracks.add(tempoTrack);
		tracks.add(noteTrack);
		
		MidiFile midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);
		
		// 4. Write the MIDI data to a file
		File output = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "tuner.mid");
		try {
			midi.writeToFile(output);
			//C.writeToFile(output, true);
			playPath(output.getPath());
		} catch(IOException e) {
			System.err.println(e);
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Log.i("Tuner", "onStop()");
		Intent intent = new Intent(MusicService.ACTION_STOP);
		startService(intent);
	}

	public void playPath(String path) {
		Log.i("Tuner", "playPath: " + path);
		// 1. change the path into a Uri with "file" protocol
		Uri uri = Uri.parse("file://"+ path);
		
		// 2. start a service to serve this MIDI playback intent
		Intent intent = new Intent(MusicService.ACTION_URL);
		intent.setData(uri);
		intent.putExtra("Tuner", true);
		startService(intent);
	}
}
