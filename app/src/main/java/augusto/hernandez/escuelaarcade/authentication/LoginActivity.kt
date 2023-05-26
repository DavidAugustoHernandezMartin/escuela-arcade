package augusto.hernandez.escuelaarcade.authentication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import augusto.hernandez.escuelaarcade.databinding.ActivityLoginBinding
import augusto.hernandez.escuelaarcade.navigation.MainActivity
import com.firebase.ui.auth.AuthUI
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            login = this@LoginActivity
        }

        // Trigger authentication
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if(user == null){
            openSomeActivityForResult()
        }
        else {
            val context:Context = this
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
     fun openSomeActivityForResult() {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        // Create sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .build()

        // Launch sign-in intent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Successfully signed in
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        } else {
            // Sign in failed.
            Snackbar.make(
                binding.root,
                "No se ha podido iniciar sesión",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}