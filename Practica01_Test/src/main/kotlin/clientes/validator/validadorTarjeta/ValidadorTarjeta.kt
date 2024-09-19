package org.example.clientes.validator.validadorTarjeta

import java.time.YearMonth
import java.time.format.DateTimeFormatter

object ValidadorTarjeta {
    val numeroPattern = Regex("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$")
    val fechaPattern = Regex("^\\d{2}/\\d{2}$")

    fun esNumeroValido(numeroTarjeta: String): Boolean {
        if (!numeroTarjeta.matches(numeroPattern)) return false
        return validarNumeroTarjeta(numeroTarjeta)
    }

    private fun validarNumeroTarjeta(numeroTarjeta: String): Boolean {
        var numeroTarjetaSinGuiones = numeroTarjeta.replace("-", "")
        val reversed = numeroTarjetaSinGuiones.reversed()
        var suma = 0
        for (i in reversed.indices) {
            val digit = reversed[i].toString().toInt()
            if (i%2 != 0){
                val doubled = digit*2
                if (doubled > 9){
                    suma += doubled-9
                }else{
                    suma += doubled
                }
            }else{
                suma+=digit
            }
        }
        return suma % 10 == 0
    }

    fun esFechaValida(fechaCaducidad: String): Boolean {
        if (!fechaCaducidad.matches(fechaPattern)) return false
        val guardarMes = fechaCaducidad.substring(0, 2)
        if (guardarMes.toInt() > 12 || guardarMes.toInt() < 1) return false

        val formato = DateTimeFormatter.ofPattern("MM/yy")
        val fechaExpiracion = YearMonth.parse(fechaCaducidad, formato)
        val fechaActual = YearMonth.now()

        return !fechaExpiracion.isBefore(fechaActual)
    }
}