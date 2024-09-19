package clientes.validator.validadorCliente

import org.example.clientes.exceptions.CuentaBancariaException
import org.example.clientes.exceptions.DniException
import org.example.clientes.exceptions.TarjetaCreditoException
import org.example.clientes.model.Cliente
import org.example.clientes.model.CuentaBancaria
import org.example.clientes.model.Tarjeta
import org.example.clientes.validator.validadorCliente.ValidadorCliente
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*

class ValidadorClienteTest {
    lateinit var cliente: Cliente
    @Test
    fun esValido() {
        //arrange
        cliente = Cliente(
            UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f02"),
            "John",
            "50378911X",
            cuentaBancaria = CuentaBancaria(
                "ES3701281234567812345678",
                100.0),
            Tarjeta(
                "4111-1111-1111-1111",
                "09/27"),
            LocalDateTime.now(),
            LocalDateTime.now())
        //act
        val result = ValidadorCliente.esValido(cliente)

        //assert
        assertTrue(result)
    }
    @Test
    fun nombreInvalido() {
        //arrange
        cliente = Cliente(
            UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f02"),
            "J",
            "50378911X",
            cuentaBancaria = CuentaBancaria(
                "ES3701281234567812345678",
                100.0),
            Tarjeta(
                "4111-1111-1111-1111",
                "09/27"),
            LocalDateTime.now(),
            LocalDateTime.now())
        //act
        val result = ValidadorCliente.esValido(cliente)

        //assert
        assertFalse(result)

    }
    @Test
    fun dniInvalido() {
        //arrange
        cliente = Cliente(
            UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f02"),
            "John",
            "12345678",
            cuentaBancaria = CuentaBancaria(
                "ES3701281234567812345678",
                100.0),
            Tarjeta(
                "4111-1111-1111-1111",
                "09/27"),
            LocalDateTime.now(),
            LocalDateTime.now())
        //act

        //assert
        assertThrows<DniException.DniInvalidoException> { ValidadorCliente.esValido(cliente) }
    }
    @Test
    fun cuentaBancariaIbanInvalida() {
        //arrange
        cliente = Cliente(
            UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f02"),
            "John",
            "50378911X",
            cuentaBancaria = CuentaBancaria(
                "ES370128123456781234567",
                100.0),
            Tarjeta(
                "4111-1111-1111-1111",
                "09/27"),
            LocalDateTime.now(),
            LocalDateTime.now())
        //act
        //assert
        assertThrows<CuentaBancariaException.CuentaBancariaIbanErrorException> { ValidadorCliente.esValido(cliente) }
    }

    @Test
    fun cuentaBancariaSaldoInvalido() {
        //arrange
        cliente = Cliente(
            UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f02"),
            "John",
            "50378911X",
            cuentaBancaria = CuentaBancaria(
                "ES3701281234567812345678",
                -100.0),
            Tarjeta(
                "4111-1111-1111-1111",
                "09/27"),
            LocalDateTime.now(),
            LocalDateTime.now())
        //act

        //assert
        assertThrows< CuentaBancariaException.CuentaBancariaBalanceErrorException >{ ValidadorCliente.esValido(cliente)}
    }
    @Test
    fun tarjetaNumeroInvalido() {
        //arrange
        cliente = Cliente(
            UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f02"),
            "John",
            "50378911X",
            cuentaBancaria = CuentaBancaria(
                "ES3701281234567812345678",
                100.0),
            Tarjeta(
                "4111111111111111",
                "09/27"),
            LocalDateTime.now(),
            LocalDateTime.now())
        //act
        //assert
        assertThrows<TarjetaCreditoException.TrajetaNumberErrorException> { ValidadorCliente.esValido(cliente) }
    }
    @Test
    fun tarjetaFechaInvalida() {
        //arrange
        cliente = Cliente(
            UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f02"),
            "John",
            "50378911X",
            cuentaBancaria = CuentaBancaria(
                "ES3701281234567812345678",
                100.0),
            Tarjeta(
                "4111-1111-1111-1111",
                "13/27"),
            LocalDateTime.now(),
            LocalDateTime.now())
        //act
        //assert
        assertThrows<TarjetaCreditoException.TrajetaDateErrorException> { ValidadorCliente.esValido(cliente) }
    }
}