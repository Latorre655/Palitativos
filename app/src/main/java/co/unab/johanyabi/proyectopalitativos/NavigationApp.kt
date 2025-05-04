package co.unab.johanyabi.proyectopalitativos

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationApp() {

    val myNavController = rememberNavController()
    val myStartDestination: String = "login"

    NavHost(
        navController = myNavController,
        startDestination = myStartDestination
    ){
        composable("login"){
            LoginScreen(onClickRegister = {
                myNavController.navigate("register")
            },
                onSuccessfulLogin = {
                    myNavController.navigate("home"){
                        popUpTo("login"){inclusive = true}
                    }
                })
        }
        composable("register"){
            RegisterScreen(onClickLogin = {
                myNavController.popBackStack()
            })
        }
        composable("home"){
            HomeScreen()
        }
    }
}