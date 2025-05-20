package co.unab.johanyabi.proyectopalitativos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun Home2(
    onClickLogout: () -> Unit = {},
    onClickPatients: () -> Unit = {},
    onClickAddPatient: () -> Unit = {},
    onClickHelp: () -> Unit = {}
) {

    val auth = Firebase.auth
    val user = auth.currentUser

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .background(
                color = Color(0xFFA9ADC6),
            )
            .systemBarsPadding()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
            ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1B1D27)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = Color(0xFFFFD700),
                    modifier = Modifier
                        .size(60.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (user != null) {
                    user.email.toString()
                } else {
                    "No hay usuario"
                },
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 25.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){

                //BOTON DE PACIENTES

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable {
                            onClickPatients()
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1B1D27)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.si),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Pacientes",
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                // BOTON DE AGREGAR PACIENTE

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable {
                            onClickAddPatient()
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1B1D27)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.person_plus),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Añadir un nuevo\npaciente",
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                //BOTON DE AYUDA

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        onClickHelp()
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1B1D27)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Ayuda",
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .align(Alignment.BottomCenter)
                .background(
                    color = Color(0xFF1B1D27),
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                ) {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 12.dp),
                        text = "Configuraciones",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            }
                            .padding(bottom = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.language_118997),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier
                                .padding(bottom = 8.dp, top = 8.dp, end = 16.dp)
                                .size(24.dp)
                        )
                        Text(
                            text = "Idioma",
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier
                                .padding(bottom = 8.dp, top = 10.dp, end = 190.dp)
                        )
                        Text(
                            text = "Español",
                            fontSize = 16.sp,
                            color = Color(0xFF6F61EF),
                            modifier = Modifier
                                .padding(bottom = 8.dp, top = 10.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            }
                            .padding(bottom = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier
                                .padding(bottom = 8.dp, top = 8.dp, end = 16.dp)
                        )
                        Text(
                            text = "Configuración",
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier
                                .padding(bottom = 8.dp, top = 10.dp, end = 110.dp)
                        )
                        Text(
                            text = "Editar Perfil",
                            fontSize = 16.sp,
                            color = Color(0xFF6F61EF),
                            modifier = Modifier
                                .padding(bottom = 8.dp, top = 10.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                auth.signOut()
                                onClickLogout()
                            }
                            .padding(bottom = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier
                                .padding(bottom = 8.dp, top = 8.dp, end = 16.dp)
                        )
                        Text(
                            text = "Cerrar sesión",
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier
                                .padding(bottom = 8.dp, top = 10.dp, end = 140.dp)
                        )
                        Text(
                            text = "Log out?",
                            fontSize = 16.sp,
                            color = Color(0xFF6F61EF),
                            modifier = Modifier
                                .padding(bottom = 8.dp, top = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Home2Preview() {
    Home2()
}