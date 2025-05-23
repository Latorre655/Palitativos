package co.unab.johanyabi.proyectopalitativos

import android.util.Patterns

fun validateEmail(email: String): Pair<Boolean, String>{
    return when{
        // Verifica si el campo está vacío
        email.isEmpty() -> Pair(false, "El correo es requerido")

        // Utiliza el patrón predefinido de Android para validar formato de email
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> Pair(false, "El correo no es válido")

        // Restringe solo a correos de Gmail (regla de negocio específica)
        !email.endsWith("@gmail.com") -> Pair(false, "El correo no es válido")

        // Si pasa todas las validaciones
        else -> Pair(true, "")
    }
}

fun validatePassword(password: String): Pair<Boolean, String>{
    return when{
        // Verifica si el campo está vacío
        password.isEmpty() -> Pair(false, "La contraseña es requerida")

        // Verifica longitud mínima de 6 caracteres
        password.length < 6 -> Pair(false, "La contraseña debe tener al menos 6 caracteres")

        // Verifica que tenga al menos un número usando la función any() con isDigit()
        !password.any { it.isDigit() } -> Pair(false, "La contraseña debe tener al menos un número")

        // Si pasa todas las validaciones
        else -> Pair(true, "")
    }
}

fun validateName(name: String): Pair<Boolean, String>{
    return when{
        // Verifica si el campo está vacío
        name.isEmpty() -> Pair(false, "El nombre es requerido")

        // Verifica longitud mínima de 3 caracteres
        name.length < 3 -> Pair(false, "El nombre debe tener al menos 3 caracteres")

        // Si pasa todas las validaciones
        else -> Pair(true, "")
    }
}

fun validateAge(age: String): Pair<Boolean, String> {
    return when {
        // Verifica si el campo está vacío
        age.isEmpty() -> Pair(false, "La edad es requerida")

        // Verifica que todos los caracteres sean dígitos usando all() con isDigit()
        !age.all { it.isDigit() } -> Pair(false, "La edad debe contener solo números")

        // Si es numérico, realiza validaciones adicionales
        else -> {
            // Convierte el string a entero para validaciones numéricas
            val ageInt = age.toInt()
            when {
                // Verifica que sea mayor que 0
                ageInt <= 0 -> Pair(false, "La edad debe ser mayor que 0")

                // Verifica que no sea excesivamente alta (regla de negocio)
                ageInt > 100 -> Pair(false, "La edad ingresada es demasiado alta")

                // Si pasa todas las validaciones
                else -> Pair(true, "")
            }
        }
    }
}

fun validateDescription(description: String): Pair<Boolean, String> {
    return when {
        // Verifica si el campo está vacío
        description.isEmpty() -> Pair(false, "La descripción es requerida")

        // Verifica longitud mínima (redundante ya que isEmpty() cubre esto)
        description.length < 1 -> Pair(false, "La descripción debe tener al menos 1 caracter")

        // Verifica longitud máxima de 500 caracteres
        description.length > 500 -> Pair(false, "La descripción no debe exceder los 500 caracteres")

        // Si pasa todas las validaciones
        else -> Pair(true, "")
    }
}

fun validateConfirmPassword(password: String, confirmPassword: String): Pair<Boolean, String>{
    return when{
        // Verifica si el campo de confirmación está vacío
        confirmPassword.isEmpty() -> Pair(false, "La confirmación de contraseña es requerida")

        // Verifica que ambas contraseñas sean idénticas
        confirmPassword != password -> Pair(false, "Las contraseñas no coinciden")

        // Si pasa todas las validaciones
        else -> Pair(true, "")
    }
}