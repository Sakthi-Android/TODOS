package com.example.todos.navigation

sealed class Screens(val route : String) {
    object List : Screens("list_route")
    object Add : Screens("add_route/data")
    object Update : Screens("update_route")
}
