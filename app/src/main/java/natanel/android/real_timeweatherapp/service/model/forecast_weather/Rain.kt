package natanel.android.real_timeweatherapp.service.model.forecast_weather


import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h")
    val h: Double
)