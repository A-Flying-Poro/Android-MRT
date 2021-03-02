package edu.singaporetech.mrtstationdb.room

import androidx.lifecycle.*
import edu.singaporetech.mrtstationdb.model.MrtStationModel
import kotlinx.coroutines.launch

class MrtStationViewModel(private val repository: MrtStationRepository) : ViewModel() {
    val allStations: LiveData<List<MrtStationModel>> = repository.allStations.asLiveData()

    fun insert(station: MrtStationModel) = viewModelScope.launch {
        repository.insert(station)
    }
}

@Suppress("UNCHECKED_CAST")
class MrtStationViewModelFactory(private val repository: MrtStationRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MrtStationViewModel::class.java)) {
            return MrtStationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}