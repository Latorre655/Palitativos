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

@Preview
@Composable
fun RegisterScreen(onClickLogin: () -> Unit = {}, onSuccessfulRegister: () -> Unit = {}){

    val auth = Firebase.auth
    val activity = LocalView.current.context as Activity

    //ESTADOS
    var inputEmail by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }
    var inputName by remember { mutableStateOf("") }
    var inputPasswordConfirmation by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    var registerError by remember { mutableStateOf("") }

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF8A50F7),
            Color(0xFFF38B69)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .background(gradientBackground)
            .systemBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(
                    color = Color(0xFF1B1D27),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(top = 36.dp, bottom = 18.dp, start = 23.dp, end = 23.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
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
                    color = Color(0xFFA9ADC6),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(textAlign = TextAlign.Center)
                )
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    value = inputName,
                    onValueChange = {inputName = it},
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
                    textStyle = TextStyle(
                        color = Color.White,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF15161E),
                        unfocusedBorderColor = Color(0xFF15161E)
                    )
                )
                if (nameError.isNotEmpty()) {
                    Text(
                        text = nameError,
                        color = Color.Red,
                        modifier = Modifier
                            .padding(top = 12.dp),
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = inputEmail,
                    onValueChange = {inputEmail = it},
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
                    textStyle = TextStyle(
                        color = Color.White,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF15161E),
                        unfocusedBorderColor = Color(0xFF15161E)
                    )
                )
                if (emailError.isNotEmpty()) {
                    Text(
                        text = emailError,
                        color = Color.Red,
                        modifier = Modifier
                            .padding(top = 12.dp),
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = inputPassword,
                    onValueChange = {inputPassword = it},
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
                    textStyle = TextStyle(
                        color = Color.White,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF15161E),
                        unfocusedBorderColor = Color(0xFF15161E)
                    )
                )
                if (passwordError.isNotEmpty()) {
                    Text(
                        text = passwordError,
                        color = Color.Red,
                        modifier = Modifier
                            .padding(top = 12.dp),
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = inputPasswordConfirmation,
                    onValueChange = {inputPasswordConfirmation = it},
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
                    textStyle = TextStyle(
                        color = Color.White,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF15161E),
                        unfocusedBorderColor = Color(0xFF15161E)
                    )
                )
                if (confirmPasswordError.isNotEmpty()) {
                    Text(
                        text = confirmPasswordError,
                        color = Color.Red,
                        modifier = Modifier
                            .padding(top = 12.dp),
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
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
                Button(
                    onClick = {

                        val isValidName = validateName(inputName).first
                        val isValidEmail = validateEmail(inputEmail).first
                        val isValidPassword = validatePassword(inputPassword).first
                        val isValidConfirmPassword = validateConfirmPassword(inputPassword, inputPasswordConfirmation).first

                        nameError= validateName(inputName).second
                        emailError = validateEmail(inputEmail).second
                        passwordError = validatePassword(inputPassword).second
                        confirmPasswordError = validateConfirmPassword(inputPassword, inputPasswordConfirmation).second

                        if (isValidName && isValidEmail && isValidPassword && isValidConfirmPassword) {
                            auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                                .addOnCompleteListener(activity) { task ->
                                    if (task.isSuccessful) {
                                        onSuccessfulRegister()
                                    } else {
                                        registerError = when (task.exception) {
                                            is FirebaseAuthInvalidCredentialsException -> "Correo invalido"
                                            is FirebaseAuthUserCollisionException -> "Correo ya registrado"
                                            else -> "Error al registrarse. Intenta de nuevo"
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
                        containerColor = Color(0xFF6F61EF)
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
                TextButton(onClick = onClickLogin) {
                    val annotatedText = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(color = Color.White, fontSize = 14.sp)
                        ) {
                            append("¿Ya tienes una cuenta? ")
                        }
                        withStyle(
                            style = SpanStyle(color = Color(0xFF6F61EF), fontSize = 14.sp)
                        ) {
                            append("Inicia Sesión aquí")
                        }
                    }
                    Text(
                        text = annotatedText,
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 16.dp)
                    )
                }
            }
        }
    }
}