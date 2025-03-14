package com.digitaldose.medtime.database.models

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 11/03/2025
 */
data class User(
    val id: String? = null,
    val name: String,
    val email: String,
    val password: String,
    val photoUrl: String? = null,
    val sexo: String? = null,
    val dataNascimento: String? = null,
    val altura: Int? = null,
    val peso: Double? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val ativo: Boolean = true,

    )
