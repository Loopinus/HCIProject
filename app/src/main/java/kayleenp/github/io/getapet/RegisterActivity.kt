package kayleenp.github.io.getapet

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApiNotAvailableException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        button_register.setOnClickListener{
            val email = text_email.text.toString().trim()
            val password = edit_text_password.text.toString().trim()

            if(email.isEmpty()){
                text_email.error = "Please Input Your Email Address"
                text_email.requestFocus()
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                text_email.error = "Please Input A Valid Email Address"
                text_email.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty() || password.length < 6){
                edit_text_password.error = "A minimum of 6 characters is required"
                edit_text_password.requestFocus()
                return@setOnClickListener
            }

            registerUser(email, password)

        }

        text_view_login.setOnClickListener{
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }

    private fun registerUser(email: String, password: String) {

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){

                    val intent = Intent(this@RegisterActivity, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }

                    startActivity(intent)

                }else {
                    task.exception?.message?.let {
                        toast(it)
                    }
                }
                }

            }

    }