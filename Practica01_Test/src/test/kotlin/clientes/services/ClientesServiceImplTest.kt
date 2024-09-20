package clientes.services

import org.example.clientes.exceptions.PersonaException
import org.example.clientes.model.Cliente
import org.example.clientes.model.CuentaBancaria
import org.example.clientes.model.Tarjeta
import org.example.clientes.repository.IClientesRepository
import org.example.clientes.services.ClientesServiceImpl
import org.example.clientes.services.cache.ICacheCliente
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.atLeast
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDateTime
import java.util.*

class ClientesServiceImplTest {

    @Mock
    private lateinit var clientesRepositoryImpl: IClientesRepository

    @Mock
    private lateinit var cache: ICacheCliente

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