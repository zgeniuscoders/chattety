package cd.zgeniuscoders.chattety.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cd.zgeniuscoders.chattety.databinding.ActivityRegisterWithGoogleBinding

class RegisterWithGoogleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterWithGoogleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterWithGoogleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}