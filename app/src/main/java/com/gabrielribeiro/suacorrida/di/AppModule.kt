package com.gabrielribeiro.suacorrida.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gabrielribeiro.suacorrida.database.RunDatabase
import com.gabrielribeiro.suacorrida.utils.Constants.DATABASE_NAME
import com.gabrielribeiro.suacorrida.utils.Constants.KEY_FIRST_TIME_TOGGLE
import com.gabrielribeiro.suacorrida.utils.Constants.KEY_NAME
import com.gabrielribeiro.suacorrida.utils.Constants.KEY_WEIGHT
import com.gabrielribeiro.suacorrida.utils.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRunDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context,
            RunDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideRunDAO(runDatabase: RunDatabase) = runDatabase.runDAO()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context) =
            app.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideName(sharedPref: SharedPreferences) = sharedPref.getString(KEY_NAME, "") ?: ""

    @Singleton
    @Provides
    fun provideWeight(sharedPref: SharedPreferences) = sharedPref.getFloat(KEY_WEIGHT, 72F)

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPref: SharedPreferences) =
            sharedPref.getBoolean(KEY_FIRST_TIME_TOGGLE, true)

}