package cd.zgeniuscoders.chattety

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import cd.zgeniuscoders.chattety.databinding.ActivitySlashBinding

class SlashActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var binding: ActivitySlashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySlashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        supportActionBar?.hide()
        handler = Handler(Looper.getMainLooper())

        handler.postDelayed({
            Intent(this, OnBoardingActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }, 5000)
    }
}