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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SoundEffect extends Activity implements OnClickListener{
	
	Button classical;
	Button dance;
	Button jazz;
	Button pop;
	Button rock;
	Button none;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.soundeffect);
		
		classical = (Button) findViewById(R.id.classical);
		dance = (Button) findViewById(R.id.dance);
		jazz = (Button) findViewById(R.id.jazz);
		pop = (Button) findViewById(R.id.pop);
		rock = (Button) findViewById(R.id.rock);
		none = (Button) findViewById(R.id.none);
	    
		classical.setOnClickListener(this);
		dance.setOnClickListener(this);
		jazz.setOnClickListener(this);
		pop.setOnClickListener(this);
		rock.setOnClickListener(this);
		none.setOnClickListener(this);
	}
	
	public void onClick (View target) {
		Intent intent = new Intent();
		Log.i("SoundEffect", "Onclick");
		if (target == classical)
			intent.putExtra("EQ", (short)0);
		else if (target == dance)
			intent.putExtra("EQ", (short)1);
		else if (target == jazz)
			intent.putExtra("EQ", (short)2);
		else if (target == pop)
			intent.putExtra("EQ", (short)3);
		else if (target == rock)
			intent.putExtra("EQ", (short)4);
		else if (target == none)
			intent.putExtra("EQ", (short)5);
		else
			intent.putExtra("EQ", (short)6);
		setResult(RESULT_OK, intent);
		finish();
	}
}