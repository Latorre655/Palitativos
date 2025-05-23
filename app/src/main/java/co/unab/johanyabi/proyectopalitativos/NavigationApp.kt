package co.unab.johanyabi.proyectopalitativos

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun NavigationApp() {

    //CREACIÓN DEL CONTROLADOR DE NAVEGACIÓN
    // rememberNavController() crea y mantiene el controlador durante recomposiciones
    val myNavController = rememberNavController()

    // VARIABLE PARA DETERMINAR LA PANTALLA INICIAL
    var myStartDestination: String = "login"

    // CONFIGURACIÓN DE AUTENTICACIÓN CON FIREBASE
    val auth = Firebase.auth
    val currentUser = auth.currentUser

    // LÓGICA DE PANTALLA INICIAL BASADA EN AUTENTICACIÓN
    // Si hay un usuario autenticado, ir directamente a home
    // Si no hay usuario, mostrar login
    if (currentUser != null) {
        myStartDestination = "home"
    } else {
        myStartDestination = "login"
    }

    // CONFIGURACIÓN DEL HOST DE NAVEGACIÓN
    // NavHost es el contenedor que maneja todas las rutas de la aplicación
    NavHost(
        navController = myNavController,
        startDestination = myStartDestination
    ) {

        // ========================================
        //SECCIÓN: AUTENTICACIÓN (LOGIN Y REGISTER)
        // ========================================

        composable("login") {
            LoginScreen(
                // Callback para navegar al registro
                onClickRegister = {
                    myNavController.navigate("register")
                },
                // Callback para login exitoso
                onSuccessfulLogin = {
                    myNavController.navigate("home") {
                        // IMPORTANTE: popUpTo borra el login del stack
                        // inclusive = true incluye "login" en lo que se borra
                        // Esto evita que el usuario regrese al login con el botón atrás
                        popUpTo("login") { inclusive = true }
                    }
                })
        }

        composable("register") {
            RegisterScreen(
                // Regresa a login usando popBackStack()
                onClickLogin = {
                    myNavController.popBackStack()
                },
                // Callback para registro exitoso
                onSuccessfulRegister = {
                    myNavController.navigate("home") {
                        // ✅ popUpTo(0) borra TODA la pila de navegación
                        // Esto asegura que no se pueda regresar a pantallas de auth
                        popUpTo(0)
                    }
                })
        }

        // ========================================
        //SECCIÓN: PANTALLA PRINCIPAL (HOME)
        // ========================================

        composable("home") {
            Home2(
                // Logout: regresa a login y borra todo el stack
                onClickLogout = {
                    myNavController.navigate("login") {
                        popUpTo(0) // Borra todo el historial
                    }
                },
                // Navegar a lista de pacientes
                onClickPatients = {
                    myNavController.navigate("pacientes")
                },
                // Navegar a agregar paciente
                onClickAddPatient = {
                    myNavController.navigate("agregarPaciente")
                }
            )
        }

        // ========================================
        //SECCIÓN: GESTIÓN DE PACIENTES
        // ========================================

        composable("pacientes") {
            ListaPacientes(
                // Botón atrás estándar
                onClickAtras = {
                    myNavController.popBackStack()
                },
                // Navegar a pantalla de escalas médicas
                onClickescala = {
                    myNavController.navigate("pantallaescala")
                }
            )
        }

        // ========================================
        //SECCIÓN: ESCALAS MÉDICAS (HUB PRINCIPAL)
        // ========================================

        composable("pantallaescala") {
            PantallaEscalas(
                onClickBack = {
                    myNavController.popBackStack()
                },
                // MÚLTIPLES ESCALAS MÉDICAS DISPONIBLES:
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

        // ========================================
        //SECCIÓN: ESCALAS MÉDICAS INDIVIDUALES
        // ========================================

        // Escala de escaneo/diagnóstico por imagen
        composable("escaner") {
            Escanear(
                onClickBack = {
                    myNavController.popBackStack()
                }
            )
        }

        // Escala de Karnofsky (evalúa estado funcional del paciente)
        composable("karfnosky"){
            Karfnosky(
                onClickBack = {
                    myNavController.popBackStack()
                }
            )
        }

        // Escala ECOG (Eastern Cooperative Oncology Group)
        composable("ecog") {
            Ecog(
                onClickBack = {
                    myNavController.popBackStack()
                }
            )
        }

        // Escala Child-Pugh (evalúa función hepática)
        composable("child") {
            ChildPugh(
                onClickBack = {
                    myNavController.popBackStack()
                }
            )
        }

        // Calculadora de calcio corregido
        composable("calcio") {
            CalcioCorregido(
                onClickBack = {
                    myNavController.popBackStack()
                }
            )
        }

        // ========================================
        //SECCIÓN: AGREGAR NUEVO PACIENTE
        // ========================================

        composable("agregarPaciente") {
            AgregarPacienteScreen(
                onClickBack = {
                    myNavController.popBackStack()
                }
            )
        }
    }
}