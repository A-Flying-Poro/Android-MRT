package edu.singaporetech.mrtstationdb.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import edu.singaporetech.mrtstationdb.MainApplication
import edu.singaporetech.mrtstationdb.R
import edu.singaporetech.mrtstationdb.adapter.MrtStationAdapter
import edu.singaporetech.mrtstationdb.databinding.ActivityMainBinding
import edu.singaporetech.mrtstationdb.room.MrtStationViewModel
import edu.singaporetech.mrtstationdb.room.MrtStationViewModelFactory

/**
 *  CSC2007 Quiz 2
 *  Some comments here?
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MrtStationViewModel by viewModels {
        MrtStationViewModelFactory((application as MainApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        with(binding.recyclerViewStations) {
            adapter = MrtStationAdapter().apply {
                viewModel.allStations.observe(this@MainActivity, Observer { list ->
                    list?.let { submitList(it) }
                })
            }
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL))
        }
        binding.fabAddStation.setOnClickListener {
            startActivity(Intent(this, AddStationActivity::class.java))
        }

        // TODO implement floating action button
    }

    // TODO need to create a separate classes for the relational database elsewhere? (not here!)
}