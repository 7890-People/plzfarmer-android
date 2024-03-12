package com.konkuk.plzfarmer.presentation.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import android.view.View
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.gms.tasks.Task
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.FragmentHomeBinding
import com.konkuk.plzfarmer.presentation.base.BaseFragment
import com.konkuk.plzfarmer.presentation.common.RecyclerViewDecoration
import com.konkuk.plzfarmer.presentation.main.WeatherViewModel
import java.io.IOException
import java.util.Locale

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val TAG: String = "HomeFragment"
    override val layoutRes: Int = R.layout.fragment_home
    private val weatherViewModel: WeatherViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val CODE_GPS: Int = 300

    lateinit var myPlantRVAdapter: MyPlantRVAdapter

    override fun afterViewCreated() {
        Log.d(TAG, "afterViewCreated" )
        initData()
        initObservers()
        initRecyclerViews()
        initClickListener()
    }


    private fun initData() {
        //농장 정보가 없을 때
        checkLocationPermission()
        binding.homeFarmTitleTv.text =  "농장을 부탁해"
        homeViewModel.getMyPlantList() // 나의 식물 리스트
        homeViewModel.getRecentHistoryList() // 최근 진단 기록 리스트
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
        homeViewModel.myPlantList.observe(this) {
            it.byState(
                onSuccess = { response ->
                    myPlantRVAdapter.myPlantList = response
                    myPlantRVAdapter.notifyDataSetChanged()
                },
                onError = {

                },
                onLoading = {

                })
        }
        homeViewModel.recentHistoryList.observe(this) {
            it.byState(
                onSuccess = { response ->
                    if (response.size>=1){
                        binding.homeRecentHistoryItem1.visibility = View.VISIBLE
                        binding.recentHistoryItem1 = response[0]
                        response[0].imgUrl.let {
                            Glide.with(binding.root.context).load(it).into(binding.item1PlantImageIv)
                        }
                    }
                    if (response.size>=2){
                        binding.homeRecentHistoryItem2.visibility = View.VISIBLE
                        binding.recentHistoryItem2 = response[1]
                        response[1].imgUrl.let {
                            Glide.with(binding.root.context).load(it).into(binding.item2PlantImageIv)
                        }
                    }
                    if (response.size>=3){
                        binding.homeRecentHistoryItem3.visibility = View.VISIBLE
                        binding.recentHistoryItem3 = response[2]
                        response[2].imgUrl.let {
                            Glide.with(binding.root.context).load(it).into(binding.item3PlantImageIv)
                        }
                    }
                },
                onError = {

                },
                onLoading = {

                })
        }
    }

    private fun initRecyclerViews() {
        // 나의 식물 리사이클러뷰
        binding.homeMyPlantRv.addItemDecoration(RecyclerViewDecoration(10)) // 수평 간격 설정
        myPlantRVAdapter = MyPlantRVAdapter(onMyPlantItemClicked)
        binding.homeMyPlantRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.homeMyPlantRv.adapter = myPlantRVAdapter


    }

    private val onMyPlantItemClicked: (myPlant: MyPlantVO) -> Unit = {
        Log.d(TAG, "onMyPlantItemClicked")
    }

    private val onRecentHistoryItemClicked: (recentHistory: RecentHistoryItemVO) -> Unit = {
        Log.d(TAG, "onRecentHistoryItemClicked")
    }

    private fun initClickListener() {
        binding.homeRecentHistoryMoreIv.setOnClickListener {
            Log.d(TAG, "homeRecentHistoryMoreIv Clicked")
        }
    }


    // 위도, 경도 값 받아오기 ( 퍼미션 린트처리- 퍼미션 꼭 확인하고 사용하기)
    @SuppressLint("MissingPermission")
    private fun getLastLocationAndWeather() {
        Log.d(TAG, "getLastLocationAndWeather 실행됨")
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { success: Location? ->
                Log.d(TAG, "getLastLocationAndWeather success : ${success.toString()}")
                if (success != null) {
                    Log.d(TAG, "${success.latitude}/${success.longitude}")
                    weatherViewModel.getCurrentWeather(success.latitude, success.longitude) // 현재 날씨 정보 api 호출
                    val address = getAddress(success.latitude, success.longitude)?.get(0) // 위도경도값 => 주소 정보 얻기
                    binding.homeFarmLocationTv.text = address?.let {
                        "현위치 : ${it.getAddressLine(0)}"
                    }
                } else {
                    // 위치 정보가 null일 때(위치 권한은 동의 되어있는데, gps가 안 켜져있을 때) 처리할 코드
                    Log.d(TAG, "fusedLocationProviderClient.lastLocation Success인데 location이 null임")
                    setDefaultLocationAndWeather("gps가 안 켜져있어")
                }
            }
            .addOnFailureListener { fail ->
                // 기본값인 서울을 기준으로 날씨 표시
                Log.d(TAG, "fusedLocationProviderClient.lastLocation로 lat/lon 얻어오는거 실패함")
                setDefaultLocationAndWeather("")
            }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocationAndWeather() {
        Log.d(TAG, "getCurrentLocationAndWeather 실행됨")
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token
            override fun isCancellationRequested() = false
        })
            .addOnSuccessListener { success: Location? ->
                Log.d(TAG, "getCurrentLocationAndWeather success : ${success.toString()}")
                if (success != null) {
                    Log.d(TAG, "${success.latitude}/${success.longitude}")
                    weatherViewModel.getCurrentWeather(success.latitude, success.longitude) // 현재 날씨 정보 api 호출
                    val address = getAddress(success.latitude, success.longitude)?.get(0) // 위도경도값 => 주소 정보 얻기
                    binding.homeFarmLocationTv.text = address?.let {
                        "현위치 : ${it.getAddressLine(0)}"
                    }
                    binding.homeFarmLocationPermissonTv.visibility = View.GONE
                } else {
                    // 위치 정보가 null일 때(위치 권한은 동의 되어있는데, gps가 안 켜져있을 때) 처리할 코드
                    Log.d(TAG, "fusedLocationProviderClient.lastLocation Success인데 location이 null임")
                    setDefaultLocationAndWeather("gps가 안 켜져있어")
                }
            }
            .addOnFailureListener { fail ->
                // 기본값인 서울을 기준으로 날씨 표시
                Log.d(TAG, "fusedLocationProviderClient.lastLocation로 lat/lon 얻어오는거 실패함")
                setDefaultLocationAndWeather("")
            }
    }

    private fun setDefaultLocationAndWeather(msg: String){
        val defaultLatitude = 37.540957955055
        val defaultLongitute = 127.08278172427
        weatherViewModel.getCurrentWeather(   defaultLatitude, defaultLongitute) // 현재 날씨 정보 api 호출
        val address = getAddress(defaultLatitude, defaultLongitute)?.get(0) // 위도경도값 => 주소 정보 얻기
        Log.d(TAG, address.toString())
        binding.homeFarmLocationTv.text = address?.let {
            "${it.getAddressLine(0)}"
        }
        binding.homeFarmLocationPermissonTv.text = "(${msg} 건국대를 기준으로 날씨 정보를 제공합니다.)"
        binding.homeFarmLocationPermissonTv.visibility = View.VISIBLE
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
            Log.d(TAG, "위치 권한 허용되어있음")
            getLastLocationAndWeather()
            checkGPS()
        }else{ // 권한 허용이 안 되어있다면 => 권한 요청
            Log.d(TAG, "위치 권한 허용되어 있지 않음")
            locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION))
        }
    }

    private fun checkGPS() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationPermissionLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // 동의가 안되어 있었는데, 사용자가 GPS 동의를 한 경우
                Log.d(TAG, "gps 동의 완료")
                showToast("위치 정보가 켜졌습니다.")
                getCurrentLocationAndWeather()
            } else {
                // 사용자가 GPS 동의를 거부한 경우
                // 필요한 처리를 수행하거나 메시지를 표시할 수 있음
            }
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            // GPS가 켜져있었을 경우
        }
        task.addOnFailureListener { exception ->
            // GPS가 꺼져있을 경우- gps 켜달라고 요청
            if (exception is ResolvableApiException) {
                Log.d(TAG, "OnFailure")
                try {
                    locationPermissionLauncher.launch(IntentSenderRequest.Builder(exception.resolution).build())
                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.d(TAG, sendEx.message.toString())
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult")
        if(requestCode == CODE_GPS){
            if(resultCode == Activity.RESULT_OK){
                Log.d(TAG, "RESULT_OK")
                getCurrentLocationAndWeather()
            }else {
                //유저가 거부한 경우의 로직
            }
        }
    }

    // 위치 정보 권한 registerForActivityResult
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                Log.d(TAG, "ACCESS_FINE_LOCATION 허용됨")
                getLastLocationAndWeather()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                Log.d(TAG, "ACCESS_COARSE_LOCATION 허용됨")
                getLastLocationAndWeather()
            } else -> {
                // 위치 권한 허용 안됨.
                setDefaultLocationAndWeather("위치 권한이 없어")
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
            else -> Log.d(TAG, "해당되는 icon이 없음")
        }
    }
}