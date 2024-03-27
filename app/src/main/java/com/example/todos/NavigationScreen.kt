package com.example.todos

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todos.navigation.Screens
import com.example.todos.navigation.addDataScreen
import com.example.todos.navigation.detailViewScreen
import com.example.todos.navigation.taskListScreen
import com.example.todos.viewmodel.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScreen(viewModel: ProductsViewModel) {

    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.List.route,
            modifier = Modifier.padding(paddingValues = paddingValues),
        ) {
            composable(Screens.List.route) {
                //call our composable screens here
                taskListScreen(navController,viewModel)
            }
            composable(
                Screens.Add.route
            ) { backstackEntry->
                val data = viewModel.selectedData.value
                detailViewScreen(modifier = Modifier,data)
            }
            composable(Screens.Update.route) {
                addDataScreen(modifier = Modifier,viewModel)
            }
        }
    }
}