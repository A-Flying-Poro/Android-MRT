package edu.singaporetech.mrtstationdb.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.singaporetech.mrtstationdb.model.MrtStationModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MrtStationDao {
    @Query("SELECT * FROM " + MrtStationModel.tableName)
    fun getAllStations(): Flow<List<MrtStationModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(model: MrtStationModel)

    @Query("DELETE FROM " + MrtStationModel.tableName)
    suspend fun deleteAll()
}