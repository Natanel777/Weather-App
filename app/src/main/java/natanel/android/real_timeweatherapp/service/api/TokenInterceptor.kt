package natanel.android.real_timeweatherapp.service.api

import natanel.android.real_timeweatherapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val apiKey:String = BuildConfig.WEATHER_API_KEY) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val celsius = "metric"

        //1) get the original request
        val original = chain.request()

        //2) add api_key query param to the url  (https://api.openweathermap.org/data/2.5/weather?)
        val url = original.url.newBuilder()
            .addQueryParameter("appid", apiKey)
            .addQueryParameter("units", celsius) // results in celsius
            .build()

        //replace the url of the original request:
        val request = original.newBuilder().url(url).build()

        //continue with the chain
        //continue to the next interceptor/server
        return chain.proceed(request)
    }
}