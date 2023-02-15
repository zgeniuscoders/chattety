package cd.zgeniuscoders.chattety.activities

import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import cd.zgeniuscoders.chattety.MainActivity
import cd.zgeniuscoders.chattety.OnBoardingActivity
import cd.zgeniuscoders.chattety.R
import cd.zgeniuscoders.chattety.databinding.ActivityAuthBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    private companion object {
        private const val RC_SIGN_IN = 100
        private const val RC_SIGN_IN_TAG = "GOOGLE_SIGN_IN_TAG"
    }


    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        auth = FirebaseAuth.getInstance()
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        binding.loginBtn.setOnClickListener {
            Intent(this, SignInActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.loginGoogleBtn.setOnClickListener {
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RC_SIGN_IN) {
            Log.d("AUTH GOOGLE", "GOOGLE SIGNIN INTENT RESULT")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: Exception) {
                Log.d("AUTH GOOGLE", "${e.message}")
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d("AUTH GOOGLE", "FIREBASE AUTH WITH GOOGLE")
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credentials).addOnSuccessListener {
            val currentUser = auth.currentUser
            val uid = currentUser!!.uid
            val email = currentUser.email

            if (it.additionalUserInfo!!.isNewUser) {
//                the new account
                Toast.makeText(this, "CREATE", Toast.LENGTH_LONG).show()
                Intent(this, OnBoardingActivity::class.java).apply {
                    this.putExtra("email", email)
                    this.putExtra("uid", uid)
                    startActivity(this)
                }
            } else {
//                user exist
                Intent(this, MainActivity::class.java).apply {
                    startActivity(this)
                }
                Toast.makeText(this, "EXIST", Toast.LENGTH_LONG).show()
            }
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }.addOnFailureListener {
            Log.d("AUTH GOOGLE ERROR", it.message.toString())
        }
    }
}