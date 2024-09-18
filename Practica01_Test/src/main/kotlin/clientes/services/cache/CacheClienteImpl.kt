package org.example.clientes.services.cache

import org.example.clientes.model.Cliente
import java.util.*

class CacheClienteImpl() : ICacheCliente {
    private val cache: MutableMap<UUID, Cliente> = mutableMapOf()
    override fun get(key: UUID): Cliente? {
        return cache[key]
    }

    override fun put(key: UUID, value: Cliente) {
        if (cache.size == 5) {
            cache.remove(cache.keys.first())
            cache[key] = value
        } else {
            cache[key] = value
        }
    }

    override fun remove(key: UUID) {
        cache.remove(key)
    }

    override fun clear() {
        cache.clear()
    }

    override fun size(): Int {
        return cache.size
    }
}