package com.gabrielribeiro.suacorrida.utils

import android.graphics.Color

object Constants {
    const val TABLE_NAME : String = "run_table"
    const val DATABASE_NAME : String = "run_database"
    const val REQUEST_CODE_LOCATION_PERMISSION = 1

    const val TAG = "TAG"

    const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"

    const val NOTIFICATION_CHANNEL_ID = "tracking_chanel"
    const val NOTIFICATION_CHANNEL_NAME = "traking"
    const val NOTIFICATION_ID = 1

    const val LOCATION_UPDATE_INTERVAL = 5000L
    const val FAST_LOCATION_INTERVAL = 2000L

    const val POLYLINE_COLOR = Color.RED
    const val POLYLINE_WIDTH = 8f
    const val CAMERA_ZOOM = 18f

    const val SHARED_PREFERENCES_NAME = "sharedPref"
    const val KEY_FIRST_TIME_TOGGLE = "KEY_FIRST_TIME_TOGGLE"
    const val KEY_NAME = "KEY_NAME"
    const val KEY_WEIGHT = "KEY_WEIGHT"

    const val CANCEL_TRACKING_DIALOG_TAG = "CancelDialog"


}