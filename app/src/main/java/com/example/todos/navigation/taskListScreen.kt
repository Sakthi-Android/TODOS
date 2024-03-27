package com.example.todos.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.todos.R
import com.example.todos.database.ProductEntity
import com.example.todos.database.ProductRepo
import com.example.todos.model.Products
import com.example.todos.viewmodel.ProductsViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun taskListScreen(navController: NavHostController, viewModel: ProductsViewModel) {
    val productsData by viewModel.productsList
    val productsDataFromDB by viewModel.dataListFromDB.collectAsState()



    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(modifier = Modifier.padding(16.dp),
                onClick = { navController.navigate(Screens.Update.route) },
                icon = { Icon(Icons.Filled.Add, "Extended floating action button.") },
                text = { Text(text =  stringResource(R.string.add_data), style = TextStyle(fontSize = 16.sp), )},
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        }
    ) { padding ->

        val ctx = LocalContext.current
        val repo = ProductRepo(context = ctx)
        LaunchedEffect(Unit) {
            viewModel.getProducts(repo)
            viewModel.getAllData(repo)

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
        ) {
            CustomTopAppBar(
                currentScreen = stringResource(R.string.task_list ),
                showBackButton = false,
                onBackButtonClick = { }
            )
            if (productsDataFromDB.isEmpty()) {
                Text(text = stringResource(R.string.loading))
            } else {
                LazyColumn {
                    items(productsDataFromDB) { data ->

                        ProductItem(viewModel,repo, product = data
                        ) { selectedProduct ->
                            viewModel.data(
                                Products(
                                    id = selectedProduct.id,
                                    title = selectedProduct.title,
                                    description = selectedProduct.description,
                                    price = selectedProduct.price,
                                    discountPercentage = selectedProduct.discountPercentage,
                                    rating = selectedProduct.rating,
                                    stock = selectedProduct.stock,
                                    brand = selectedProduct.brand,
                                    category = selectedProduct.category,
                                    thumbnail = selectedProduct.thumbnail,
                                )
                            )
                            navController.navigate(Screens.Add.route)
                        }


                    }
                }

            }

        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductItem(
    viewModel: ProductsViewModel,
    repo: ProductRepo,
    product: ProductEntity,
    onItemClick: (ProductEntity) -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }
    val titleData = MutableStateFlow(ProductEntity())

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onItemClick(product) }
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.thumbnail),
                contentDescription = null,
                modifier = Modifier
                    .size(130.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Fit,
            )

            Column(Modifier.padding(8.dp)) {
                Text(
                    text = product.title,
                )
                Spacer(Modifier.size(10.dp))
                Text(
                    text = product.description
                )
                Spacer(Modifier.size(10.dp))
                Row {
                    Button(
                        onClick = {
                            openDialog.value = true
                            titleData.value = product
                        },
                        enabled = true,
                        border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
                        shape = MaterialTheme.shapes.medium,

                        ) {
                        Text(text = stringResource(R.string.update), color = Color.White)
                    }
                    Spacer(Modifier.size(10.dp))

                    Button(
                        onClick = {
                            viewModel.deleteData(repo,product.product_id)

                        },
                        enabled = true,
                        border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
                        shape = MaterialTheme.shapes.medium,

                        ) {
                        Text(text =stringResource(R.string.delete), color = Color.White)
                    }
                }

            }
        }
        if (openDialog.value) {
            var textName by remember {
                mutableStateOf(TextFieldValue(""))
            }
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = stringResource(R.string.update_title))
                },
                text = {
                    TextField(
                        value = textName,
                        onValueChange = {
                            textName = it
                        },
                        label = { Text(text =  stringResource(R.string.enter_title)) },
                        placeholder = { Text(text = titleData.value.title) },
                    )

                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                            viewModel.updateData(product.product_id,Products(title = textName.text),repo)
//                            repo.updateTitle(singleData.value.id,textName.text)
                        }
                    ) {
                        Text(stringResource(R.string.confirm))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text(stringResource(R.string.dismiss))
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    currentScreen: String = "",
    showBackButton: Boolean = true,
    onBackButtonClick: () -> Unit
) {
    TopAppBar(
        title = { Text(currentScreen) },
        modifier = modifier,
        navigationIcon = {
            if (showBackButton) {
                // Show back navigation button if allowed
                IconButton(onClick = onBackButtonClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        tint = Color.White,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}