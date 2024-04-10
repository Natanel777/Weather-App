package natanel.android.real_timeweatherapp.service.model.current_weather


import com.google.gson.annotations.SerializedName

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)