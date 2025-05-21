package co.unab.johanyabi.proyectopalitativos

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun NavigationApp() {

    val myNavController = rememberNavController()
    var myStartDestination: String = "login"

    val auth = Firebase.auth
    val currentUser = auth.currentUser

    if (currentUser != null) {
        myStartDestination = "home"
    } else {
        myStartDestination = "login"
    }

    NavHost(
        navController = myNavController,
        startDestination = myStartDestination
    ) {
        composable("login") {
            LoginScreen(
                onClickRegister = {
                    myNavController.navigate("register")
                },
                onSuccessfulLogin = {
                    myNavController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                })
        }
        composable("register") {
            RegisterScreen(onClickLogin = {
                myNavController.popBackStack()
            }, onSuccessfulRegister = {
                myNavController.navigate("home") {
                    popUpTo(0)
                }
            })
        }
        composable("home") {
            Home2(
                onClickLogout = {
                    myNavController.navigate("login") {
                        popUpTo(0)
                    }
                },
                onClickPatients = {
                    myNavController.navigate("pacientes") {
                        popUpTo(0)
                    }
                },
                onClickAddPatient = {
                    myNavController.navigate("agregarPaciente") {
                        popUpTo(0)
                    }
                }
            )
        }
        composable("pacientes") {
            ListaPacientes(
                onClickBack = {
                    myNavController.popBackStack()
                },
                onClickescala = {
                    myNavController.navigate("pantallaescala") {
                        popUpTo(0)
                    }
                }
            )
        }
        composable("agregarPaciente") {
            AgregarPacienteScreen(onClickBack = {
                myNavController.popBackStack()
            })
        }
        composable("pantallaescala") {
            PantallaEscalas(
                onClickBack = {
                    myNavController.popBackStack()
                },
                onClickescaner = {
                    myNavController.navigate("escaner") {
                        popUpTo(0)
                    }
                }
                )
        }
        composable("escaner") {
            Escanear(onClickBack = {
                myNavController.popBackStack()
            })
        }
    }
}