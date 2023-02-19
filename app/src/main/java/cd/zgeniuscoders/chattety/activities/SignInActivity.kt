package cd.zgeniuscoders.chattety.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import cd.zgeniuscoders.chattety.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var builder: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        builder =
            AlertDialog.Builder(this).setTitle("Chargement...").setMessage("Veuillez patienter")
                .setCancelable(false).create()

        binding.register.setOnClickListener {
            Intent(this, SignUpActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.emailAddress.text
            val password = binding.password.text
            if (email!!.isNotEmpty() && password!!.isNotEmpty()) {
                builder.show()
                signIn(email.toString(), password.toString())
            } else {
                builder.dismiss()
                binding.errorTxt.visibility = View.VISIBLE
                binding.errorTxt.text = "Veuillez remplir tout les champs"
            }

        }

    }

    private fun signIn(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                builder.dismiss()
                savePreferences()
                Intent(this, MainActivity::class.java).apply {
                    startActivity(this)
                }
            } else {
                builder.dismiss()
                when (task.exception!!.message) {
                    "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> {
                        binding.errorTxt.visibility = View.VISIBLE
                        binding.errorTxt.text = "Verifier votre connexion internet et reeseyer"
                    }
                    else -> {
                        binding.errorTxt.visibility = View.VISIBLE
                        binding.errorTxt.text = "Address email ou Mot de passe incorect"
                    }
                }
                // If sign in fails, display a message to the user.
                Log.e("AUTH", "signInWithEmail:failure", task.exception)

            }
        }
    }

    private fun savePreferences() {
        sharedPreferences = applicationContext.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isAuth", true)
        editor.apply()
    }
}