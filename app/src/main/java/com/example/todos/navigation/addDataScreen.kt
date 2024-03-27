package com.example.todos.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.todos.R
import com.example.todos.database.ProductEntity
import com.example.todos.database.ProductRepo
import com.example.todos.model.Products
import com.example.todos.viewmodel.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addDataScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductsViewModel
) {

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier
                .fillMaxSize()
                .padding(15.dp),
        ) {
            val ctx = LocalContext.current
            val repo = ProductRepo(context = ctx)
            CustomTopAppBar(
                currentScreen = "Add Product",
                showBackButton = false,
                onBackButtonClick = { }
            )
            val textStateTitle = remember { mutableStateOf(TextFieldValue()) }
            val textStateDesc = remember { mutableStateOf(TextFieldValue()) }
            val textStatePrice = remember { mutableStateOf(TextFieldValue()) }
            val textStateDiscount = remember { mutableStateOf(TextFieldValue()) }
            val textStateRating = remember { mutableStateOf(TextFieldValue()) }
            val textStateStock = remember { mutableStateOf(TextFieldValue()) }
            val textStateBrand = remember { mutableStateOf(TextFieldValue()) }
            val textStateCategory = remember { mutableStateOf(TextFieldValue()) }
            TextField(
                label = { Text(text = stringResource(R.string.TITLE) ) },
                value = textStateTitle.value,
                onValueChange = { textStateTitle.value = it }
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                label = { Text(text =  stringResource(R.string.description)) },
                value = textStateDesc.value,
                onValueChange = { textStateDesc.value = it }
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                label = { Text(text =  stringResource(R.string.price)) },
                value = textStatePrice.value,
                onValueChange = { textStatePrice.value = it }
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                label = { Text(text =  stringResource(R.string.discountPercentage)) },
                value = textStateDiscount.value,
                onValueChange = { textStateDiscount.value = it }
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                label = { Text(text =  stringResource(R.string.rating)) },
                value = textStateRating.value,
                onValueChange = { textStateRating.value = it }
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                label = { Text(text =  stringResource(R.string.stock)) },
                value = textStateStock.value,
                onValueChange = { textStateStock.value = it }
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                label = { Text(text =  stringResource(R.string.brand)) },
                value = textStateBrand.value,
                onValueChange = { textStateBrand.value = it }
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                label = { Text(text =  stringResource(R.string.category)) },
                value = textStateCategory.value,
                onValueChange = { textStateCategory.value = it }
            )
            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {
                    val products = Products(
                        title = textStateTitle.value.text,
                        description = textStateDesc.value.text,
                        price = textStatePrice.value.text.toInt(),
                        discountPercentage = textStateDiscount.value.text.toFloat(),
                        rating = textStateRating.value.text.toFloat(),
                        stock = textStateStock.value.text.toInt(),
                        brand = textStateBrand.value.text,
                        category = textStateCategory.value.text,
                    )
                    viewModel.addData(products,repo)
                },
                enabled = true,
                border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
                shape = MaterialTheme.shapes.medium,
            ) {
                Text(text =  stringResource(R.string.add), color = Color.White)
            }
        }
    }
}