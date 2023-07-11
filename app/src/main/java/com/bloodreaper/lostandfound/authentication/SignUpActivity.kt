package com.bloodreaper.lostandfound.authentication

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bloodreaper.lostandfound.MainActivity
import com.bloodreaper.lostandfound.databinding.ActivitySignUpBinding
import com.bloodreaper.lostandfound.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var firebaseAuth: FirebaseAuth
    private var pickedPhoto : Uri? = null
    private var pickedBitMap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("user")
        firebaseAuth = FirebaseAuth.getInstance()
        registerUser()

    }


    private fun registerUser(){
        binding.signupButton.setOnClickListener {
            val email = binding.signupEmail.text.toString()
            val pass = binding.signupPass.text.toString()
            val confPass = binding.signupConfirmpass.text.toString()
            val name = binding.signupName.text.toString()
            val roll = binding.signupRoll.text.toString()
            val phone = binding.signupPhone.text.toString()
            val whatsApp = binding.signupWhatsapp.text.toString()
            val user = User(name, roll, email, phone, whatsApp)
            val re = Regex("^[a-zA-Z0-9+_.-]+@iitp.ac.in+$")
            val valid = email.matches(re)
            if (email.isNotEmpty() && pass.isNotEmpty() && confPass.isNotEmpty() && name.isNotEmpty() && roll.isNotEmpty() && phone.isNotEmpty() && whatsApp.isNotEmpty()&&valid) {
                if (pass == confPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { authResultTask ->
                        if (authResultTask.isSuccessful) {
                            val uid = databaseReference.push().key
                            if (uid != null) {
                                databaseReference.child(uid).setValue(user).addOnCompleteListener {
                                    if (it.isSuccessful){
                                        uploadProfilePic()
                                        Toast.makeText(this,"Successfully Signed up", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                    }else{
                                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }else{
                                Toast.makeText(this, authResultTask.exception.toString(), Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, authResultTask.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                if(valid) Toast.makeText(this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "Only IITP mail domain is supported", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun pickPhoto(view: View){
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1)
        } else {
            val gallery_intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(gallery_intent,2)
        }
    }


    private fun uploadProfilePic(){
        storageReference = FirebaseStorage.getInstance().getReference("Profile/"+firebaseAuth.currentUser?.uid)
        val imageUri = pickedPhoto;
        if (imageUri != null) {
            storageReference.putFile(imageUri).addOnSuccessListener{
                Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed to upload", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            pickedPhoto = data.data
            if (pickedPhoto != null) {
                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(this.contentResolver,pickedPhoto!!)
                    pickedBitMap = ImageDecoder.decodeBitmap(source)
                    upload_img_button.setImageBitmap(pickedBitMap)
                }
                else {
                    pickedBitMap = MediaStore.Images.Media.getBitmap(this.contentResolver,pickedPhoto)
                    upload_img_button.setImageBitmap(pickedBitMap)
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
