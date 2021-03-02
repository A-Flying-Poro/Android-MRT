package edu.singaporetech.mrtstationdb.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.asLiveData
import edu.singaporetech.mrtstationdb.MainApplication
import edu.singaporetech.mrtstationdb.R
import edu.singaporetech.mrtstationdb.databinding.ActivityAddStationBinding
import edu.singaporetech.mrtstationdb.model.MrtStationModel
import edu.singaporetech.mrtstationdb.room.MrtStationViewModel
import edu.singaporetech.mrtstationdb.room.MrtStationViewModelFactory
import edu.singaporetech.mrtstationdb.room.UserPreferences
import edu.singaporetech.mrtstationdb.room.UserPreferencesRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddStationActivity : AppCompatActivity() {
    private val regexStationCode = Regex("^.+\$")
    private val regexStationName = Regex("^\\D+\$")

    private val preferencesRepository = UserPreferencesRepository.getInstance(this)

    private lateinit var binding: ActivityAddStationBinding

    private val viewModel: MrtStationViewModel by viewModels {
        MrtStationViewModelFactory((application as MainApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add_station)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_station)

        preferencesRepository.preferencesFlow.asLiveData().observe(this) { preferences ->
            binding.editTextStationCode.setText(preferences.stationCode)
            binding.editTextStationName.setText(preferences.stationName)
            binding.spinnerLineName.setSelection(preferences.stationLine)
        }
    }

    fun isInputValid(): Boolean {
        val stationCode = binding.editTextStationCode.text ?: return false
        val stationName = binding.editTextStationName.text ?: return false

        return stationCode.matches(regexStationCode) && stationName.matches(regexStationName)
    }

    override fun onStop() {
        super.onStop()

        if (isInputValid()) {
            val stationCode = binding.editTextStationCode.text?.toString() ?: return
            val stationName = binding.editTextStationName.text?.toString() ?: return
            val stationLine = binding.spinnerLineName.selectedItemPosition

            GlobalScope.launch {
                preferencesRepository.updateStation(UserPreferences(stationCode, stationName, stationLine))
            }
        }
    }

    fun addStation(view: View) {
        if (!isInputValid()) {
            Toast.makeText(this, R.string.add_invalid, Toast.LENGTH_SHORT).show()
            return
        }

        val stationCode = binding.editTextStationCode.text?.toString() ?: return
        val stationName = binding.editTextStationName.text?.toString() ?: return
        val stationLine = binding.spinnerLineName.selectedItem.toString()

        viewModel.insert(MrtStationModel(0, stationCode, stationName, stationLine))
        GlobalScope.launch {
            preferencesRepository.updateStation(UserPreferences("", "", 0))
        }
        finish()
    }

    fun clearValues(view: View) {
        binding.editTextStationCode.text?.clear()
        binding.editTextStationName.text?.clear()
        binding.spinnerLineName.setSelection(0)
    }
}