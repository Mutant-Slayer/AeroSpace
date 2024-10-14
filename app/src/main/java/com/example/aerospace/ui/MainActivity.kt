package com.example.aerospace.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aerospace.databinding.ActivityMainBinding
import com.example.aerospace.ui.satelliteList.SatelliteListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(binding.flMainContainer.id, SatelliteListFragment.newInstance())
                .commit()
        }
    }
}
