package com.bloodreaper.lostandfound.sealed

import com.bloodreaper.lostandfound.models.PostData

sealed class PostState{
    class Success(val data : MutableList<PostData>) : PostState()
    class Failure(val message : String) : PostState()
    object Loading : PostState()
    object Empty : PostState()
}
