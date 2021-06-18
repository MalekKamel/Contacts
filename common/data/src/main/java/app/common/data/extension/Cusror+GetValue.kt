package app.common.data.extension

import android.database.Cursor


fun Cursor.string(columnName: String): String? {
    return getString(getColumnIndex(columnName))
}

fun Cursor.int(columnName: String): Int {
    return getInt(getColumnIndex(columnName))
}