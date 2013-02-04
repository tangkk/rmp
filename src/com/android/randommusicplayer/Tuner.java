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

public class Tuner extends Activity implements OnClickListener{
	Button E4;
	Button B3;
	Button G3;
	Button D3;
	Button A2;
	Button E2;
	
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
		
	}
}