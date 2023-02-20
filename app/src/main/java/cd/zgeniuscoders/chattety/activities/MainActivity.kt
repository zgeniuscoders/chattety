package cd.zgeniuscoders.chattety.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import cd.zgeniuscoders.chattety.R
import cd.zgeniuscoders.chattety.databinding.ActivityMainBinding
import cd.zgeniuscoders.chattety.fragments.AddPostFragment
import cd.zgeniuscoders.chattety.fragments.HomeFragment
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private var drawerToggle: ActionBarDrawerToggle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerToggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(drawerToggle!!)
        drawerToggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.navigationView.setNavigationItemSelectedListener(this)


        val navController = findNavController(R.id.fragment_container)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {

            }
            R.id.developper -> {

            }
        }
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (drawerToggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.close()
        }
    }

}