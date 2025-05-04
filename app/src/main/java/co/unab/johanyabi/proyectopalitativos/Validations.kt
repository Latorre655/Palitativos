package co.unab.johanyabi.proyectopalitativos

import android.util.Patterns

fun validateEmail(email: String): Pair<Boolean, String>{
    return when{
        email.isEmpty() -> Pair(false, "El correo es requerido")
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> Pair(false, "El correo no es válido")
        !email.endsWith("@gmail.com") -> Pair(false, "El correo no es válido")
        else -> Pair(true, "")
    }
}

fun validatePassword(password: String): Pair<Boolean, String>{
    return when{
        password.isEmpty() -> Pair(false, "La contraseña es requerida")
        password.length < 6 -> Pair(false, "La contraseña debe tener al menos 6 caracteres")
        !password.any { it.isDigit() } -> Pair(false, "La contraseña debe tener al menos un número")
        else -> Pair(true, "")
    }
}