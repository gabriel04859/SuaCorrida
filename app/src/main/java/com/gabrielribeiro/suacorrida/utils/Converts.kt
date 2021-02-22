package com.gabrielribeiro.suacorrida.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Converts {

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap = BitmapFactory
        .decodeByteArray(byteArray,0, byteArray.size)

    @TypeConverter
    fun fromBitmap(bitmap : Bitmap) : ByteArray{
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }
}