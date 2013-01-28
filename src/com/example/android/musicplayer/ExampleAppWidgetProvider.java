package com.example.android.musicplayer;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.app.PendingIntent;

public class ExampleAppWidgetProvider extends AppWidgetProvider{	
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
			RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.example_appwidget);
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