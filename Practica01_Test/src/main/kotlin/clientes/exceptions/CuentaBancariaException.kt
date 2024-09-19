package org.example.clientes.exceptions

sealed class CuentaBancariaException(message: String) : Exception(message) {
    class CuentaBancariaIbanErrorException(iban: String) : CuentaBancariaException("Iban inválido: $iban")
    class CuentaBancariaBalanceErrorException(amount: Double) : CuentaBancariaException("Saldo Erroneo : $amount")
}