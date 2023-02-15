package cd.zgeniuscoders.chattety.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import cd.zgeniuscoders.chattety.R
import cd.zgeniuscoders.chattety.databinding.ActivityUserPreferenceBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserPreferenceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserPreferenceBinding
    private lateinit var ages: ArrayList<String>
    private lateinit var peopels: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        agesPreferences()
        peopelsPreferences()

        binding.btnSave.setOnClickListener {
            saveUserData()
        }
    }

    private fun saveUserData() {

        val age = ages[binding.ages.selectedItemPosition]
        val peeople = peopels[binding.peopleType.selectedItemPosition]

        val hash = HashMap<String, Any>()
        hash["agePreference"] = age
        hash["peoplePreference"] = peeople

        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        Firebase.firestore.collection("users").document(uid).update(hash)
        Intent(this, PassionActivity::class.java).apply {
            startActivity(this)
        }
    }

    fun agesPreferences() {
        ages = ArrayList<String>()
        ages.add(0, "Selectionner les tranches d'ages qui vous interresse")
        ages.add(1, "18-25")
        ages.add(2, "25-30")
        ages.add(3, "30-35")
        ages.add(4, "35-40")
        ages.add(5, "40-50")

        val arrayAdapter = ArrayAdapter(this, R.layout.dropdownn_item, ages)
        binding.ages.adapter = arrayAdapter
    }

    fun peopelsPreferences() {
        peopels = ArrayList<String>()
        peopels.add(0, "Je veux voir")
        peopels.add(1, "DES FEMMES")
        peopels.add(2, "DES HOMMES")
        peopels.add(3, "TOUT LE MONDE")

        val arrayAdapter = ArrayAdapter(this, R.layout.dropdownn_item, peopels)
        binding.peopleType.adapter = arrayAdapter
    }

}