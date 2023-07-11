package com.bloodreaper.lostandfound.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bloodreaper.lostandfound.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth


class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.forgotpasswordButton.setOnClickListener{
            val email = binding.emailForgotpass.text.toString()

            if (email.isNotEmpty()) {
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Email sent", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}