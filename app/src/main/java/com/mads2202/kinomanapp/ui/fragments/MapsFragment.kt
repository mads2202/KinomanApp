package com.mads2202.kinomanapp.ui.fragments

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.common.LOCATION_NAME

class MapsFragment : Fragment(), OnMapReadyCallback {
    private val MAX_RESULT: Int = 1

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near myMarker, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val geoCoder = Geocoder(requireContext())
        val adress =
            geoCoder.getFromLocationName(requireArguments().getString(LOCATION_NAME), MAX_RESULT)

        val myMarker = LatLng(adress[0].latitude, adress[0].longitude)
        googleMap.addMarker(MarkerOptions().position(myMarker).title(getString(R.string.place_of_birth)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myMarker))
        googleMap.uiSettings.isZoomControlsEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onMapReady(googleMap: GoogleMap) {
    }
}