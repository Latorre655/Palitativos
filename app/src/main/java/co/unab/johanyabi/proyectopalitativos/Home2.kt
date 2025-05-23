package co.unab.johanyabi.proyectopalitativos

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Home2(
    onClickLogout: () -> Unit = {},      // Callback para cerrar sesión
    onClickPatients: () -> Unit = {},    // Callback para ir a lista de pacientes
    onClickAddPatient: () -> Unit = {},  // Callback para agregar nuevo paciente
    onClickHelp: () -> Unit = {}         // Callback para ayuda (no implementado)
) {

    //CONFIGURACIÓN DE FIREBASE Y CONTEXTO
    val auth = Firebase.auth
    val user = auth.currentUser  // Usuario actualmente autenticado
    val activity = LocalView.current.context as Activity  // Referencia a la Activity
    val context = LocalView.current.context               // Contexto para Toast
    val scope = rememberCoroutineScope()                  // Scope para corrutinas

    //ESTADO PARA MANEJO DEL BOTÓN ATRÁS (DOBLE TAP PARA SALIR)
    var backPressedOnce by remember { mutableStateOf(false) }

    //MANEJO PERSONALIZADO DEL BOTÓN ATRÁS DEL SISTEMA
    // Implementa el patrón "presiona dos veces para salir"
    BackHandler {
        if (backPressedOnce) {
            // Segunda pulsación: cerrar la aplicación
            activity.finish()
        } else {
            // Primera pulsación: mostrar Toast y marcar como presionado
            backPressedOnce = true
            Toast.makeText(context, "Presiona atrás de nuevo para salir", Toast.LENGTH_SHORT).show()

            // RESETEAR EL ESTADO DESPUÉS DE 2 SEGUNDOS
            scope.launch {
                delay(2000) // Esperar 2 segundos
                backPressedOnce = false // Resetear para requerir doble tap nuevamente
            }
        }
    }

    //CONTENEDOR PRINCIPAL DE LA PANTALLA
    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()        // Ajuste automático para teclado virtual
            .verticalScroll(rememberScrollState()) // Scroll vertical si el contenido es muy largo
            .background(
                color = Color(0xFFA9ADC6), //COLOR DE FONDO PRINCIPAL (gris-azul claro)
            )
            .systemBarsPadding() // Ajuste para barras del sistema (status bar, navigation bar)
    ) {
        // ========================================
        // SECCIÓN SUPERIOR: PERFIL DE USUARIO
        // ========================================
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
        ) {

            // AVATAR CIRCULAR DEL USUARIO
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1B1D27)), // Fondo oscuro
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = Color(0xFFFFD700), // COLOR DORADO para el ícono
                    modifier = Modifier
                        .size(60.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // EMAIL DEL USUARIO (O MENSAJE SI NO HAY USUARIO)
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

            // ========================================
            // SECCIÓN: BOTONES DE NAVEGACIÓN PRINCIPAL
            // ========================================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 25.dp),
                horizontalArrangement = Arrangement.SpaceEvenly // Distribución uniforme
            ){

                // BOTÓN DE PACIENTES
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable {
                            onClickPatients() // Navegar a lista de pacientes
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1B1D27)), // Fondo oscuro consistente
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.si), // ÍCONO PERSONALIZADO
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

                // BOTÓN DE AGREGAR PACIENTE
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable {
                            onClickAddPatient() // Navegar a agregar paciente
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
                            painter = painterResource(id = R.drawable.person_plus), // ÍCONO DE AGREGAR PERSONA
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Añadir un nuevo\npaciente", // TEXTO EN DOS LÍNEAS
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                // BOTÓN DE AYUDA (NO IMPLEMENTADO)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        onClickHelp() // Callback para ayuda (actualmente vacío)
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
                            imageVector = Icons.Filled.Info, // ÍCONO DE INFORMACIÓN
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

        // ========================================
        // SECCIÓN INFERIOR: PANEL DE CONFIGURACIONES
        // ========================================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp) // ALTURA FIJA DEL PANEL
                .align(Alignment.BottomCenter) // Posicionado en la parte inferior
                .background(
                    color = Color(0xFF1B1D27), // FONDO OSCURO
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp) // Esquinas redondeadas arriba
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
                    // TÍTULO DE LA SECCIÓN
                    Text(
                        modifier = Modifier
                            .padding(bottom = 12.dp),
                        text = "Configuraciones",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    // OPCIÓN DE IDIOMA (NO FUNCIONAL)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            }
                            .padding(bottom = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.language_118997), // ÍCONO DE IDIOMA
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
                            text = "Español", //  IDIOMA ACTUAL (HARDCODED)
                            fontSize = 16.sp,
                            color = Color(0xFF6F61EF), // Color púrpura para valores
                            modifier = Modifier
                                .padding(bottom = 8.dp, top = 10.dp)
                        )
                    }

                    // OPCIÓN DE CONFIGURACIÓN/EDITAR PERFIL (NO FUNCIONAL)
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

                    // OPCIÓN DE CERRAR SESIÓN (FUNCIONAL)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // PROCESO DE LOGOUT
                                auth.signOut()        // Cerrar sesión en Firebase
                                onClickLogout()       // Navegar a pantalla de login
                            }
                            .padding(bottom = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp, // ÍCONO DE SALIDA
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
                            text = "Log out?", // TEXTO DESCRIPTIVO
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