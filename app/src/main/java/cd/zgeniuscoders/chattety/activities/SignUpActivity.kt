package cd.zgeniuscoders.chattety.activities

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import cd.zgeniuscoders.chattety.R
import cd.zgeniuscoders.chattety.databinding.ActivitySignUpBinding
import cd.zgeniuscoders.chattety.managers.UserManager
import cd.zgeniuscoders.chattety.models.User
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var genders: ArrayList<String>
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userMananger: UserManager
    private lateinit var builder: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        userMananger = UserManager()
        builder = AlertDialog.Builder(this)
            .setTitle("Chargement...")
            .setMessage("Veuillez patienter")
            .setCancelable(false)
            .create()

        setGender()

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            upDateInputCalendar(myCalendar)
        }

        binding.birthday.setOnClickListener {
            DatePickerDialog(
                this,
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.login.setOnClickListener {
            Intent(this, SignInActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.btnSignUp.setOnClickListener {
            val email = binding.emailAddress.text
            val username = binding.username.text
            val password = binding.password.text
            val birthday = binding.birthday.text

            if (email!!.isNotEmpty() && password!!.isNotEmpty() && username!!.isNotEmpty() && birthday!!.isNotEmpty()) {
                if (password.length < 6) {
                    binding.errorTxt.visibility = View.VISIBLE
                    binding.errorTxt.text = "Le mot de passe doit contenir plus de 6 caracteres"
                } else {
                    builder.show()
                    signUp(email.toString(), password.toString())
                }
            } else {
                builder.dismiss()
                binding.errorTxt.visibility = View.VISIBLE
                binding.errorTxt.text = "Veuillez remplir tout les champs"
            }
        }
    }

    private fun setGender() {
        genders = ArrayList<String>()
        genders.add("masculin")
        genders.add("feminin")
        val arrayAdapter =
            ArrayAdapter(this, R.layout.dropdownn_item, genders)
        binding.gender.adapter = arrayAdapter

    }

    private fun upDateInputCalendar(myCalendar: Calendar) {
        val dateFormat = "dd/mm/yyyy"
        val sdf = SimpleDateFormat(dateFormat, Locale.UK)
        val date = sdf.format(myCalendar.time)
        binding.birthday.setText(date)
    }

    private fun signUp(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    saveUserToFirestore()
                } else {
                    builder.dismiss()
                    when (task.exception!!.message) {
                        "The email address is already in use by another account." -> {
                            binding.errorTxt.visibility = View.VISIBLE
                            binding.errorTxt.text = "Cette address email a deja ete utiliser"
                        }
                        "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> {
                            binding.errorTxt.visibility = View.VISIBLE
                            binding.errorTxt.text = "Verifier votre connexion internet et reeseyer"
                        }
                    }
                    // If sign in fails, display a message to the user.
                    Log.e("AUTH", "createUserWithEmail:failure", task.exception)
                }
            }
    }

    private fun saveUserToFirestore() {

        // save the user in firestore
        userMananger.createUser(
            User(
                id = userMananger.getCurrentUser(),
                username = binding.username.text.toString(),
                email = binding.emailAddress.text.toString(),
                birthday = binding.birthday.text.toString(),
                gender = genders[binding.gender.selectedItemPosition]
            )
        )

        // save auth share preferences
        savePreferences()
        Intent(this, UserPreferenceActivity::class.java).apply {
            startActivity(this)
        }

    }

    private fun savePreferences() {
        sharedPreferences = applicationContext.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isAuth", true)
        editor.apply()
    }

}