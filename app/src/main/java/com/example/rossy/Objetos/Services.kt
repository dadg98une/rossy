package com.example.rossy.Objetos

import java.text.DateFormat

data class Services (
    val id: String,
    val entregado: Boolean,
    val folio: Int,
    val fecha: DateFormat,
    val mesa: String,
    val total: Double,
    val pedido: String
)