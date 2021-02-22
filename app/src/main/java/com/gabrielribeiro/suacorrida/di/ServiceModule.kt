package com.gabrielribeiro.suacorrida.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.gabrielribeiro.suacorrida.MainActivity
import com.gabrielribeiro.suacorrida.R
import com.gabrielribeiro.suacorrida.utils.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @ServiceScoped
    @Provides
    fun provideFusedLocationProviderClient( @ApplicationContext context: Context)
    = FusedLocationProviderClient(context)

    @ServiceScoped
    @Provides
    fun provideMainActivityPendingIntent(@ApplicationContext context: Context) = PendingIntent.getActivity(
        context, 0, Intent(context, MainActivity::class.java).apply {
            action = Constants.ACTION_SHOW_TRACKING_FRAGMENT
        },
        PendingIntent.FLAG_UPDATE_CURRENT
    )


    @ServiceScoped
    @Provides
    fun provideBaseNotificationBuilder(@ApplicationContext context: Context, pendingIntent: PendingIntent)
        = NotificationCompat.Builder(context,
        Constants.NOTIFICATION_CHANNEL_ID)
        .setAutoCancel(false)
        .setSmallIcon(R.drawable.ic_run)
        .setContentTitle(context.getString(R.string.app_name))
        .setContentText("00:00:00")
        .setContentIntent(pendingIntent)
}