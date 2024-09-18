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
        //arrange

        //act
        val result = repository.getAll()

        //assert
        assertEquals(1, result.size)
    }

    @Test
    fun save() {
        //arrange
        val newCliente = Cliente(
            2,
            "Jane",
            "50378912X",
            cuentaBancaria = CuentaBancaria(
                "ES22345678901234567890",
                150.0),
            Tarjeta(
                "2345-2345-2345-2345",
                "10/28"),
            LocalDateTime.now(),
            LocalDateTime.now())

        //act
        val result = repository.save(newCliente)

        //assert
        assertAll(
            { assertEquals("Jane", result.nombre) },
            { assertEquals("50378912X", result.dni) },
            { assertEquals("ES22345678901234567890", result.cuentaBancaria.IBAN)},
            { assertEquals(150.0, result.cuentaBancaria.saldo) },
            { assertEquals("2345-2345-2345-2345", result.tarjeta.numero) },
            { assertEquals("10/28", result.tarjeta.fechaCaducidad) }
        )
    }

    @Test
    fun update() {
        //arrange


        //act
        val result = repository.update(1, repository.getByDni("50378911X")!!)

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
    fun delete() {
        //arrange

        //act
        val result = repository.delete(1)

        //assert
        assertAll(
            {assert(result?.nombre == "john")},
            {assert(result?.dni == "50378911X")},
            {assert(result?.cuentaBancaria?.IBAN == "ES12345678901234567890")},
            {assert(result?.cuentaBancaria?.saldo == 100.0)},
            {assert(result?.tarjeta?.numero == "1234-1234-1234-1234")},
            {assert(result?.tarjeta?.fechaCaducidad == "09/27")}
        )
    }
}