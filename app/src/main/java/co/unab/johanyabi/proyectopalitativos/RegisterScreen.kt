package co.unab.johanyabi.proyectopalitativos

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.auth

@Composable
fun RegisterScreen(
    onClickLogin: () -> Unit = {},
    onSuccessfulRegister: () -> Unit = {}
){

    // ============================================================================
    // CONFIGURACIÓN DE FIREBASE AUTH
    // ============================================================================

    // Instancia de Firebase Auth para crear nuevas cuentas
    val auth = Firebase.auth

    // Obtener la actividad actual para usar con Firebase Auth
    val activity = LocalView.current.context as Activity

    // ============================================================================
    // ESTADOS PARA LOS CAMPOS DE ENTRADA
    // ============================================================================

    // Estados para los 4 campos del formulario de registro
    var inputEmail by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }
    var inputName by remember { mutableStateOf("") }                    // NUEVO: Campo de nombre
    var inputPasswordConfirmation by remember { mutableStateOf("") }    // NUEVO: Confirmación de contraseña

    // ============================================================================
    // ESTADOS PARA MANEJO DE ERRORES INDIVIDUALES
    // ============================================================================

    // Cada campo tiene su propio estado de error para mostrar mensajes específicos
    var nameError by remember { mutableStateOf("") }           // Error del nombre
    var emailError by remember { mutableStateOf("") }          // Error del email
    var passwordError by remember { mutableStateOf("") }       // Error de la contraseña
    var confirmPasswordError by remember { mutableStateOf("") } // Error de confirmación

    // Error general del proceso de registro
    var registerError by remember { mutableStateOf("") }

    // ============================================================================
    // DISEÑO VISUAL - MISMO GRADIENTE QUE LOGIN
    // ============================================================================

    // Gradiente de fondo idéntico al login para consistencia visual
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF8A50F7), // Morado
            Color(0xFFF38B69)  // Naranja
        )
    )

    // ============================================================================
    // ESTRUCTURA PRINCIPAL DE LA INTERFAZ
    // ============================================================================

    Box(
        modifier = Modifier
            .fillMaxSize()                          // Ocupa toda la pantalla
            .imePadding()                          // Ajusta cuando aparece el teclado
            .verticalScroll(rememberScrollState()) // Scroll vertical (importante con 4 campos)
            .background(gradientBackground)        // Aplica el gradiente de fondo
            .systemBarsPadding(),                 // Respeta las barras del sistema
        contentAlignment = Alignment.Center        // Centra el contenido
    ) {
        // CAJA CONTENEDORA PRINCIPAL - MISMA ESTRUCTURA QUE LOGIN
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)  // 90% del ancho de la pantalla
                .background(
                    color = Color(0xFF1B1D27),           // Fondo oscuro
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
                    text = "Registro",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(textAlign = TextAlign.Center)
                )
                Text(
                    modifier = Modifier.padding(top = 12.dp, start = 20.dp, end = 20.dp),
                    text = "Llena la siguiente información para poder registrarse",
                    color = Color(0xFFA9ADC6), // Gris claro
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(textAlign = TextAlign.Center)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ============================================================================
                // CAMPO 1: NOMBRE COMPLETO
                // ============================================================================

                OutlinedTextField(
                    value = inputName,
                    onValueChange = { inputName = it }, // Actualiza el estado del nombre
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF15161E), shape = RoundedCornerShape(12.dp)),
                    label = {
                        Text(
                            "Nombre Completo",
                            color = Color(0xFFA9ADC6),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    textStyle = TextStyle(color = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF15161E),   // Sin borde visible
                        unfocusedBorderColor = Color(0xFF15161E)
                    )
                )

                // MOSTRAR ERROR DE NOMBRE SI EXISTE
                if (nameError.isNotEmpty()) {
                    Text(
                        text = nameError,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 12.dp),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ============================================================================
                // CAMPO 2: EMAIL
                // ============================================================================

                OutlinedTextField(
                    value = inputEmail,
                    onValueChange = { inputEmail = it },
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
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF15161E),
                        unfocusedBorderColor = Color(0xFF15161E)
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
                // CAMPO 3: CONTRASEÑA
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
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF15161E),
                        unfocusedBorderColor = Color(0xFF15161E)
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

                // ============================================================================
                // CAMPO 4: CONFIRMACIÓN DE CONTRASEÑA
                // ============================================================================

                OutlinedTextField(
                    value = inputPasswordConfirmation,
                    onValueChange = { inputPasswordConfirmation = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF15161E), shape = RoundedCornerShape(12.dp)),
                    label = {
                        Text(
                            "Confirmar contraseña",
                            color = Color(0xFFA9ADC6),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    textStyle = TextStyle(color = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF15161E),
                        unfocusedBorderColor = Color(0xFF15161E)
                    )
                )

                // MOSTRAR ERROR DE CONFIRMACIÓN SI EXISTE
                if (confirmPasswordError.isNotEmpty()) {
                    Text(
                        text = confirmPasswordError,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 12.dp),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // MOSTRAR ERROR GENERAL DE REGISTRO SI EXISTE
                if (registerError.isNotEmpty()) {
                    Text(
                        registerError,
                        color = Color.Red,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        style = TextStyle(textAlign = TextAlign.Center)
                    )
                }

                // ============================================================================
                // BOTÓN DE REGISTRO
                // ============================================================================

                Button(
                    onClick = {
                        // VALIDACIÓN EXHAUSTIVA DE TODOS LOS CAMPOS
                        // Cada campo se valida individualmente para dar feedback específico

                        val isValidName = validateName(inputName).first
                        val isValidEmail = validateEmail(inputEmail).first
                        val isValidPassword = validatePassword(inputPassword).first
                        val isValidConfirmPassword = validateConfirmPassword(inputPassword, inputPasswordConfirmation).first

                        // Actualizar mensajes de error individuales
                        nameError = validateName(inputName).second
                        emailError = validateEmail(inputEmail).second
                        passwordError = validatePassword(inputPassword).second
                        confirmPasswordError = validateConfirmPassword(inputPassword, inputPasswordConfirmation).second

                        // PROCESO DE REGISTRO CON FIREBASE
                        // Solo procede si TODOS los campos son válidos
                        if (isValidName && isValidEmail && isValidPassword && isValidConfirmPassword) {

                            // CREAR USUARIO CON EMAIL Y CONTRASEÑA
                            auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                                .addOnCompleteListener(activity) { task ->
                                    if (task.isSuccessful) {
                                        // REGISTRO EXITOSO
                                        // El usuario se crea automáticamente en Firebase Auth
                                        onSuccessfulRegister()
                                    } else {
                                        // MANEJO DE ERRORES ESPECÍFICOS DE FIREBASE
                                        registerError = when (task.exception) {
                                            is FirebaseAuthInvalidCredentialsException ->
                                                "Correo invalido"
                                            is FirebaseAuthUserCollisionException ->
                                                "Correo ya registrado" // El email ya existe
                                            else -> "Error al registrarse. Intenta de nuevo"
                                        }
                                    }
                                }
                        }
                        // Si algún campo no es válido, no se ejecuta el registro
                        // Los errores ya se muestran en la UI automáticamente
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6F61EF) // Mismo color que el login
                    )
                ) {
                    Text(
                        text = "Registrarse",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // ============================================================================
                // ENLACE AL LOGIN
                // ============================================================================

                TextButton(onClick = onClickLogin) {
                    // TEXTO CON ESTILOS MÚLTIPLES - MISMA TÉCNICA QUE EN LOGIN
                    val annotatedText = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.White, fontSize = 14.sp)) {
                            append("¿Ya tienes una cuenta? ")
                        }
                        withStyle(style = SpanStyle(color = Color(0xFF6F61EF), fontSize = 14.sp)) {
                            append("Inicia Sesión aquí")
                        }
                    }
                    Text(
                        text = annotatedText,
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
                    )
                }
            }
        }
    }
}