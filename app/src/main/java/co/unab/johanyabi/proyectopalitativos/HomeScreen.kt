package co.unab.johanyabi.proyectopalitativos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Preview
@Composable
fun HomeScreen(onClickLogout: () -> Unit = {}){
    val auth = Firebase.auth
    val user = auth.currentUser

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column {
            Text("Home Screen", fontSize = 30.sp)

            if (user != null){
                Text(user.email.toString())
            }else{
                Text("No hay usuario")
            }
            Button(onClick = {
                auth.signOut()
                onClickLogout()
            }, colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6F61EF))
            ) {
                Text("Cerrar Sesión", color = Color.White)
            }
        }

    }
}