package com.gabrielribeiro.suacorrida.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gabrielribeiro.suacorrida.utils.Constants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Run(
    val image : Bitmap? = null,
    val timestamp : Long = 0L,
    val avgSpeedInKMH : Float = 0F,
    val distanceInMeters : Int = 0,
    val timeInMillis : Long = 0L,
    val caloriesBurned : Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
}
