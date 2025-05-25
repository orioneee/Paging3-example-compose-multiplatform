package com.oriooneee.paging.presentation.rating

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
                Text("${item.position})")
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
    fun Screen() {
        val vm = remember { RatingViewModel() }
        val data = vm.ratingPager.collectAsLazyPagingItems()

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
                                Text(
                                    "Error: ${e.error.message}",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}