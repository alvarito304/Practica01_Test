package clientes.repository

import org.example.clientes.model.Cliente
import org.example.clientes.model.CuentaBancaria
import org.example.clientes.model.Tarjeta
import org.example.clientes.repository.ClientesRepositoryImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime
import java.util.*

class ClientesRepositoryImplTest {

    private lateinit var repository: ClientesRepositoryImpl

    @BeforeEach
    fun setUp() {
        repository = ClientesRepositoryImpl()

        // Initialize the repository with some data for testing purposes
        repository.save(Cliente(
            UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f02"),
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
    fun getByDniNotFound() {
        //arrange

        //act
        val result = repository.getByDni("12345678X")

        //assert
        assertNull(result)
    }

    @Test
    fun getAll() {
        //arrange

        //act
        val result = repository.getAll()

        //assert
        assertEquals(1, result.size)
    }

    @Test
    fun getById() {
        //arrange
        val id = UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f02")

        //act
        val result = repository.getById(id)

        //assert
        assertAll(
            { assertEquals("John", result?.nombre) },
            { assertEquals("50378911X", result?.dni) },
            { assertEquals("ES12345678901234567890", result!!.cuentaBancaria.IBAN) },
            { assertEquals(100.0, result!!.cuentaBancaria.saldo) },
            { assertEquals("1234-1234-1234-1234", result!!.tarjeta.numero) },
            { assertEquals("09/27", result!!.tarjeta.fechaCaducidad) }
        )
    }

    @Test
    fun save() {
        //arrange
        val newCliente = Cliente(
            UUID.fromString("123e4567-e89b-12d3-a456-426655440001"),
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
        val id = UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f02")
        val cliente = Cliente(id, "john2", "50378911D", CuentaBancaria("ES47771398123983889", 400.0), Tarjeta("3456-3456-3456-3456", "11/29"))
        //act
        val result = repository.update(id, cliente)
        //assert
        assertAll(
            { assertEquals("john2", result?.nombre) },
            { assertEquals("50378911D", result?.dni) },
            { assertEquals("ES47771398123983889", result?.cuentaBancaria?.IBAN) },
            { assertEquals(400.0, result?.cuentaBancaria?.saldo) },
            { assertEquals("3456-3456-3456-3456", result?.tarjeta?.numero) },
            { assertEquals("11/29", result?.tarjeta?.fechaCaducidad) }
        )
    }

    @Test
    fun updateNotFound() {
        //arrange
        val id = UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f0")
        val cliente = Cliente(id, "john2", "50378911D", CuentaBancaria("ES47771398123983889", 400.0), Tarjeta("3456-3456-3456-3456", "11/29"))

        //act
        val result = repository.update(id, cliente)

        //assert
        assertNull(result)
    }

    @Test
    fun delete() {
        // arrange
        val id = UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f02")


        // act
        val result = repository.delete(id)

        // assert
        assertAll(
            { assertEquals("John", result?.nombre, "El nombre no coincide") },
            { assertEquals("50378911X", result?.dni, "El DNI no coincide") },
            { assertEquals("ES12345678901234567890", result?.cuentaBancaria?.IBAN, "El IBAN no coincide") },
            { assertEquals(100.0, result?.cuentaBancaria?.saldo, "El saldo no coincide") },
            { assertEquals("1234-1234-1234-1234", result?.tarjeta?.numero, "El número de la tarjeta no coincide") },
            { assertEquals("09/27", result?.tarjeta?.fechaCaducidad, "La fecha de caducidad no coincide") }
        )
    }

    @Test
    fun deleteNotFound() {
        //arrange
        val id = UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f0")
        //act
        val result = repository.delete(id)
        //assert
        assertNull(result)
    }
}