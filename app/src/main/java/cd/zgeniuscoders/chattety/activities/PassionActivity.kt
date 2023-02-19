package cd.zgeniuscoders.chattety.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cd.zgeniuscoders.chattety.databinding.ActivityPassionBinding
import cd.zgeniuscoders.chattety.managers.UserManager
import com.google.android.material.chip.Chip

class PassionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPassionBinding
    private lateinit var passionsUser: ArrayList<String>
    private lateinit var userManager: UserManager
    private var passions = arrayListOf<String>(
        "mangas",
        "anime",
        "musiques",
        "films",
        "series",
        "jeux videos",
        "football",
        "basketball",
        "judo",
        "fitness",
        "boxe",
        "actions",
        "porno",
        "vin",
        "bbq",
        "concerts",
        "karate",
        "cinema",
        "tennis",
        "photographie",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        passionsUser = arrayListOf()
        userManager = UserManager()

        for (chip in passions) {
            createChip(chip)
        }

        binding.chipEntryGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val ids = group.checkedChipIds
            for (id in ids) {
                val chip: Chip = group.findViewById(id!!)
                if (chip.isChecked) {
                    passionsUser.add(chip.text as String)
                } else {
                    passionsUser.remove(chip.text as String)
                }
            }
        }

        binding.btnSave.setOnClickListener {
            val hash = HashMap<String, Any>()
            hash["passions"] = passionsUser

            userManager.upDateUser(userManager.getCurrentUser(), hash)
            Intent(this, OnBoardingActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun createChip(name: String) {
        val chip = Chip(this)
        chip.apply {
            text = name
            isCheckedIconVisible = false
            isClickable = true
            isCheckable = true
            binding.apply {
                chipEntryGroup.addView(chip as View)
            }
        }
    }
}