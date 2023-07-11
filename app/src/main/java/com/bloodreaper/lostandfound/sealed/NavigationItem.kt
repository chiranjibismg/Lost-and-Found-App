package com.bloodreaper.lostandfound.sealed

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(
    val route: String,
    val label: String,
    val icons: ImageVector
    ) {
    object Lost : NavigationItem(
        "LostFragment",
        "Lost",
        Icons.Default.Search)
    object Mine : NavigationItem(
        "MyPostsFragment",
        "Mine",
        Icons.Default.Person)
    object  Found : NavigationItem(
        "FoundFragment",
        "Found",
        Icons.Default.LocationOn)

}
