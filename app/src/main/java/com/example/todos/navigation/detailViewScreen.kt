package com.example.todos.navigation

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.todos.R
import com.example.todos.model.Products
import com.example.todos.viewmodel.ProductsViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun detailViewScreen(
    modifier: Modifier = Modifier,
    data: Products,
) {
    val pagerState = rememberPagerState {
        data.images.size
    }
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
//        LaunchedEffect(Unit) { while (true) {
//                delay(2000)
//                val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
//                pagerState.scrollToPage(nextPage)
//            }
//        }
        Column(
            modifier
                .fillMaxSize()
                .padding(15.dp),
        ) {
            CustomTopAppBar(
                currentScreen =  stringResource(R.string.task_view),
                showBackButton = false,
                onBackButtonClick = { }
            )
            Column(
                modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                HorizontalPager(
                    state = pagerState,
                    modifier
                        .fillMaxWidth()
                        .padding(26.dp)
                ) { currentPage ->
                    Card(
                        modifier
                            .fillMaxWidth()
                            .padding(26.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(data.thumbnail[currentPage]),
                            contentDescription = null,
                            Modifier.fillMaxWidth()
                        )
                    }

                }

            }
            
            Text(text = "Model : ${data.title}")
            Text(text = "Category : ${data.category}")
            Text(text = "Description : ${data.description}")
            Text(text = "Brand : ${data.brand}")
            Text(text = "Price : ${data.price}")
            Text(text = "Discount : ${data.discountPercentage}")
            Text(text = "Rating : ${data.rating}")
        }



    }

}