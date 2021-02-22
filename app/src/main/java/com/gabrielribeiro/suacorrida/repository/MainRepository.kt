package com.gabrielribeiro.suacorrida.repository

import com.gabrielribeiro.suacorrida.database.RunDAO
import com.gabrielribeiro.suacorrida.model.Run
import javax.inject.Inject

class MainRepository @Inject constructor(private val runDAO : RunDAO) {

    suspend fun insertRun(run : Run) = runDAO.insertRun(run)

    suspend fun deleteRun(run: Run) = runDAO.deleteRun(run)

    fun getAllRunsSortedByDate() = runDAO.getAllRunsSortedByDate()

    fun getAllRunsSortedByAvgSpeed() = runDAO.getAllRunsSortedByAvgSpeed()

    fun getAllRunsSortedByDistanceInMeters() = runDAO.getAllRunsSortedByDistanceInMeters()

    fun getAllRunsSortedByTimeInMillis() = runDAO.getAllRunsSortedByTimeInMillis()

    fun getAllRunsSortedByCaloriesBurned() = runDAO.getAllRunsSortedByCaloriesBurned()

    fun getTotalTimeMilliseconds() = runDAO.getTotalTimeMilliseconds()

    fun getTotalCaloriesBurned() = runDAO.getTotalCaloriesBurned()

    fun getTotalDistanceInMeters() = runDAO.getTotalDistanceInMeters()

    fun getTotalAvgSpeed() = runDAO.getTotalAvgSpeed()
}