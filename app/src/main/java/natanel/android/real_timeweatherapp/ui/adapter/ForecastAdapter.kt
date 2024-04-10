package natanel.android.real_timeweatherapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import natanel.android.real_timeweatherapp.R
import natanel.android.real_timeweatherapp.databinding.ForecastItemBinding
import natanel.android.real_timeweatherapp.service.model.forecast_weather.forecastData.ForecastItemData

class ForecastAdapter(private val forecastData : List<ForecastItemData>) :
RecyclerView.Adapter<ForecastAdapter.ForecastItem>( ){
    class ForecastItem(val binding: ForecastItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastItem {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ForecastItemBinding.inflate(inflater, parent, false)
        return ForecastItem(binding)
    }

    override fun getItemCount(): Int {
        return forecastData.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ForecastItem, position: Int) {

        val item = forecastData[position]
        with(holder.binding){
            dayText.text = item.date
            lowForecastText.text = item.tempMin +"°C"
            highForecastText.text = item.tempMax +"°C"

            when (item.weatherStatus) {
                "Clear" -> Picasso.get().load(R.drawable.sun).into(statusImage)
                "Clouds" -> Picasso.get().load(R.drawable.clouds).into(statusImage)
                "Rain" -> Picasso.get().load(R.drawable.rain).into(statusImage)
                "Thunderstorm" -> Picasso.get().load(R.drawable.thunderstorm).into(statusImage)
                "Mist" -> Picasso.get().load(R.drawable.mist).into(statusImage)
                "Snow" -> Picasso.get().load(R.drawable.snow).into(statusImage)
                "Drizzle" -> Picasso.get().load(R.drawable.drizzle).into(statusImage)
                else -> {
                    Picasso.get().load(R.drawable.sun).into(statusImage)
                }
            }
        }
    }
}