package com.gabrielribeiro.suacorrida.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gabrielribeiro.suacorrida.model.Run

@Dao
interface RunDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run : Run)

    @Delete
    suspend fun deleteRun(run: Run)

    @Query("SELECT * FROM run_table ORDER BY timestamp DESC ")
    fun getAllRunsSortedByDate() : LiveData<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY avgSpeedInKMH DESC ")
    fun getAllRunsSortedByAvgSpeed() : LiveData<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY distanceInMeters DESC ")
    fun getAllRunsSortedByDistanceInMeters() : LiveData<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY timeInMillis DESC ")
    fun getAllRunsSortedByTimeInMillis() : LiveData<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY caloriesBurned DESC ")
    fun getAllRunsSortedByCaloriesBurned() : LiveData<List<Run>>

    @Query("SELECT SUM (timeInMillis) FROM run_table")
    fun getTotalTimeMilliseconds() : LiveData<Long>

    @Query("SELECT SUM (caloriesBurned) FROM run_table")
    fun getTotalCaloriesBurned() : LiveData<Long>

    @Query("SELECT SUM (distanceInMeters) FROM run_table")
    fun getTotalDistanceInMeters() : LiveData<Long>

    @Query("SELECT AVG (avgSpeedInKMH) FROM run_table")
    fun getTotalAvgSpeed() : LiveData<Float>
}