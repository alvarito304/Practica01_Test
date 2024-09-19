package org.example.clientes.validator

import org.example.clientes.exceptions.CuentaBancariaException
import org.example.clientes.exceptions.DniException
import org.example.clientes.exceptions.TarjetaCreditoException
import org.example.clientes.model.Cliente
import org.example.clientes.validator.validadorCuentaBancaria.ValidadorCuentaBancaria
import org.example.clientes.validator.validadorDniNif.ValidadorDniNif
import org.example.clientes.validator.validadorTarjeta.ValidadorTarjeta

object ValidadorCliente {
    fun esValido(cliente: Cliente): Boolean {
        if (cliente.nombre.length < 2 ) return false
        if (!ValidadorDniNif.esValido(cliente.dni)) throw DniException.DniInvalidoException(cliente.dni)
        if (!ValidadorCuentaBancaria.ibanEsValido(cliente.cuentaBancaria.IBAN)) throw CuentaBancariaException.CuentaBancariaIbanErrorException(cliente.cuentaBancaria.IBAN)
        if (!ValidadorCuentaBancaria.saldoEsValido(cliente.cuentaBancaria.saldo)) throw CuentaBancariaException.CuentaBancariaBalanceErrorException(cliente.cuentaBancaria.saldo)
        if (!ValidadorTarjeta.esNumeroValido(cliente.tarjeta.numero)) throw TarjetaCreditoException.TrajetaNumberErrorException(cliente.tarjeta.numero)
        if (!ValidadorTarjeta.esFechaValida(cliente.tarjeta.fechaCaducidad)) throw TarjetaCreditoException.TrajetaDateErrorException(cliente.tarjeta.fechaCaducidad)
        return true
    }
}