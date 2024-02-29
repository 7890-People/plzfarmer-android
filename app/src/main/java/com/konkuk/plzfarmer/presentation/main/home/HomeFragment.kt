package com.konkuk.plzfarmer.presentation.main.home

import android.util.Log
import androidx.fragment.app.activityViewModels
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.FragmentHomeBinding
import com.konkuk.plzfarmer.presentation.base.BaseFragment
import com.konkuk.plzfarmer.presentation.main.WeatherViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val TAG: String = "HomeFragment"
    override val layoutRes: Int = R.layout.fragment_home
    private val weatherViewModel: WeatherViewModel by activityViewModels()

    override fun afterViewCreated() {
        weatherViewModel.getCurrentWeather(	37.564214, 127.001699)
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