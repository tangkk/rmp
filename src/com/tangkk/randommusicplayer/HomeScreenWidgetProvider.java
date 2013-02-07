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

package com.tangkk.randommusicplayer;

import com.tangkk.randommusicplayer.R;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.app.PendingIntent;

public class HomeScreenWidgetProvider extends AppWidgetProvider{	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
		final int N = appWidgetIds.length;
		
		// Perform this loop procedure for each App Widget that belongs to this provider
		for (int i=0; i<N; i++) {
			int appWidgetId = appWidgetIds[i];
			
			// Create MusicService Intents
			Intent PlayIntent = new Intent(MusicService.ACTION_PLAY);
			PendingIntent pendingPlayIntent = PendingIntent.getService(context, 0, PlayIntent, 0);
			Intent PauseIntent = new Intent(MusicService.ACTION_PAUSE);
			PendingIntent pendingPauseIntent = PendingIntent.getService(context, 0, PauseIntent, 0);
			Intent SkipIntent = new Intent(MusicService.ACTION_SKIP);
			PendingIntent pendingSkipIntent = PendingIntent.getService(context, 0, SkipIntent, 0);
			Intent RewindIntent = new Intent(MusicService.ACTION_REWIND);
			PendingIntent pendingRewindIntent = PendingIntent.getService(context, 0, RewindIntent, 0);
			Intent StopIntent = new Intent(MusicService.ACTION_STOP);
			PendingIntent pendingStopIntent = PendingIntent.getService(context, 0, StopIntent, 0);
			
			// Get the layout for the App Widget and attach on-click listeners to the buttons
			RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.home_screen_appwidget);
			views.setOnClickPendingIntent(R.id.playbutton, pendingPlayIntent);
			views.setOnClickPendingIntent(R.id.pausebutton, pendingPauseIntent);
			views.setOnClickPendingIntent(R.id.skipbutton, pendingSkipIntent);
			views.setOnClickPendingIntent(R.id.rewindbutton, pendingRewindIntent);
			views.setOnClickPendingIntent(R.id.stopbutton, pendingStopIntent);
			
			// Update the current views
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
		
	}
}
