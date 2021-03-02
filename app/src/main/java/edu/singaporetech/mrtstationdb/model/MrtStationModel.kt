package edu.singaporetech.mrtstationdb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.singaporetech.mrtstationdb.model.MrtStationModel.Companion.tableName

@Entity(tableName = tableName)
data class MrtStationModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "station_code")
    val stationCode: String,

    @ColumnInfo(name = "station_name")
    val stationName: String,

    @ColumnInfo(name = "station_line")
    val stationLine: String
) {
    companion object {
        const val tableName = "mrtstation"
    }
}