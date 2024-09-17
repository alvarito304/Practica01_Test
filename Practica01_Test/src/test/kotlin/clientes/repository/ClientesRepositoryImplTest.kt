package clientes.repository

import org.example.clientes.model.Cliente
import org.example.clientes.model.CuentaBancaria
import org.example.clientes.model.Tarjeta
import org.example.clientes.repository.ClientesRepositoryImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime

class ClientesRepositoryImplTest {

    private lateinit var repository: ClientesRepositoryImpl

    @BeforeEach
    fun setUp() {
        repository = ClientesRepositoryImpl()

        // Initialize the repository with some data for testing purposes
        repository.save(Cliente(
            1,
            "John",
            "50378911X",
            cuentaBancaria = CuentaBancaria(
                "ES12345678901234567890",
                100.0),
            Tarjeta(
                "1234-1234-1234-1234",
                "09/27"),
            LocalDateTime.now(),
            LocalDateTime.now()))
    }

    @Test
    fun getByDni() {
        //arrange

        //act
        val result = repository.getByDni("50378911X")

        //assert
        assertAll(
            { assertEquals("John", result?.nombre) },
            { assertEquals("50378911X", result?.dni) },
            { assertEquals("ES12345678901234567890", result!!.cuentaBancaria.IBAN) },
            {assertEquals(100.0, result!!.cuentaBancaria.saldo) },
            { assertEquals("1234-1234-1234-1234", result!!.tarjeta.numero) },
            { assertEquals("09/27", result!!.tarjeta.fechaCaducidad) }
        )
    }

    @Test
    fun getAll() {
    }

    @Test
    fun getById() {
    }

    @Test
    fun save() {
    }

    @Test
    fun update() {
    }

    @Test
    fun delete() {
    }
}