package com.gabrielribeiro.suacorrida.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gabrielribeiro.suacorrida.model.Run
import com.gabrielribeiro.suacorrida.utils.Converts

@Database(
    entities = [Run::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converts::class)
abstract class RunDatabase : RoomDatabase() {

    abstract fun runDAO() : RunDAO
}