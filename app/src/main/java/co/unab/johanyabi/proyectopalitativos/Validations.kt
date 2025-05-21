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

fun validateName(name: String): Pair<Boolean, String>{
    return when{
        name.isEmpty() -> Pair(false, "El nombre es requerido")
        name.length < 3 -> Pair(false, "El nombre debe tener al menos 3 caracteres")
        else -> Pair(true, "")
    }
}

fun validateAge(age: String): Pair<Boolean, String> {
    return when {
        age.isEmpty() -> Pair(false, "La edad es requerida")
        !age.all { it.isDigit() } -> Pair(false, "La edad debe contener solo números")
        else -> {
            val ageInt = age.toInt()
            when {
                ageInt <= 0 -> Pair(false, "La edad debe ser mayor que 0")
                ageInt > 100 -> Pair(false, "La edad ingresada es demasiado alta")
                else -> Pair(true, "")
            }
        }
    }
}

fun validateDescription(description: String): Pair<Boolean, String> {
    return when {
        description.isEmpty() -> Pair(false, "La descripción es requerida")
        description.length < 1 -> Pair(false, "La descripción debe tener al menos 1 caracter")
        description.length > 500 -> Pair(false, "La descripción no debe exceder los 500 caracteres")
        else -> Pair(true, "")
    }
}

fun validateConfirmPassword(password: String, confirmPassword: String): Pair<Boolean, String>{
    return when{
        confirmPassword.isEmpty() -> Pair(false, "La confirmación de contraseña es requerida")
        confirmPassword != password -> Pair(false, "Las contraseñas no coinciden")
        else -> Pair(true, "")
    }
}