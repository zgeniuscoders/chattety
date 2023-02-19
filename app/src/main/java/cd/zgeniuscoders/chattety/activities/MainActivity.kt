package cd.zgeniuscoders.chattety.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import cd.zgeniuscoders.chattety.R
import cd.zgeniuscoders.chattety.databinding.ActivityMainBinding
import cd.zgeniuscoders.chattety.fragments.AddPostFragment
import cd.zgeniuscoders.chattety.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_fragment -> {
                    loadFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.post_fragmemt -> {
                    loadFragment(AddPostFragment())
                    return@setOnItemSelectedListener true
                }
                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }

    fun loadFragment(myFragment: Fragment) {
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragment_container, myFragment)
        fragment.commit()
    }
}