package co.unab.johanyabi.proyectopalitativos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Brush



@Preview
@Composable
fun LoginScreen() {

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF8A50F7),
            Color(0xFFF38B69)
        )
    )

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(gradientBackground),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .height(440.dp)
                    .width(360.dp)
                    .size(200.dp)
                    .background(
                        color = Color(0xFF1B1D27),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(top = 36.dp, start = 28.dp, end = 28.dp),
                contentAlignment = Alignment.Center

            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
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
                        modifier = Modifier
                            .padding(top = 12.dp, start = 26.dp, end = 26.dp),
                        text = "Llena la siguiente información para iniciar sesión",
                        color = Color(0xFFA9ADC6),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(textAlign = TextAlign.Center)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFF15161E),
                                shape = RoundedCornerShape(12.dp),
                            ),
                        label = {
                            Text(
                                "Email",
                                color = Color(0xFFA9ADC6),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF15161E),
                            unfocusedBorderColor = Color(0xFF15161E)
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFF15161E),
                                shape = RoundedCornerShape(12.dp),
                            ),
                        label = {
                            Text(
                                "Contraseña",
                                color = Color(0xFFA9ADC6),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF15161E),
                            unfocusedBorderColor = Color(0xFF15161E)
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {

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
                    TextButton(onClick = {}) {

                        val annotatedText = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            ) {
                                append("¿No tienes una cuenta aún? ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = Color(0xFF6F61EF),
                                    fontSize = 14.sp
                                )
                            ) {
                                append("Regístrate aquí")
                            }
                        }
                        Text(text = annotatedText)
                    }

                }
            }

        }
    }
}