package com.aldiprahasta.storyapp.presentation.map

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.aldiprahasta.storyapp.R
import com.aldiprahasta.storyapp.databinding.ActivityMapsBinding
import com.aldiprahasta.storyapp.domain.model.StoryDomainModel
import com.aldiprahasta.storyapp.utils.doIfError
import com.aldiprahasta.storyapp.utils.doIfLoading
import com.aldiprahasta.storyapp.utils.doIfSuccess
import com.aldiprahasta.storyapp.utils.gone
import com.aldiprahasta.storyapp.utils.visible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val viewModel by viewModel<MapsViewModel>()
    private lateinit var storyModelList: List<StoryDomainModel>
    private val boundsBuilder = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeData()
    }

    private fun subscribeData() {
        lifecycleScope.launch {
            viewModel.getStoryLocation.flowWithLifecycle(lifecycle).collect { state ->
                state.apply {
                    doIfLoading {
                        binding.pbMap.visible()
                    }

                    doIfError { _, errorMessage ->
                        binding.pbMap.gone()
                        Toast.makeText(this@MapsActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    }

                    doIfSuccess { storyList ->
                        binding.pbMap.gone()
                        storyModelList = storyList

                        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                        val mapFragment = supportFragmentManager
                                .findFragmentById(R.id.map) as SupportMapFragment
                        mapFragment.getMapAsync(this@MapsActivity)
                    }
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        addAnyMarkers()
    }

    private fun addAnyMarkers() {
        storyModelList.forEach { item ->
            if (item.lat != null && item.lng != null) {
                val latLng = LatLng(item.lat, item.lng)
                mMap.addMarker(MarkerOptions().position(latLng).title(item.name))
                boundsBuilder.include(latLng)
            }
        }

        val bounds: LatLngBounds = boundsBuilder.build()
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngBounds(
                        bounds,
                        resources.displayMetrics.widthPixels,
                        resources.displayMetrics.heightPixels,
                        300
                )
        )

        val indonesiaLatLng = LatLng(0.7893, 113.9213)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(indonesiaLatLng, 3f))
    }
}