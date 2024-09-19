package org.example.clientes.validator.validadorCuentaBancaria

import java.math.BigInteger

object ValidadorCuentaBancaria {
    fun ibanEsValido(iban: String): Boolean {
        val ibanPattern = "^[A-Z]{2}[0-9]{22}$".toRegex()
        val ibanCountryValues = mapOf(
            "AL" to "1021", "AD" to "1013", "AT" to "1029", "BE" to "1114", "BA" to "1120", "BG" to "1116", "HR" to "1727",
            "CY" to "1225", "CZ" to "1235", "DK" to "1314", "EE" to "1424", "FI" to "1518", "FR" to "1527", "GE" to "1624",
            "DE" to "1314", "GR" to "1627", "HU" to "1721", "IS" to "1827", "IE" to "1824", "IT" to "1829", "LV" to "2112",
            "LI" to "2118", "LT" to "2119", "LU" to "2121", "MT" to "2212", "NL" to "2314", "NO" to "2324", "PL" to "2512",
            "PT" to "2520", "RO" to "2714", "SM" to "2923", "RS" to "2728", "SK" to "2920", "SI" to "2927", "ES" to "1428",
            "SE" to "2814", "CH" to "1214", "TR" to "2927"
        )

        // Validar formato
        if (!iban.matches(ibanPattern)) return false

        // Extraer componentes del IBAN
        val countryCode = iban.substring(0, 2)
        val numeroControl = iban.substring(2, 4)
        val codigoEntidad = iban.substring(4, 8)
        val numeroCuenta = iban.substring(8, 24)

        // Generar el número completo para el cálculo del control
        val numeroGenerado = codigoEntidad + numeroCuenta + ibanCountryValues[countryCode] + "00"
        val numeroGeneradoBigInt = numeroGenerado.toBigInteger()
        println(numeroGeneradoBigInt)

        // Cálculo del dígito de control
        val restoDe97 = numeroGeneradoBigInt % BigInteger.valueOf(97)
        val numeroControlCalculado = 98 - restoDe97.toInt()

        // Asegurarse de que el dígito de control calculado tenga dos dígitos
        val numeroControlFormateado = if (numeroControlCalculado < 10) "0$numeroControlCalculado" else numeroControlCalculado.toString()

        // Comparar el número de control calculado con el que está en el IBAN
        return numeroControl == numeroControlFormateado
    }


    fun saldoEsValido(saldo: Double): Boolean {
        return saldo >= 0
    }
}