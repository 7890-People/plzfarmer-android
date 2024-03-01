package com.konkuk.plzfarmer.presentation.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.LocationServices
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.FragmentHomeBinding
import com.konkuk.plzfarmer.presentation.base.BaseFragment
import com.konkuk.plzfarmer.presentation.main.WeatherViewModel
import java.io.IOException
import java.util.Locale

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val TAG: String = "HomeFragment"
    override val layoutRes: Int = R.layout.fragment_home
    private val weatherViewModel: WeatherViewModel by activityViewModels()

    override fun afterViewCreated() {
        //농장 정보가 없을 때
        checkLocationPermission()
        binding.homeFarmTitleTv.text =  "농장을 부탁해"

        initObservers()
    }
    private fun initObservers() {
        weatherViewModel.weatherResponse.observe(this) {
            it.byState(
                onSuccess = { response ->
                    setWeatherIconAndDescription(response.weather[0].icon)
                },
                onError = {

                },
                onLoading = {

                })
        }
    }

    // 위도, 경도 값 받아오기 ( 퍼미션 린트처리- 퍼미션 꼭 확인하고 사용하기)
    @SuppressLint("MissingPermission")
    private fun getCurrentLocationAndWeather() {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { success: Location? ->
                success?.let { location ->
                    weatherViewModel.getCurrentWeather(location.latitude, location.longitude) // 현재 날씨 정보 api 호출
                    val address = getAddress(location.latitude, location.longitude)?.get(0) // 위도경도값 => 주소 정보 얻기
                    binding.homeFarmLocationTv.text = address?.let {
                        "현위치 : ${it.adminArea} ${it.locality} ${it.thoroughfare}"
                    }
                }
            }
            .addOnFailureListener { fail ->
                // 기본값인 서울을 기준으로 날씨 표시
                val defaultLatitude = 37.540957955055
                val defaultLongitute = 127.08278172427
                weatherViewModel.getCurrentWeather(   defaultLatitude, defaultLongitute) // 현재 날씨 정보 api 호출
                val address = getAddress(defaultLatitude, defaultLongitute)?.get(0) // 위도경도값 => 주소 정보 얻기
                Log.d("address", address.toString())
                binding.homeFarmLocationTv.text = address?.let {
                    "${it.adminArea} ${it.locality} ${it.thoroughfare}"
                }
                binding.homeFarmLocationPermissonTv.text = "(위치 권한이 없어 건국대를 기준으로 날씨 정보를 제공합니다.)"
                binding.homeFarmLocationPermissonTv.visibility = View.VISIBLE
            }
    }

    // 위도, 경도 => 주소 값
    private fun getAddress(lat: Double, lng: Double): List<Address>? {
        lateinit var address: List<Address>

        return try {
            val geocoder = Geocoder(requireContext(), Locale.KOREA)
            address = geocoder.getFromLocation(lat, lng, 1) as List<Address>
            address
        } catch (e: IOException) {
            showToast("주소를 가져 올 수 없습니다")
            null
        }
    }

    // 위치 정보 권한 체크
    private fun checkLocationPermission() {
        var hasFineLocationPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        var hasCoarseLocationPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)

        // 권한 허용이 되어있다면
        if(hasFineLocationPermission == PackageManager.PERMISSION_GRANTED || hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED){
            getCurrentLocationAndWeather()
        }else{ // 권한 허용이 안 되어있다면 => 권한 요청
            locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION))
        }
    }

    // 위치 정보 권한 registerForActivityResult
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                Log.d("permission", "ACCESS_FINE_LOCATION 허용됨")
                getCurrentLocationAndWeather()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                Log.d("permission", "ACCESS_COARSE_LOCATION 허용됨")
                getCurrentLocationAndWeather()
            } else -> {
                // 위치 권한 허용 안됨.
            }
        }
    }

    private fun setWeatherIconAndDescription(value: String) {
        when (value) {
            "01d" -> {
                binding.homeWeatherIconIv.setImageResource(R.drawable.home_icon_weather_01d)
                binding.homeWeatherDescriptionTv.text = "날씨가 맑아요"
            }
            "01n" -> {
                binding.homeWeatherIconIv.setImageResource(R.drawable.home_icon_weather_01n)
                binding.homeWeatherDescriptionTv.text = "날씨가 맑아요"
            }
            "02d" -> {
                binding.homeWeatherIconIv.setImageResource(R.drawable.home_icon_weather_02d)
                binding.homeWeatherDescriptionTv.text = "구름이 조금 있어요"
            }
            "02n" -> {
                binding.homeWeatherIconIv.setImageResource(R.drawable.home_icon_weather_02n)
                binding.homeWeatherDescriptionTv.text = "구름이 조금 있어요"
            }
            in listOf("03d", "03n")  -> {
                binding.homeWeatherIconIv.setImageResource(R.drawable.home_icon_weather_03dn)
                binding.homeWeatherDescriptionTv.text = "구름이 많이 있어요"
            }
            in listOf("04d", "04n")  -> {
                binding.homeWeatherIconIv.setImageResource(R.drawable.home_icon_weather_04dn)
                binding.homeWeatherDescriptionTv.text = "날씨가 흐려요"
            }
            in listOf("09d", "09n")  -> {
                binding.homeWeatherIconIv.setImageResource(R.drawable.home_icon_weather_09_10dn)
                binding.homeWeatherDescriptionTv.text = "소나기가 와요"
            }
            in listOf("10d", "10n")  -> {
                binding.homeWeatherIconIv.setImageResource(R.drawable.home_icon_weather_09_10dn)
                binding.homeWeatherDescriptionTv.text = "비가 와요"
            }
            in listOf("11d", "11n")   -> {
                binding.homeWeatherIconIv.setImageResource(R.drawable.home_icon_weather_11dn)
                binding.homeWeatherDescriptionTv.text = "천둥이 쳐요"
            }
            in listOf("13d", "13n")  -> {
                binding.homeWeatherIconIv.setImageResource(R.drawable.home_icon_weather_13dn)
                binding.homeWeatherDescriptionTv.text = "눈이 내려요"
            }
            in listOf("50d", "50n")  -> {
                binding.homeWeatherIconIv.setImageResource(R.drawable.home_icon_weather_50dn)
                binding.homeWeatherDescriptionTv.text = "안개가 꼈어요"
            }
            else -> Log.d("weather", "해당되는 icon이 없음")
        }
    }
}