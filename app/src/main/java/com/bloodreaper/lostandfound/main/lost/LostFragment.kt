package com.bloodreaper.lostandfound.main.lost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bloodreaper.lostandfound.models.PostData
import com.bloodreaper.lostandfound.presentation.ItemCards
import com.bloodreaper.lostandfound.sealed.PostState
import com.bloodreaper.lostandfound.ui.theme.LazyListTheme
import com.bloodreaper.lostandfound.viewmodel.LostPostViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

class LostFragment : Fragment() {

    private val viewModel: LostPostViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                LazyListTheme {
                    Column(modifier = androidx.compose.ui.Modifier.padding(8.dp).fillMaxWidth()) {
                        SetData(viewModel)
                        Spacer(modifier = androidx.compose.ui.Modifier.padding(16.dp))
                    }

                }
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    FloatingActionButton(
                        modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                        onClick = {
                            findNavController().navigate(LostFragmentDirections.actionLostFragmentToPostForLostItemFragment())
                        }
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "add",
                            //tint = Color.Blue
                        )
                    }
                }

            }
        }
    }

    @Composable
    fun SetData(viewModel: LostPostViewModel) {
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

}