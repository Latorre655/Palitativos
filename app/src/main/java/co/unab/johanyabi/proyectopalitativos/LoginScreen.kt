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
import androidx.compose.ui.tooling.preview.Preview
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
import androidx.compose.material3.*
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

@Composable
fun LoginScreen(
    onClickRegister: () -> Unit = {}, onSuccessfulLogin: () -> Unit = {},
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val auth = Firebase.auth
    val activity = LocalView.current.context as Activity
    val context = LocalView.current.context
    val scope = rememberCoroutineScope()

    val token = "1048348273236-2qs6tsjjngsjivabajor41bpg6g59sj2.apps.googleusercontent.com"
    val context_google = LocalContext.current

    // Configurar las opciones de Google Sign-In
    val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(token)
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context_google, googleSignInOptions)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            viewModel.singInWithGoogleCredential(credential) {
                onSuccessfulLogin()
            }
        } catch (ex: Exception) {
            Log.d("Mascota Feliz", "Google sign in failed: ${ex.message}")
        }
    }

    //ESTADOS
    var inputEmail by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var backPressedOnce by remember { mutableStateOf(false) }

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF8A50F7),
            Color(0xFFF38B69)
        )
    )

    BackHandler {
        if (backPressedOnce) {
            activity.finish()
        } else {
            backPressedOnce = true
            Toast.makeText(context, "Presiona atrás de nuevo para salir", Toast.LENGTH_SHORT).show()

            scope.launch {
                delay(2000)
                backPressedOnce = false
            }
        }
    }

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
                    text = "Bienvenido",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(textAlign = TextAlign.Center)
                )
                Text(
                    modifier = Modifier.padding(top = 12.dp, start = 20.dp, end = 20.dp),
                    text = "Llena la siguiente información para iniciar sesión",
                    color = Color(0xFFA9ADC6),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(textAlign = TextAlign.Center)
                )
                Spacer(modifier = Modifier.height(24.dp))
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
                    textStyle = TextStyle(
                        color = Color.White
                    ),
                    singleLine = true,
                    maxLines = 1,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF15161E),
                        unfocusedBorderColor = Color(0xFF15161E)
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Email
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
                    textStyle = TextStyle(
                        color = Color.White,
                    ),
                    singleLine = true,
                    maxLines = 1,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF15161E),
                        unfocusedBorderColor = Color(0xFF15161E)
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Password
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
                Button(
                    onClick = {

                        val isValidEmail: Boolean = validateEmail(inputEmail).first
                        val isValidPassword: Boolean = validatePassword(inputPassword).first
                        emailError = validateEmail(inputEmail).second
                        passwordError = validatePassword(inputPassword).second

                        if (isValidEmail && isValidPassword) {
                            auth.signInWithEmailAndPassword(inputEmail, inputPassword)
                                .addOnCompleteListener(activity) { task ->
                                    if (task.isSuccessful) {
                                        onSuccessfulLogin()
                                    } else {
                                        loginError = when (task.exception) {
                                            is FirebaseAuthInvalidCredentialsException -> "Correo o contraseña incorrecta"
                                            is FirebaseAuthInvalidUserException -> "No existe una cuenta con este correo"
                                            else -> "Error al iniciar sesión. Intenta de nuevo"
                                        }
                                    }
                                }
                        } else {

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
                        text = "Iniciar Sesión",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                TextButton(onClick = onClickRegister) {
                    val annotatedText = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(color = Color.White, fontSize = 14.sp)
                        ) {
                            append("¿No tienes una cuenta aún? ")
                        }
                        withStyle(
                            style = SpanStyle(color = Color(0xFF6F61EF), fontSize = 14.sp)
                        ) {
                            append("Regístrate aquí")
                        }
                    }
                    Text(
                        text = annotatedText,
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 12.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .clickable {
                            // IMPORTANTE: Cerrar sesión antes de iniciar una nueva
                            googleSignInClient.signOut().addOnCompleteListener {
                                // Después de cerrar sesión, iniciar el intent de Google Sign-In
                                launcher.launch(googleSignInClient.signInIntent)
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Google",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(10.dp)
                    )
                    Text(
                        text = "Continuar con Google",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(end=10.dp)
                    )
                }
            }
        }
    }
}