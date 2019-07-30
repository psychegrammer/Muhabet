package psychegrammer.example.muhabet.registerlogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import psychegrammer.example.muhabet.R
import psychegrammer.example.muhabet.messages.LatestMessagesActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {

            performLogin()

        }

        back_to_register_textview.setOnClickListener {
            finish()
        }
    }

    private fun performLogin() {
        val email = email_edittext_login.text.toString()
        val password = password_edittext_login.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Molimo vas popunite navedena polja.", Toast.LENGTH_SHORT).show()
            return
        }



        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() {
                if (!it.isSuccessful) return@addOnCompleteListener

                // else if successful
                Log.d("Login", "User with uid: ${it.result?.user?.uid} has successfully logged in.")

                val intent = Intent(this, LatestMessagesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener() {
                Log.d("Login", "User has failed to login: ${it.message}")
            }
    }
}
