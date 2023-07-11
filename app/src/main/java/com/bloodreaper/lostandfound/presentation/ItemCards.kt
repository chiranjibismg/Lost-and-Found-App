package com.bloodreaper.lostandfound.presentation

import android.graphics.Bitmap
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bloodreaper.lostandfound.R
import com.bloodreaper.lostandfound.models.PostData
import com.bloodreaper.lostandfound.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

private lateinit var bitmap: Bitmap
@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalCoroutinesApi
@Composable
fun ItemCards(
    post: PostData,
){
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if(expandedState) 180f else 0f)

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            val image =loadPicture(post.imageUrl!!, R.drawable.empty_plate).value
            if (image != null) {
                Image(
                    bitmap = image.asImageBitmap(),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(6f),
                    text = post.where!!,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    modifier = Modifier
                        .alpha(DefaultAlpha)
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    Icon(imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop down")
                    
                }
            }
            if(expandedState){
                Column(modifier = Modifier
                    .fillMaxWidth()) {
                    Text(
                        text = "Owner's message",
                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        modifier = Modifier.padding(bottom = 4.dp),
                        textDecoration = TextDecoration.Underline)
                    Text(
                        text = post.message!!,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Justify
                        )
                    Text(
                        text = "Contact Details",
                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        modifier = Modifier.padding(bottom = 4.dp),
                        textDecoration = TextDecoration.Underline)
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = post.name!!,
                            modifier = Modifier.weight(3f),
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(text = post.phone!!,
                            modifier = Modifier.weight(1f),
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                        )
                    }
                }
            }
        }
        
    }
}

@ExperimentalMaterial3Api
@Composable
@Preview
fun ItemCardsPreview(){
    ItemCards(PostData("Lost"
        ,"sdf",
        "PM",
        "89xxxxxxx56"
        ,"Libaray"
        ,"agar mile to please batana mughe bahut zauruart hai",
        "adsf"))
}
