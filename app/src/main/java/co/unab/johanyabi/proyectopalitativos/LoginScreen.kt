package co.unab.johanyabi.proyectopalitativos

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * COMPOSABLE PRINCIPAL DE LA PANTALLA DE LOGIN
 * Esta función crea toda la interfaz de usuario para el inicio de sesión
 *
 * @param onClickRegister: Función que se ejecuta cuando el usuario quiere registrarse
 * @param onSuccessfulLogin: Función que se ejecuta cuando el login es exitoso
 * @param viewModel: ViewModel que maneja la lógica de negocio del login
 */
@Composable
fun LoginScreen(
    onClickRegister: () -> Unit = {},
    onSuccessfulLogin: () -> Unit = {},
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    // ============================================================================
    // CONFIGURACIÓN DE FIREBASE AUTH Y GOOGLE SIGN-IN
    // ============================================================================

    // Instancia de Firebase Auth para manejar la autenticación
    val auth = Firebase.auth

    // Obtener la actividad actual para usar con Firebase Auth
    val activity = LocalView.current.context as Activity
    val context = LocalView.current.context

    // Scope para manejar corrutinas (operaciones asíncronas)
    val scope = rememberCoroutineScope()

    // TOKEN DE GOOGLE OAUTH - CRÍTICO PARA LA AUTENTICACIÓN CON GOOGLE
    // Este token debe coincidir con el configurado en Google Cloud Console
    val token = "1048348273236-2qs6tsjjngsjivabajor41bpg6g59sj2.apps.googleusercontent.com"
    val context_google = LocalContext.current

    // CONFIGURACIÓN DE GOOGLE SIGN-IN
    // Aquí se especifica qué información queremos obtener del usuario
    val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(token)  // Solicitar el ID token para Firebase
        .requestEmail()         // Solicitar acceso al email del usuario
        .build()

    // Cliente de Google Sign-In configurado con las opciones anteriores
    val googleSignInClient = GoogleSignIn.getClient(context_google, googleSignInOptions)

    // LAUNCHER PARA MANEJAR EL RESULTADO DE GOOGLE SIGN-IN
    // Este es el mecanismo moderno para manejar resultados de actividades
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Procesar el resultado del Google Sign-In
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            // Extraer la cuenta de Google del resultado
            val account = task.getResult(ApiException::class.java)

            // Crear credenciales de Firebase usando el token de Google
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)

            // Usar el ViewModel para autenticar con Firebase
            viewModel.singInWithGoogleCredential(credential) {
                onSuccessfulLogin() // Ejecutar callback de éxito
            }
        } catch (ex: Exception) {
            Log.d("Mascota Feliz", "Google sign in failed: ${ex.message}")
        }
    }

    // ============================================================================
    // ESTADOS DE LA INTERFAZ DE USUARIO
    // ============================================================================

    // Estados para los campos de entrada
    var inputEmail by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }

    // Estados para manejar errores
    var loginError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    // Estado para manejar el doble toque del botón atrás
    var backPressedOnce by remember { mutableStateOf(false) }

    // GRADIENTE DE FONDO
    // Crea un degradado vertical de morado a naranja
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF8A50F7), // Morado
            Color(0xFFF38B69)  // Naranja
        )
    )

    // ============================================================================
    // MANEJO DEL BOTÓN ATRÁS DEL SISTEMA
    // ============================================================================

    // IMPORTANTE: Implementa la funcionalidad de "presionar dos veces para salir"
    BackHandler {
        if (backPressedOnce) {
            // Si ya se presionó una vez, cerrar la aplicación
            activity.finish()
        } else {
            // Primera vez, mostrar mensaje y marcar como presionado
            backPressedOnce = true
            Toast.makeText(context, "Presiona atrás de nuevo para salir", Toast.LENGTH_SHORT).show()

            // Después de 2 segundos, resetear el estado
            scope.launch {
                delay(2000)
                backPressedOnce = false
            }
        }
    }

    // ============================================================================
    // INTERFAZ DE USUARIO - ESTRUCTURA PRINCIPAL
    // ============================================================================

    Box(
        modifier = Modifier
            .fillMaxSize()                          // Ocupa toda la pantalla
            .imePadding()                          // Ajusta cuando aparece el teclado
            .verticalScroll(rememberScrollState()) // Permite scroll vertical
            .background(gradientBackground)        // Aplica el gradiente de fondo
            .systemBarsPadding(),                 // Respeta las barras del sistema
        contentAlignment = Alignment.Center        // Centra el contenido
    ) {
        // CAJA CONTENEDORA PRINCIPAL CON FONDO OSCURO
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)  // 90% del ancho de la pantalla
                .background(
                    color = Color(0xFF1B1D27),           // Color de fondo oscuro
                    shape = RoundedCornerShape(12.dp)     // Esquinas redondeadas
                )
                .padding(top = 36.dp, bottom = 18.dp, start = 23.dp, end = 23.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                // ============================================================================
                // TÍTULO Y SUBTÍTULO
                // ============================================================================

                Text(
                    text = "Bienvenido",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(textAlign = TextAlign.Center)
                )
                Text(
                    modifier = Modifier.padding(top = 12.dp, start = 20.dp, end = 20.dp),
                    text = "Llena la siguiente información para iniciar sesión",
                    color = Color(0xFFA9ADC6), // Gris claro
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(textAlign = TextAlign.Center)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ============================================================================
                // CAMPO DE EMAIL
                // ============================================================================

                OutlinedTextField(
                    value = inputEmail,
                    onValueChange = { inputEmail = it }, // Actualiza el estado cuando cambia el texto
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF15161E), shape = RoundedCornerShape(12.dp)),
                    label = {
                        Text(
                            "Email",
                            color = Color(0xFFA9ADC6),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    textStyle = TextStyle(color = Color.White),
                    singleLine = true,  // Solo una línea
                    maxLines = 1,       // Máximo una línea
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF15161E),   // Sin borde visible
                        unfocusedBorderColor = Color(0xFF15161E)
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None, // Sin capitalización automática
                        autoCorrect = false,                          // Sin corrección automática
                        keyboardType = KeyboardType.Email            // Teclado optimizado para email
                    )
                )

                // MOSTRAR ERROR DE EMAIL SI EXISTE
                if (emailError.isNotEmpty()) {
                    Text(
                        text = emailError,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 12.dp),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ============================================================================
                // CAMPO DE CONTRASEÑA
                // ============================================================================

                OutlinedTextField(
                    value = inputPassword,
                    onValueChange = { inputPassword = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF15161E), shape = RoundedCornerShape(12.dp)),
                    label = {
                        Text(
                            "Contraseña",
                            color = Color(0xFFA9ADC6),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    textStyle = TextStyle(color = Color.White),
                    singleLine = true,
                    maxLines = 1,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF15161E),
                        unfocusedBorderColor = Color(0xFF15161E)
                    ),
                    // IMPORTANTE: Oculta el texto de la contraseña con asteriscos
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Password // Teclado optimizado para contraseñas
                    )
                )

                // MOSTRAR ERROR DE CONTRASEÑA SI EXISTE
                if (passwordError.isNotEmpty()) {
                    Text(
                        text = passwordError,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 12.dp),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // MOSTRAR ERROR GENERAL DE LOGIN SI EXISTE
                if (loginError.isNotEmpty()) {
                    Text(
                        loginError,
                        color = Color.Red,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        style = TextStyle(textAlign = TextAlign.Center)
                    )
                }

                // ============================================================================
                // BOTÓN DE INICIAR SESIÓN
                // ============================================================================

                Button(
                    onClick = {
                        // VALIDACIÓN DE CAMPOS
                        val isValidEmail: Boolean = validateEmail(inputEmail).first
                        val isValidPassword: Boolean = validatePassword(inputPassword).first
                        emailError = validateEmail(inputEmail).second
                        passwordError = validatePassword(inputPassword).second

                        // PROCESO DE AUTENTICACIÓN CON FIREBASE
                        if (isValidEmail && isValidPassword) {
                            // Intentar iniciar sesión con email y contraseña
                            auth.signInWithEmailAndPassword(inputEmail, inputPassword)
                                .addOnCompleteListener(activity) { task ->
                                    if (task.isSuccessful) {
                                        // Login exitoso
                                        onSuccessfulLogin()
                                    } else {
                                        // Manejar diferentes tipos de errores de Firebase
                                        loginError = when (task.exception) {
                                            is FirebaseAuthInvalidCredentialsException ->
                                                "Correo o contraseña incorrecta"
                                            is FirebaseAuthInvalidUserException ->
                                                "No existe una cuenta con este correo"
                                            else -> "Error al iniciar sesión. Intenta de nuevo"
                                        }
                                    }
                                }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6F61EF) // Morado
                    )
                ) {
                    Text(
                        text = "Iniciar Sesión",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // ============================================================================
                // ENLACE A REGISTRO
                // ============================================================================

                TextButton(onClick = onClickRegister) {
                    // TEXTO CON DIFERENTES ESTILOS (PARTE NORMAL + PARTE DESTACADA)
                    val annotatedText = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.White, fontSize = 14.sp)) {
                            append("¿No tienes una cuenta aún? ")
                        }
                        withStyle(style = SpanStyle(color = Color(0xFF6F61EF), fontSize = 14.sp)) {
                            append("Regístrate aquí")
                        }
                    }
                    Text(
                        text = annotatedText,
                        modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
                    )
                }

                // ============================================================================
                // BOTÓN DE GOOGLE SIGN-IN
                // ============================================================================

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .clickable {
                            // PROCESO CRÍTICO: CERRAR SESIÓN ANTES DE INICIAR NUEVA
                            // Esto evita conflictos con cuentas previamente autenticadas
                            googleSignInClient.signOut().addOnCompleteListener {
                                // Después de cerrar sesión exitosamente,
                                // lanzar el intent de Google Sign-In
                                launcher.launch(googleSignInClient.signInIntent)
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // ÍCONO DE GOOGLE
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Google",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(10.dp)
                    )
                    // TEXTO DEL BOTÓN
                    Text(
                        text = "Continuar con Google",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }
            }
        }
    }
}