package com.bloodreaper.lostandfound.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bloodreaper.lostandfound.MainActivity
import com.bloodreaper.lostandfound.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.signupButton.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.forgotpasswordButton.setOnClickListener{
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        binding.loginButton.setOnClickListener {
            val email = binding.emailLogin.text.toString()
            val password = binding.loginpass.text.toString()
            val re = Regex("^[a-zA-Z0-9+_.-]+@iitp.ac.in+$")
            val valid = email.matches(re)
            if (email.isNotEmpty() && password.isNotEmpty() && valid) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Only IITP mail domain is supported", Toast.LENGTH_SHORT).show()
            }
        }
    }
}