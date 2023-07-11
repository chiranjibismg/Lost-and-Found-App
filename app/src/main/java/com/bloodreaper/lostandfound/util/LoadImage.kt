package com.bloodreaper.lostandfound.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.bloodreaper.lostandfound.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val DEFAULT_RECIPE_IMAGE = R.drawable.cycle

@ExperimentalCoroutinesApi
@Composable
fun loadPicture(url: String, @DrawableRes defaultImage: Int): MutableState<Bitmap?> {

    val bitmapState: MutableState<Bitmap?> =  remember {
        mutableStateOf(null)
    }

    // show default image while image loads
    Glide.with(LocalContext.current)
        .asBitmap()
        .load(defaultImage)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) { }
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                bitmapState.value = resource
            }
        })

    // get network image
    Glide.with(LocalContext.current)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) { }
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                bitmapState.value = resource
            }
        })

    return bitmapState
}



















//private lateinit var bitmap: Bitmap
//@ExperimentalCoroutinesApi
//@Composable
//fun loadPicture(): MutableState<Bitmap?> {
//    val context = LocalContext.current
//    val storageRef = FirebaseStorage.getInstance().reference.child("Found/hello.jpg")
//    val bitmapState: MutableState<Bitmap?> = remember{
//        mutableStateOf(null)
//    }
//    bitmap = createBitmap(500,500,Bitmap.Config.ARGB_8888)
//    // show default image while image loads
//    Glide.with(LocalContext.current)
//        .asBitmap()
//        .load(DEFAULT_RECIPE_IMAGE)
//        .into(object : CustomTarget<Bitmap>() {
//            override fun onLoadCleared(placeholder: Drawable?) { }
//            override fun onResourceReady(
//                resource: Bitmap,
//                transition: Transition<in Bitmap>?
//            ) {
//                bitmapState.value = resource
//            }
//        })
//    val localFile = File.createTempFile("tempImage",".jpg")
//    storageRef.getFile(localFile).addOnSuccessListener {
//        bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
//        Toast.makeText(context,"DONE",
//            Toast.LENGTH_SHORT).show()
//    }.addOnFailureListener{
//        Toast.makeText(context,"Failed",
//            Toast.LENGTH_SHORT).show()
//    }
//    // get network image
//    Glide.with(LocalContext.current)
//        .asBitmap()
//        .load(bitmap)
//        .into(object : CustomTarget<Bitmap>() {
//            override fun onLoadCleared(placeholder: Drawable?) { }
//            override fun onResourceReady(
//                resource: Bitmap,
//                transition: Transition<in Bitmap>?
//            ) {
//                bitmapState.value = resource
//            }
//        })
//
//    return bitmapState
//}