package com.bloodreaper.lostandfound.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bloodreaper.lostandfound.models.PostData
import com.bloodreaper.lostandfound.sealed.PostState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class YourViewModel : ViewModel() {
    val response : MutableState<PostState> = mutableStateOf(PostState.Empty)
    init {
        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        val tmpList = mutableListOf<PostData>()
        response.value = PostState.Loading
        val uid = FirebaseAuth.getInstance().uid
        FirebaseDatabase.getInstance().getReference("PostData")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(values in snapshot.children){
                        val post = values.getValue(PostData::class.java)
                        if(post!=null&&post.uid==uid){
                            tmpList.add(post)
                        }
                    }
                    response.value = PostState.Success(tmpList)
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = PostState.Failure(error.message)
                }
            }
            )
    }
}