package natanel.android.real_timeweatherapp.service.model.forecast_weather.forecastData

import natanel.android.real_timeweatherapp.service.model.forecast_weather.ForecastItem

fun toForecastItemData(
    tempMax:String,
    tempMin: String,
    weatherStatus: String,
    date: String
): ForecastItemData = ForecastItemData(
    tempMax = tempMax,
    tempMin = tempMin,
    weatherStatus = weatherStatus,
    date = date
)