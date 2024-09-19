package clientes.validator.validadorCuentaBancaria

import org.example.clientes.validator.validadorCuentaBancaria.ValidadorCuentaBancaria
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ValidadorCuentaBancariaTest {

    @Test
    fun ibanEsValido() {
        //arrange
        val iban = "ES3701281234567812345678"
        //act
        val result = ValidadorCuentaBancaria.ibanEsValido(iban)
        //assert
        assertTrue(result)
    }

    @Test
    fun ibanEsInvalido() {
        //arrange
        val iban = "ES3701281234567812345679"
        //act
        val result = ValidadorCuentaBancaria.ibanEsValido(iban)
        //assert
        assertFalse(result)
    }

    @Test
    fun saldoEsValido() {
        //arrange
        val saldo = 1000.22
        //act
        val result = ValidadorCuentaBancaria.saldoEsValido(saldo)
        //assert
        assertTrue(result)
    }
    @Test
    fun saldoEsInvalido() {
        //arrange
        val saldo = -1000.02
        //act
        val result = ValidadorCuentaBancaria.saldoEsValido(saldo)
        //assert
        assertFalse(result)
    }
}