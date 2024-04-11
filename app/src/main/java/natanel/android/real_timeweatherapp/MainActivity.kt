package natanel.android.real_timeweatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import natanel.android.real_timeweatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, WeatherMediatorFragment())
                .commit()
        }
    }
}