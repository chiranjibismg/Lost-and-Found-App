package com.bloodreaper.lostandfound.models

data class PostData(
    val type: String? = null,
    val uid : String? = null,
    val name: String? = null,
    val phone: String? = null,
    val where: String? = null,
    val message: String? = null,
    //var image: Bitmap?=null,
    var imageUrl : String?=null,
)