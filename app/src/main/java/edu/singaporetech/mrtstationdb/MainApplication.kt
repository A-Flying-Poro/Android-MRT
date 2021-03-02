package edu.singaporetech.mrtstationdb

import android.app.Application
import edu.singaporetech.mrtstationdb.room.MrtStationDatabase
import edu.singaporetech.mrtstationdb.room.MrtStationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { MrtStationDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { MrtStationRepository(database.dao()) }
}