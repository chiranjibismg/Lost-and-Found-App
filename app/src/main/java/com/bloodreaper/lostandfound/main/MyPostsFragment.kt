package com.bloodreaper.lostandfound.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bloodreaper.lostandfound.models.PostData
import com.bloodreaper.lostandfound.presentation.ItemCards
import com.bloodreaper.lostandfound.sealed.PostState
import com.bloodreaper.lostandfound.ui.theme.LazyListTheme
import com.bloodreaper.lostandfound.viewmodel.YourViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MyPostsFragment : Fragment() {
    private val viewModel: YourViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                LazyListTheme {
                    Column(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                        SetData(viewModel)

                        Spacer(modifier = Modifier.padding(16.dp))
                    }

                }

            }
        }

    }
}

@Composable
fun SetData(viewModel: YourViewModel) {
    when (val result = viewModel.response.value) {
        is PostState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is PostState.Success -> {
            ShowLazyList(result.data)
        }
        is PostState.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = result.message,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                )
            }
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error Fetching data",
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                )
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ShowLazyList(mutableList : MutableList<PostData>) {
    LazyColumn {
        items(mutableList) { post ->
            ItemCards(post)
        }
    }
}
