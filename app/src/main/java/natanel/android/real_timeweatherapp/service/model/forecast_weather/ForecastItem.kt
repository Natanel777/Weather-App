package natanel.android.real_timeweatherapp.service.model.forecast_weather


import com.google.gson.annotations.SerializedName

data class ForecastItem(
    val clouds: Clouds,
    val dt: Int,
    @SerializedName("dt_txt")
    val dtTxt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)