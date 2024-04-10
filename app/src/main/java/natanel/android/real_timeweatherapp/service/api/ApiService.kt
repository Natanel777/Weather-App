package natanel.android.real_timeweatherapp.service.api

import natanel.android.real_timeweatherapp.service.model.current_weather.CurrentWeatherResponse
import natanel.android.real_timeweatherapp.service.model.forecast_weather.ForecastResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat:Double = 32.089870,
        @Query("lon") lon:Double = 34.880450
    ) : CurrentWeatherResponse

    @GET("forecast")
    suspend fun getForecast(
        @Query("lat") lat:Double = 32.089870,
        @Query("lon") lon:Double = 34.880450
    ) : ForecastResponse




    companion object {
        fun create(): ApiService {

            //add the api key and the units
            val client = OkHttpClient.Builder()
                .addInterceptor(TokenInterceptor())
                .build()


            //use the client with retrofit
            return Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(
                    ApiService::
                    class.java
                )
        }
    }
}