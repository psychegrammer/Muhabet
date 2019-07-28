package psychegrammer.example.muhabet

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            val email = email_edittext_login.text.toString()
            val password = password_edittext_login.text.toString()

            Log.d("Login", "Attempt login with email/pw: $email/***")

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() {
                    if (!it.isSuccessful) return@addOnCompleteListener

                    // else if successful
                    Log.d("RegisterActivity", "User with uid: ${it.result?.user?.uid} has successfully logged in.")
                }
                .addOnFailureListener() {
                    Log.d("RegisterActivity", "User has failed to login: ${it.message}")
                }
        }

        back_to_register_textview.setOnClickListener {
            finish()
        }
    }
}
