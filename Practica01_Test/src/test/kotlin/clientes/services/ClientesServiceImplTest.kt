package clientes.services

import org.example.clientes.exceptions.CuentaBancariaException
import org.example.clientes.exceptions.DniException
import org.example.clientes.exceptions.PersonaException
import org.example.clientes.exceptions.TarjetaCreditoException
import org.example.clientes.model.Cliente
import org.example.clientes.model.CuentaBancaria
import org.example.clientes.model.Tarjeta
import org.example.clientes.repository.IClientesRepository
import org.example.clientes.services.ClientesServiceImpl
import org.example.clientes.services.cache.ICacheCliente
import org.example.clientes.validator.validadorCliente.ValidadorCliente
import org.example.clientes.validator.validadorDniNif.ValidadorDniNif
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.atLeast
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDateTime
import java.util.*

class ClientesServiceImplTest {

    @Mock
    private lateinit var clientesRepositoryImpl: IClientesRepository

    @Mock
    private lateinit var cache: ICacheCliente

    @Mock
    private lateinit var validadorCliente: ValidadorCliente

    @InjectMocks
    private lateinit var clientesServiceImpl: ClientesServiceImpl

    private val cliente1 = (Cliente(
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
    LocalDateTime.now()))

    private val cliente2 = (Cliente(
        UUID.fromString("63591beb-d620-4dcf-a719-3a2c4ed88f03"),
        "Sara",
        "50378910D",
        cuentaBancaria = CuentaBancaria(
            "ES9121000418450200051332",
            100.0),
        Tarjeta(
            "5555-5555-5555-4444",
            "09/27"),
        LocalDateTime.now(),
        LocalDateTime.now()))

    val clientes = mutableListOf(cliente1, cliente2)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getAllClientes() {
        //arrange
        whenever(clientesRepositoryImpl.getAll()).thenReturn(clientes)
        //act
        val result = clientesServiceImpl.getAllClientes()
        //assert
        assertEquals(clientes, result)
        //verify
        verify(atLeast(1), { clientesRepositoryImpl.getAll() })
    }

    @Test
    fun getClienteByDniNif() {
        //arrange
        whenever(clientesRepositoryImpl.getByDni(cliente1.dni)).thenReturn(cliente1)
        whenever(cache.getByDni(cliente1.dni)).thenReturn(cliente1)
        //act
        val result = clientesServiceImpl.getClienteByDniNif(cliente1.dni)
        //assert
        assertEquals(cliente1,result)
        //verify
        verify(atLeast(1)){clientesRepositoryImpl.getByDni(cliente1.dni)}
    }

    @Test
    fun getClienteByDniNifNotFoundInCache() {
        //arrange
        whenever(clientesRepositoryImpl.getByDni(cliente1.dni)).thenReturn(cliente1)
        whenever(cache.getByDni(cliente1.dni)).thenReturn(null)
        //act
        val result = clientesServiceImpl.getClienteByDniNif(cliente1.dni)
        //assert
        assertEquals(result, cliente1)
        //verify
        verify(atLeast(1)){clientesRepositoryImpl.getByDni(cliente1.dni)}
    }

    @Test
    fun getClienteByDniNifNotFound() {
        //arrange
        whenever(clientesRepositoryImpl.getByDni(cliente1.dni)).thenReturn(null)
        whenever(cache.getByDni(cliente1.dni)).thenReturn(null)
        //act
        val message = assertThrows<PersonaException.PersonaNotFoundException> { clientesServiceImpl.getClienteByDniNif(cliente1.id.toString()) }
        //assert
        assertEquals(message.message,"Persona no encontrada con ID: ${cliente1.id.toString()}")
        //verify
        verify(atLeast(1)){clientesRepositoryImpl.getByDni(cliente1.dni)}
    }

    @Test
    fun saveCliente() {
        //arrange
        whenever(clientesRepositoryImpl.save(cliente1)).thenReturn(cliente1)
        whenever(validadorCliente.esValido(cliente1)).thenReturn(true)
        //act
        val result = clientesServiceImpl.saveCliente(cliente1)
        //assert
        assertEquals(result,cliente1)
        //verify
        verify(atLeast(1)){clientesRepositoryImpl.save(cliente1)}
    }

    @Test
    fun saveClienteNotFound() {
        //arrange
        whenever(clientesRepositoryImpl.save(cliente1)).thenReturn(null)
        //act
        val message = assertThrows<PersonaException.PersonaNotSavedException> { clientesServiceImpl.saveCliente(cliente1) }
        //assert
        assertEquals(message.message,"Persona no guardada")
        //verify
        verify(atLeast(1)){clientesRepositoryImpl.save(cliente1)}
    }

    @Test
    fun saveClienteWithInvalidName() {
        //arrange
        val clienteNombreInvalido = Cliente(
            UUID.randomUUID(),
            "J",
            "50378911X",
            cuentaBancaria = CuentaBancaria(
                "ES3701281234567812345678",
                100.0),
            tarjeta = Tarjeta(
                "4111-1111-1111-1111",
                "09/27"),
            LocalDateTime.now(),
            LocalDateTime.now()
        )
        whenever(validadorCliente.esValido(clienteNombreInvalido)).thenReturn(false)
        //assert
        assertThrows<PersonaException.PersonaNotSavedException> { clientesServiceImpl.saveCliente(clienteNombreInvalido) }
        //verify
        verify(atLeast(1)){validadorCliente.esValido(clienteNombreInvalido)}
        verify(atLeast(0)){clientesRepositoryImpl.save(clienteNombreInvalido)}

    }

    @Test
    fun saveClienteWithInvalidDni() {
        //arrange
        val clienteDniInvalido = Cliente(
            UUID.randomUUID(),
            "John",
            "50378911X1",
            cuentaBancaria = CuentaBancaria(
                "ES3701281234567812345678",
                100.0),
            tarjeta = Tarjeta(
                "4111-1111-1111-1111",
                "09/27"),
            LocalDateTime.now(),
            LocalDateTime.now()
        )
        whenever(validadorCliente.esValido(clienteDniInvalido)).thenReturn(false)
        //assert
        assertThrows<DniException.DniInvalidoException> { clientesServiceImpl.saveCliente(clienteDniInvalido) }
        //verify
        verify(atLeast(1)){validadorCliente.esValido(clienteDniInvalido)}
        verify(atLeast(0)){clientesRepositoryImpl.save(clienteDniInvalido)}
    }
    @Test
    fun saveClienteWithInvalidCuentaBancariaIban() {
        //arrange
        val clienteCuentaBancariaInvalida = Cliente(
            UUID.randomUUID(),
            "John",
            "50378911X",
            cuentaBancaria = CuentaBancaria(
                "ES3701281234567812345679",
                100.0),
            tarjeta = Tarjeta(
                "4111-1111-1111-1111",
                "09/27"),
            LocalDateTime.now(),
            LocalDateTime.now()
        )
        //assert
        assertThrows<CuentaBancariaException.CuentaBancariaIbanErrorException> { clientesServiceImpl.saveCliente(clienteCuentaBancariaInvalida) }
        //verify
        verify(atLeast(1)){validadorCliente.esValido(clienteCuentaBancariaInvalida)}
        verify(atLeast(0)){clientesRepositoryImpl.save(clienteCuentaBancariaInvalida)}
    }

    @Test
    fun saveClienteWithInvalidCuentaBancariaSaldo() {
        //arrange
        val clienteCuentaBancariaSaldoInvalido = Cliente(
            UUID.randomUUID(),
            "John",
            "50378911X",
            cuentaBancaria = CuentaBancaria(
                "ES3701281234567812345678",
                -100.0),
            tarjeta = Tarjeta(
                "4111-1111-1111-1111",
                "09/27"),
            LocalDateTime.now(),
            LocalDateTime.now()
        )
        //assert
        assertThrows<CuentaBancariaException.CuentaBancariaBalanceErrorException> { clientesServiceImpl.saveCliente(clienteCuentaBancariaSaldoInvalido) }
        //verify
        verify(atLeast(1)){validadorCliente.esValido(clienteCuentaBancariaSaldoInvalido)}
        verify(atLeast(0)){clientesRepositoryImpl.save(clienteCuentaBancariaSaldoInvalido)}
    }

    @Test
    fun saveClienteWithInvalidTarjetaNumero() {
        //arrange
        val clienteTarjetaNumeroInvalido = Cliente(
            UUID.randomUUID(),
            "John",
            "50378911X",
            cuentaBancaria = CuentaBancaria(
                "ES3701281234567812345678",
                100.0),
            tarjeta = Tarjeta(
                "4111-1111-1111-1112",
                "09/27"),
            LocalDateTime.now(),
            LocalDateTime.now()
        )
        //assert
        assertThrows<TarjetaCreditoException.TrajetaNumberErrorException> { clientesServiceImpl.saveCliente(clienteTarjetaNumeroInvalido) }
        //verify
        verify(atLeast(1)){validadorCliente.esValido(clienteTarjetaNumeroInvalido)}
        verify(atLeast(0)){clientesRepositoryImpl.save(clienteTarjetaNumeroInvalido)}
    }

    @Test
    fun saveClienteWithInvalidTarjetaCaducidad() {
        //arrange
        val clienteTarjetaCaducidadInvalida = Cliente(
            UUID.randomUUID(),
            "John",
            "50378911X",
            cuentaBancaria = CuentaBancaria(
                "ES3701281234567812345678",
                100.0),
            tarjeta = Tarjeta(
                "4111-1111-1111-1111",
                "13/27"),
            LocalDateTime.now(),
            LocalDateTime.now()
        )
        //assert
        assertThrows<TarjetaCreditoException.TrajetaDateErrorException> { clientesServiceImpl.saveCliente(clienteTarjetaCaducidadInvalida) }
        //verify
        verify(atLeast(1)){validadorCliente.esValido(clienteTarjetaCaducidadInvalida)}
        verify(atLeast(0)){clientesRepositoryImpl.save(clienteTarjetaCaducidadInvalida)}
    }


    @Test
    fun updateCliente() {
        //arrange
        whenever(clientesRepositoryImpl.update(cliente1.id, cliente1)).thenReturn(cliente1)
        //act
        val result = clientesServiceImpl.updateCliente(cliente1.id, cliente1)
        //assert
        assertEquals(result,cliente1)
        //verify
        verify(atLeast(1)){clientesRepositoryImpl.update(cliente1.id, cliente1)}
    }

    @Test
    fun updateClienteNotFound() {
        //arrange
        whenever(clientesRepositoryImpl.update(cliente1.id, cliente1)).thenReturn(null)
        //act
        val message = assertThrows<PersonaException.PersonaNotUpdatedException> { clientesServiceImpl.updateCliente(cliente1.id, cliente1) }
        //assert
        assertEquals(message.message,"Persona no actualizada con ID: ${cliente1.id.toString()}")
        //verify
        verify(atLeast(1)){clientesRepositoryImpl.update(cliente1.id, cliente1)}
    }

    @Test
    fun deleteCliente() {
        //arrange
        whenever(clientesRepositoryImpl.delete(cliente1.id)).thenReturn(cliente1)
        //act
        val result = clientesServiceImpl.deleteCliente(cliente1.id)
        //assert
        assertEquals(result,cliente1)
        //verify
        verify(atLeast(1)){clientesRepositoryImpl.delete(cliente1.id)}
    }

    @Test
    fun deleteClienteNotFound() {
        //arrange
        whenever(clientesRepositoryImpl.delete(cliente1.id)).thenReturn(null)
        //act
        val message = assertThrows<PersonaException.PersonaNotDeletedException> { clientesServiceImpl.deleteCliente(cliente1.id) }
        //assert
        assertEquals(message.message,"Persona no eliminada con ID: ${cliente1.id}")
        //verify
        verify(atLeast(1)){clientesRepositoryImpl.delete(cliente1.id)}
    }
}