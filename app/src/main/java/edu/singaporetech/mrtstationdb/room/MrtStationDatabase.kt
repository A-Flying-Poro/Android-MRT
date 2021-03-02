package edu.singaporetech.mrtstationdb.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.singaporetech.mrtstationdb.model.MrtStationModel
import kotlinx.coroutines.CoroutineScope

@Database(entities = [MrtStationModel::class], version = 1, exportSchema = false)
abstract class MrtStationDatabase : RoomDatabase() {
    abstract fun dao(): MrtStationDao

    companion object {
        @Volatile
        private var INSTANCE: MrtStationDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): MrtStationDatabase =
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MrtStationDatabase::class.java,
                    MrtStationModel.tableName
                ).build()
                INSTANCE = instance
                instance
            }
    }
}