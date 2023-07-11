package com.bloodreaper.lostandfound.main.found


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bloodreaper.lostandfound.models.PostData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class PostForFoundItemFragment : Fragment() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseDatabase : FirebaseDatabase
    private lateinit var storageReference: StorageReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally)

                {
                    val name = inputTextField("Name")
                    val phone = inputTextField("Phone")
                    val whereLost = inputTextField("Where Found?")
                    val message = inputTextField("Message")
                    val imageUrl = inputTextField(label = "Image url")
                    Spacer(modifier = Modifier.padding(16.dp))
                    //val imageUri = RequestContentPermission()
                    Button(
                        onClick = {
                            firebaseDatabase = FirebaseDatabase.getInstance()
                            databaseReference = firebaseDatabase.getReference("PostData")
                            val uid = databaseReference.push().key
                            storageReference = FirebaseStorage.getInstance().getReference("Found/"+"hello.jpg")

//                            if (imageUri != null) {
//                                storageReference.putFile(imageUri).addOnSuccessListener{
//                                    Toast.makeText(requireContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show()
//                                }.addOnFailureListener{
//                                    Toast.makeText(requireContext(),"Failed to upload", Toast.LENGTH_SHORT).show()
//                                }
//                            }
                            val lost = PostData(
                                name = name,
                                phone = phone,
                                where = whereLost,
                                message = message,
                                type = "found",
                                uid = FirebaseAuth.getInstance().uid,
                                imageUrl = imageUrl
                            )

                            if(name.isNotEmpty() && phone.isNotEmpty() && whereLost.isNotEmpty()
                                && message.isNotEmpty()){

                                if (uid != null) {
                                    databaseReference.child(uid).setValue(lost).addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            Toast.makeText(context,"Found item reported",
                                                Toast.LENGTH_SHORT).show()
                                            findNavController().navigate(PostForFoundItemFragmentDirections.actionPostForFoundItemFragmentToFoundFragment())
                                        } else {
                                            Toast.makeText(context, it.exception.toString(),
                                                Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }else{
                                Toast.makeText(context,"Empty fields are not allowed",
                                    Toast.LENGTH_SHORT).show()
                            }
                        },
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(text = "Post For Found Item",
                        )

                    }
                }
            }

        }

    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun inputTextField(label : String): String {
        var text by remember { mutableStateOf("") }

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(label)  },
            textStyle = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        return text
    }

}