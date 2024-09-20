package org.example.clientes.exceptions

sealed class TarjetaCreditoException(message: String) : Exception(message) {
    class TrajetaNumberErrorException(numero: String) : TarjetaCreditoException("Error en el numero de la tarjeta de crédito: $numero")
    class TrajetaDateErrorException(fecha: String) : TarjetaCreditoException("Error en la fecha de vencimiento de la tarjeta de crédito: $fecha")
}