package cd.zgeniuscoders.chattety.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import cd.zgeniuscoders.chattety.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        binding.register.setOnClickListener {
            Intent(this, SignUpActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.emailAddress.text
            val password = binding.password.text
            if (email!!.isNotEmpty() && password!!.isNotEmpty()) {
                signIn(email.toString(), password.toString())
            } else {
                binding.errorTxt.visibility = View.VISIBLE
                binding.errorTxt.text = "Veuillez remplir tout les champs"
            }

        }

    }

    private fun signIn(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    savePreferences()
                } else {
                    binding.errorTxt.visibility = View.VISIBLE
                    binding.errorTxt.text = "Address email ou Mot de passe incorect"
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