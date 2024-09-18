package clientes.services.cache

import org.example.clientes.model.Cliente
import org.example.clientes.model.CuentaBancaria
import org.example.clientes.model.Tarjeta
import org.example.clientes.services.cache.CacheClienteImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime
import java.util.*

class CacheClienteImplTest {

    private var cache = CacheClienteImpl()
    var cliente = Cliente(
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
        LocalDateTime.now())

    @BeforeEach
    fun setUp() {
        cache.clear()
    }

    @Test
    fun get() {
        //arrange
        cache.put(cliente.id, cliente)
        //act
        val result  = cache.get(UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f02"))
        //assert
        assertAll(
            {assert(cache.size() == 1)},
            { assertNotNull(result) },
            { assert(result == cliente) }
        )
    }

    @Test
    fun getNotFound(){
        //arrange
        cache.put(cliente.id, cliente)
        //act
        val result = cache.get(UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f03"))
        //assert
        assertAll(
            { assert(cache.size() == 1) },
            { assert(result == null)}
        )
    }

    @Test
    fun put() {
        //arrange

        //act
        val result = cache.put(cliente.id, cliente)
        //assert
        assertAll(
            { assert(cache.size() == 1) },
            { assertNotNull(result)}
        )
    }

    @Test
    fun putMoreThan5() {
        val cliente2 = Cliente(
                    UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f03"),
                    "John",
                    "50378911X",
                    cuentaBancaria = CuentaBancaria(
                        "ES12345678901234567890",
                        100.0),
                    Tarjeta(
                        "1234-1234-1234-1234",
                        "09/27"),
                    LocalDateTime.now(),
                    LocalDateTime.now())
                val cliente3 = Cliente(
                    UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f04"),
                    "John",
                    "50378911X",
                    cuentaBancaria = CuentaBancaria(
                        "ES12345678901234567890",
                        100.0),
                    Tarjeta(
                        "1234-1234-1234-1234",
                        "09/27"),
                    LocalDateTime.now(),
                    LocalDateTime.now())
                val cliente4 = Cliente(
                    UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f05"),
                    "John",
                    "50378911X",
                    cuentaBancaria = CuentaBancaria(
                        "ES12345678901234567890",
                        100.0),
                    Tarjeta(
                        "1234-1234-1234-1234",
                        "09/27"),
                    LocalDateTime.now(),
                    LocalDateTime.now())
                val cliente5 = Cliente(
                    UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f06"),
                    "John",
                    "50378911X",
                    cuentaBancaria = CuentaBancaria(
                        "ES12345678901234567890",
                        100.0),
                    Tarjeta(
                        "1234-1234-1234-1234",
                        "09/27"),
                    LocalDateTime.now(),
                    LocalDateTime.now())

                 val cliente6 = Cliente(
                    UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f07"),
                    "John",
                    "50378911X",
                    cuentaBancaria = CuentaBancaria(
                        "ES12345678901234567890",
                        100.0),
                    Tarjeta(
                        "1234-1234-1234-1234",
                        "09/27"),
                    LocalDateTime.now(),
                    LocalDateTime.now())
        //act
        cache.put(cliente.id, cliente)
        cache.put(cliente2.id, cliente2)
        cache.put(cliente3.id, cliente3)
        cache.put(cliente4.id, cliente4)
        cache.put(cliente5.id, cliente5)
        cache.put(cliente6.id, cliente6)
        val result = cache.get(cliente6.id)

        //assert
        assertAll(
            { assert(cache.size() == 5) },
            { assertNotNull(result) },
            { assert(cliente6 == result) }
        )
    }

    @Test
    fun putOverwrite (){
        //arrange
        val cliente1 = cliente
        val cliente2 = cliente

        //act
        cache.put(cliente1.id, cliente1)
        cache.put(cliente2.id, cliente2)
        val result = cache.get(cliente.id)

        //assert
        assertAll(
            { assert(cache.size() == 1) },
            { assertNotNull(result) },
            { assert(cliente == result) }
        )
    }

    @Test
    fun remove() {
        //arrange
        cache.put(cliente.id, cliente)

        //act
        cache.remove(cliente.id)

        //assert
        assertAll(
            { assert(cache.size() == 0) },
            { assert(cache.get(cliente.id) == null) },
        )

    }

    @Test
    fun removeNotFound(){
        //act
        cache.put(cliente.id, cliente)

        //act
        cache.remove(UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f03"))
        val result = cache.get(cliente.id)

        //assert
        assertAll(
            { assert(cache.size() == 1) },
            { assert(result != null) }
        )
    }

    @Test
    fun clear() {
        //arrange
        cache.put(cliente.id, cliente)
        //act
        cache.clear()
        val result = cache.get(cliente.id)
        //assert
        assertAll(
            { assert(cache.size() == 0) },
            { assert(result == null) }
        )
    }

    @Test
    fun size() {
        //arrange
        cache.put(cliente.id, cliente)
        //act
        val result = cache.size()
        //assert
        assertEquals(1, result)
    }
}