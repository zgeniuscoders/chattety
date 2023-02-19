package cd.zgeniuscoders.chattety.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cd.zgeniuscoders.chattety.R
import cd.zgeniuscoders.chattety.adapters.onBoardingDataViewPagerAdapter
import cd.zgeniuscoders.chattety.databinding.ActivityOnBoardingBinding
import cd.zgeniuscoders.chattety.models.OnBoardingData
import com.google.android.material.tabs.TabLayout

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    private lateinit var onBoardingDataViewPagerAdapter: onBoardingDataViewPagerAdapter
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        val onBoardingData: MutableList<OnBoardingData> = ArrayList()
        onBoardingData.add(
            OnBoardingData(
                "Bienvenu sur chattety",
                "Un centre de formation en informatique offrant des presestations de services de qualité pour une maîtrise des technologies de pointe",
                R.drawable.onboardbg1
            )
        )
        onBoardingData.add(
            OnBoardingData(
                "Qui somme-nous ?",
                "Nous somme un centre de formation en informatique specialement conçu pour offrir des formations de qualité à tous les niveaux," +
                        "des débutants aux professionnels expérimentés.\n\n" +
                        "Nous proposons des cours sur les derniéres technologies et tendances dans le domain de l'informatique,afin de permettre à nos étudiants de rester comptétitifs sur le" +
                        " marché du travail",
                R.drawable.onboardbg2
            )
        )
        onBoardingData.add(
            OnBoardingData(
                "Notre equipe",
                "Notre équipe est formée de professionnels expérmimentés et dévoués," +
                        " qui ont une grande expertise dans leur domaine respectif\n\nChacun de nos membres a une forte expérience et une formation pour garantir une qualité de service supérieure\n\n" +
                        "Nous sommes fiers de travailler ensemble pour atteindre les objectifs de nos clients\n\n" +
                        "Ensemble nous sommes une force pour réaliser s",
                R.drawable.onboardbg3
            )
        )

        setOnBoardingDataAdapter(onBoardingData)

        binding.skipBtn.setOnClickListener {
            Intent(applicationContext, MainActivity::class.java).apply {
                startActivity(this)
            }
        }

        position = binding.viewPager.currentItem
        binding.nextBtn.setOnClickListener {
            if (position < onBoardingData.size) {
                position++
                binding.viewPager.currentItem = position
            }
            if (position == onBoardingData.size) {
                Intent(applicationContext, MainActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position = tab!!.position
                if (tab.position == onBoardingData.size - 1) {
                    binding.nextBtn.text = "Commencer"
                } else {
                    binding.nextBtn.text = "Suivant"
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

    }

    private fun setOnBoardingDataAdapter(onBoardingData: MutableList<OnBoardingData>) {
        onBoardingDataViewPagerAdapter = onBoardingDataViewPagerAdapter(this, onBoardingData)
        binding.viewPager.adapter = onBoardingDataViewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

}