package co.unab.johanyabi.proyectopalitativos

import androidx.activity.compose.BackHandler
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

        //NAVEGACIÓN LOGIN Y REGISTER
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

        //NAVEGACIÓN HOME
        composable("home") {
            Home2(
                onClickLogout = {
                    myNavController.navigate("login") {
                        popUpTo(0)
                    }
                },
                onClickPatients = {
                    myNavController.navigate("pacientes")
                },
                onClickAddPatient = {
                    myNavController.navigate("agregarPaciente")
                }
            )
        }

        //NAVEGACIÓN PACIENTES, PANTALLAS ESCALAS, ESCANER, KARFNOSKY
        composable("pacientes") {
            ListaPacientes(
                onClickAtras = {
                    myNavController.popBackStack()
                },
                onClickescala = {
                    myNavController.navigate("pantallaescala")
                }
            )
        }
        composable("pantallaescala") {
            PantallaEscalas(
                onClickBack = {
                    myNavController.popBackStack()
                },
                onClickescaner = {
                    myNavController.navigate("escaner")
                },
                onClickkarfnosky = {
                    myNavController.navigate("karfnosky")
                },
                onClickecog = {
                    myNavController.navigate("ecog")
                },
                onClickchild = {
                    myNavController.navigate("child")
                },
                onClickcalcio = {
                    myNavController.navigate("calcio")
                }
            )
        }
        composable("escaner") {
            Escanear(
                onClickBack = {
                    myNavController.popBackStack()
                }
            )
        }
        composable("karfnosky"){
            Karfnosky(
                onClickBack = {
                    myNavController.popBackStack()
                }
            )
        }
        composable("ecog") {
            Ecog(
                onClickBack = {
                    myNavController.popBackStack()
                }
            )
        }
        composable("child") {
            ChildPugh(
                onClickBack = {
                    myNavController.popBackStack()
                }
            )
        }
        composable("calcio") {
            CalcioCorregido(
                onClickBack = {
                    myNavController.popBackStack()
                }
            )
        }

        //NAVEGACIÓN AGREGAR PACIENTE
        composable("agregarPaciente") {
            AgregarPacienteScreen(
                onClickBack = {
                    myNavController.popBackStack()
                }
            )
        }
    }
}