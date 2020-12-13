package com.example.weatherapp.today

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.R
import com.example.weatherapp.daily.DailyViewModel
import com.example.weatherapp.databinding.FragmentTodayBinding
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.TimeUnit


class TodayFragment : Fragment() {

    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    private val binding: FragmentTodayBinding by lazy {
        FragmentTodayBinding.inflate(layoutInflater)
    }

    private val viewModel: TodayViewModel by lazy {
        ViewModelProvider(this).get(TodayViewModel::class.java)
    }

    private val dailyViewModel : DailyViewModel by lazy {
        ViewModelProvider(this).get(DailyViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentTodayBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        getLastLocation()

        return binding.root
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    //When user Allow or Deny our requested permissions
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                //start getting the location information
                getLastLocation()
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            activity!!.getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }


    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireContext())
                mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        viewModel.onLocationUpdate(location)
                        dailyViewModel.onLocationUpdate(location)
                        Log.d(TAG, "onLocationResult: ${location.latitude} & ${location.longitude}")
                    } else {
                        //requestNewLocationData
                        mFusedLocationClient.requestLocationUpdates(
                            getLocationRequest(),
                            getLocationCallback(),
                            Looper.getMainLooper()
                        )
                    }
                }
            } else {
                Snackbar.make(
                    binding.todayLayout,
                    R.string.location_required,
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Settings") {
                    val intent = Intent().apply {
                        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(intent)
                }.show()
                return
            }
        } else {
            requestPermissions()
        }
    }

    private fun getLocationCallback(): LocationCallback {

        return object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)

                val location = locationResult?.lastLocation ?: return
                viewModel.onLocationUpdate(location)
                dailyViewModel.onLocationUpdate(location)
                Log.d(TAG, "onLocationResult: ${location.latitude} & ${location.longitude}")
            }
        }

    }

    private fun getLocationRequest(): LocationRequest {
        return LocationRequest().apply {
            interval = TimeUnit.SECONDS.toMillis(60)
            fastestInterval = TimeUnit.SECONDS.toMillis(30)
            maxWaitTime = TimeUnit.MINUTES.toMillis(2)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    companion object {
        const val PERMISSION_ID = 42
        private const val TAG = "TodayFragment"
    }
}