package com.oriooneee.paging.presentation.rating

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.oriooneee.paging.domain.RatingEntity

class RatingScreen {

    @Composable
    fun RatingItem(item: RatingEntity) {
        ListItem(
            headlineContent = {
                Text("${item.name} ${item.surname}")
            },
            leadingContent = {
                Text("${item.id})")
            }
        )
    }

    @Composable
    fun PagingProgressIndicator() {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    @Composable
    fun ErrorState(errorMessage: String, onRetry: () -> Unit) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error
            )
            Button(
                onClick = onRetry,
            ) {
                Text("Retry")
            }
        }
    }

    @Composable
    fun Screen() {
        val vm = remember { RatingViewModel() }
        val data = vm.ratingPager.collectAsLazyPagingItems()
        LaunchedEffect(data.loadState.hasError) {
            println("Load state has error: ${data.loadState.hasError}")
        }
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                if (data.loadState.refresh is LoadState.Loading && data.itemCount == 0) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(data.itemCount) { index ->
                            data[index]?.let { rating ->
                                RatingItem(rating)
                            }
                        }

                        data.apply {
                            when {
                                loadState.refresh is LoadState.Loading -> {
                                    item {
                                        PagingProgressIndicator()
                                    }
                                }

                                loadState.append is LoadState.Loading -> {
                                    item {
                                        PagingProgressIndicator()
                                    }
                                }

                                loadState.refresh is LoadState.Error -> {
                                    val e = loadState.refresh as LoadState.Error
                                    item {
                                        ErrorState(e.error.message ?: "", onRetry = {
                                            data.refresh()
                                        })
                                    }
                                }
                            }
                        }
                        if( data.loadState.append is LoadState.Error) {
                            val e = data.loadState.append as LoadState.Error
                            item {
                                ErrorState(e.error.message ?: "", onRetry = {
                                    data.retry()
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}