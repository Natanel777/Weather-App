package natanel.android.real_timeweatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import natanel.android.real_timeweatherapp.databinding.FragmentWeatherMediatorBinding
import natanel.android.real_timeweatherapp.utils.hasInternetConnection
import natanel.android.real_timeweatherapp.ui.NoInternetFragment
import natanel.android.real_timeweatherapp.ui.WeatherFragment


class WeatherMediatorFragment : Fragment()  {

    private var _binding : FragmentWeatherMediatorBinding? = null
    private val binding : FragmentWeatherMediatorBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherMediatorBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(hasInternetConnection()) {
            childFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_weather_mediator, WeatherFragment.newInstance())
                .commit()
        }
        else {
            childFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_weather_mediator, NoInternetFragment())
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}