package cd.zgeniuscoders.chattety.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cd.zgeniuscoders.chattety.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        binding.loginBtn.setOnClickListener {
            Intent(this, SignInActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}