package com.gabrielribeiro.suacorrida.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gabrielribeiro.suacorrida.database.RunDatabase
import com.gabrielribeiro.suacorrida.utils.Constants.DATABASE_NAME
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




}