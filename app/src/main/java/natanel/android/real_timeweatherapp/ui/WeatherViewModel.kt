package natanel.android.real_timeweatherapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import natanel.android.real_timeweatherapp.service.api.ApiService
import natanel.android.real_timeweatherapp.service.model.current_weather.CurrentWeatherResponse
import natanel.android.real_timeweatherapp.service.model.forecast_weather.ForecastItem
import natanel.android.real_timeweatherapp.service.model.forecast_weather.forecastData.ForecastItemData
import natanel.android.real_timeweatherapp.service.model.forecast_weather.forecastData.toForecastItemData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

class WeatherViewModel : ViewModel() {

    private val _currentWeather = MutableLiveData<CurrentWeatherResponse?>(null)
    val currentWeather: LiveData<CurrentWeatherResponse?> = _currentWeather

    private val _forecast = MutableLiveData<List<ForecastItemData>>(listOf())
    val forecast: LiveData<List<ForecastItemData>> = _forecast

    //error for catching problems with the api
    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    init {
        fetchCurrentWeather()
        fetchForecast()
    }

    private fun fetchCurrentWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = ApiService.create().getCurrentWeather()
                withContext(Dispatchers.Main) {
                    _currentWeather.postValue(result)
                }
            } catch (e: Exception) {
                _error.postValue("Failed to fetch current weather. Please try again later.")
                Log.d("weather api error", "ERROR: " + e.message)
            }
        }
    }

    private fun fetchForecast() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val apiResult = ApiService.create().getForecast()
                val result = filterForecastData(apiResult.list)

                withContext(Dispatchers.Main) {
                    _forecast.postValue(result)
                }
            } catch (e: Exception) {
                _error.postValue( "Failed to fetch forecast. Please try again later.")
                Log.d("forecast api error", "ERROR: " + e.message)
            }
        }
    }

    private fun filterForecastData(apiResult: List<ForecastItem>) : List<ForecastItemData>{

        val forecastList = mutableListOf<ForecastItemData>()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        var tempMin = ""
        var tempMax: String
        var weatherStatus: String
        var date: String

        for (item in apiResult){
            if (!item.dtTxt.startsWith(currentDate)){ // get the dates except the first one
                if (item.dtTxt.contains("03:00:00")){
                    tempMin = item.main.tempMin.roundToInt().toString()
                }

                else if (item.dtTxt.contains("12:00:00")){
                    tempMax = item.main.tempMax.roundToInt().toString()
                    weatherStatus = item.weather[0].main
                    date = convertDateToDay(item.dtTxt)

                    forecastList.add(
                        toForecastItemData(tempMax,tempMin,weatherStatus,date)
                    )
                }
            }
        }

        return forecastList
    }

    private fun convertDateToDay(dateStr: String) : String{
        // Define the input date format
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        try {
            // Parse the input date string into a Date object
            val date = inputFormat.parse(dateStr)

            // Create a Calendar instance and set it to the parsed date
            val calendar = Calendar.getInstance()
            if (date != null) {
                calendar.time = date
            }

            // Get the day of the week (e.g., Calendar.WEDNESDAY)
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

            // Map the Calendar day to the corresponding day name
            val dayName = when (dayOfWeek) {
                Calendar.SUNDAY -> "Sun"
                Calendar.MONDAY -> "Mon"
                Calendar.TUESDAY -> "Tue"
                Calendar.WEDNESDAY -> "Wed"
                Calendar.THURSDAY -> "Thu"
                Calendar.FRIDAY -> "Fri"
                Calendar.SATURDAY -> "Sat"
                else -> dateStr
            }

            return dayName;

        } catch (e: Exception) {
            _error.postValue("Failed to Parse Data. Please try again later.")
            Log.d("parse error", "ERROR: " + e.message)
        }
        return dateStr
    }
}