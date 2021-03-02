package edu.singaporetech.mrtstationdb.room

import androidx.annotation.WorkerThread
import edu.singaporetech.mrtstationdb.model.MrtStationModel
import kotlinx.coroutines.flow.Flow

class MrtStationRepository(private val dao: MrtStationDao) {
    val allStations: Flow<List<MrtStationModel>> = dao.getAllStations()

    @WorkerThread
    suspend fun insert(station: MrtStationModel) = dao.insert(station)
}