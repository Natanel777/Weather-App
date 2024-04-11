package natanel.android.real_timeweatherapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import natanel.android.real_timeweatherapp.R
import natanel.android.real_timeweatherapp.databinding.FragmentWeatherBinding
import natanel.android.real_timeweatherapp.ui.adapter.ForecastAdapter
import java.util.Calendar
import kotlin.math.roundToInt

fun  <T: Any> LiveData<T?>.observeNotNull(
    owner: LifecycleOwner,
    observer: Observer<in T>
) {
    observe(owner) {
        if(it == null) return@observe
        observer.onChanged(it)
    }

}

class WeatherFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using view binding
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        //Current Weather
        weatherViewModel.currentWeather.observeNotNull(viewLifecycleOwner) { weather ->
                val celsius = getString(R.string.celsius)
                val percentage = getString(R.string.percentage)

                with(binding) {

                    locationText.text = weather.name // Location
                    weatherStatusText.text = weather.weather[0].main // status
                    tempText.text = weather.main.temp.roundToInt().toString() + celsius // temp
                    highTempText.text =
                        weather.main.tempMax.roundToInt().toString() + celsius // high temp
                    lowTempText.text =
                        weather.main.tempMin.roundToInt().toString() + celsius // low temp
                    feelsLikeText.text =
                        weather.main.feelsLike.roundToInt().toString() + celsius // feels like
                    humidityText.text = weather.main.humidity.toString() + percentage // humidity
                    pressureText.text = weather.main.pressure.toString() // pressure
                    windText.text = weather.wind.speed.toString() + "m/s" // wind
                    cloudsText.text = weather.clouds.all.toString() + percentage // clouds

                    //weather Image
                    val calendar = Calendar.getInstance()
                    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)  // Get current hour

                    when (weather.weather[0].main) {
                        "Clear" -> {
                            if (currentHour in 6..19) {
                                Picasso.get().load(R.drawable.sun).into(weatherMainImage)
                            } else {
                                Picasso.get().load(R.drawable.moon).into(weatherMainImage)
                            }
                        }

                        "Clouds" -> {
                            if (currentHour in 6..19) {
                                Picasso.get().load(R.drawable.clouds).into(weatherMainImage)
                            } else {
                                Picasso.get().load(R.drawable.clouds_moon).into(weatherMainImage)
                            }
                        }

                        "Rain" -> Picasso.get().load(R.drawable.rain).into(weatherMainImage)
                        "Thunderstorm" -> Picasso.get().load(R.drawable.thunderstorm).into(weatherMainImage)
                        "Mist" -> Picasso.get().load(R.drawable.mist).into(weatherMainImage)
                        "Snow" -> Picasso.get().load(R.drawable.snow).into(weatherMainImage)
                        "Drizzle" -> Picasso.get().load(R.drawable.drizzle).into(weatherMainImage)
                        else -> {
                            Picasso.get().load(R.drawable.sun).into(weatherMainImage)
                        }
                    }
            }
        }
        //Forecast
        weatherViewModel.forecast.observe(viewLifecycleOwner) { forecast ->
                with(binding.foracstRecyclerView) {
                    adapter = ForecastAdapter(forecast)
                    layoutManager = LinearLayoutManager(context)
                }
        }

        //error
        weatherViewModel.error.observeNotNull(viewLifecycleOwner) { error ->
                // Handle error - Show an error message to the user
                Snackbar.make(view,  "$error Please Try Again", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}