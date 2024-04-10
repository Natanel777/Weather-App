package natanel.android.real_timeweatherapp.service.model.forecast_weather


data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)