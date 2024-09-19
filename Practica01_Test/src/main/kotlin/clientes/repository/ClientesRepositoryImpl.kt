package org.example.clientes.repository

import org.example.clientes.model.Cliente
import org.lighthousegames.logging.logging
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

class ClientesRepositoryImpl: IClientesRepository {
    private val logger = logging()
    //"conexion" a la base de datos en este caso es una lista en memoria
    private val db = hashMapOf<UUID, Cliente>()

    override fun getByDni(dni: String): Cliente? {
        logger.debug{"Buscando cliente por dni: $dni"}
        logger.info{"Cliente encontrado: ${db.values.find { it.dni == dni }}" }
        return db.values.find { it.dni == dni }
    }

    override fun getAll(): List<Cliente> {
        logger.debug{"Buscando todos los clientes"}
        logger.info{"total de clientes: ${db.size}"}
        return db.values.toList()
    }

    override fun getById(id: UUID): Cliente? {
        logger.debug{"Buscando cliente por id: $id"}
        logger.info{"total de coincidencias: ${db.values.count { it.id == id }}"}
        return db.values.find { it.id == id }
    }

    override fun save(t: Cliente): Cliente {
        logger.debug{"Guardando cliente: $t"}
        val timeStamp = LocalDateTime.now()
        val newClient = t.copy(createdAt = timeStamp, updatedAt = timeStamp)
        db[t.id] = newClient
        logger.info{"persona guardada con id: ${newClient.id} y nombre: ${newClient.nombre}"}
        return newClient
    }

    override fun update(id: UUID, t: Cliente): Cliente? {
        logger.debug{"Actualizando cliente con id: $id"}
        val Client = db[id]
        if (Client!= null) {
            val updatedClient = t.copy(nombre = t.nombre, dni = t.dni, cuentaBancaria = t.cuentaBancaria, tarjeta = t.tarjeta , updatedAt = LocalDateTime.now())
            db[id] = updatedClient
            logger.info{"cliente actualizado con id: ${updatedClient.id} y nombre: ${updatedClient.nombre}"}
            return updatedClient
        } else {
            logger.warn{"No se encontró el cliente con id: $id"}
            return null
        }
    }

    override fun delete(id: UUID): Cliente? {
        logger.debug{"Eliminando cliente con id: $id"}
        val client = db[id]
        if (client!= null) {
            db.remove(id)
            logger.info{"cliente eliminado con id: ${client.id} y nombre: ${client.nombre}"}
            return client
        } else {
            logger.warn{"No se encontró el cliente con id: $id"}
            return null
        }
    }
}