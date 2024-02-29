package com.konkuk.plzfarmer.remote.response

// WeatherResponse
// API DOCS: https://openweathermap.org/current#example_JSON

data class WeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val rain: Rain,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)

data class Coord(
    val lon: Double, //Longitude of the location
    val lat: Double //Latitude of the location
)

data class Weather(
    val id: Int, //Weather condition id
    val main: String, //Group of weather parameters (Rain, Snow, Clouds etc.)
    val description: String, //Weather condition within the group
    val icon: String //Weather icon id
)

data class Main(
    val temp: Double, // Temperature. Unit Default: Kelvin
    val feels_like: Double, // This temperature parameter accounts for the human perception of weather.
    val temp_min: Double, // Minimum temperature at the moment
    val temp_max: Double, //  Maximum temperature at the moment.
    val pressure: Int,
    val humidity: Int, // Humidity, %
    val sea_level: Int, // Atmospheric pressure on the sea level, hPa
    val grnd_level: Int // Atmospheric pressure on the ground level, hPa
)

data class Wind(
    val speed: Double, // Wind speed. Unit Default: meter/sec,
    val deg: Int, // Wind direction, degrees (meteorological)
    val gust: Double // Wind gust. Unit Default: meter/sec,
)

data class Rain(
    val hour : Double
)

data class Clouds(
    val all: Int // Cloudiness, %
)

data class Sys(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)