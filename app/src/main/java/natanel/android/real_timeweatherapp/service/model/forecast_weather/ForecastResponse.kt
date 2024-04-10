package natanel.android.real_timeweatherapp.service.model.forecast_weather


data class ForecastResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ForecastItem>,
    val message: Int
)