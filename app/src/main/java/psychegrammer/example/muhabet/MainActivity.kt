package psychegrammer.example.muhabet

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register_button_register.setOnClickListener {
            performRegister()
        }

        already_have_account_textview.setOnClickListener {
            Log.d("MainActivity", "Try to show login activity")

            // launch the login activity somehow
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
    }

    private fun performRegister() {
        val email = email_edittext_register.text.toString()
        val password = password_edittext_register.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Molimo vas unesite vaše podatke.", Toast.LENGTH_SHORT).show()
        }

        Log.d("MainActivity", "Email is: " + email)
        Log.d("MainActivity", "Password:  $password")

        // Firebase Authentication to create a user with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!it.isSuccessful) return@addOnCompleteListener

            // else if successful
            Log.d("MainActivity", "Successfully created user with uid: ${it.result?.user?.uid}")
        }
            .addOnFailureListener {
                Log.d("Main", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
