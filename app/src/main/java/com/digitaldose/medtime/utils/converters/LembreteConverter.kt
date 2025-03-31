package com.digitaldose.medtime.utils.converters

import com.digitaldose.medtime.database.models.Lembrete
import com.digitaldose.medtime.database.models.LembreteEntity

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 20/03/2025
 */

fun Lembrete.toLembreteEntity() = LembreteEntity(
    id = id,
    titulo = titulo,
    descricao = descricao,
    data = data,
    hora = hora,
    acao = acao,
    medicamentoId = medicamentoId
)

fun LembreteEntity.toLembrete() = Lembrete(
    id = id,
    titulo = titulo,
    descricao = descricao,
    data = data,
    hora = hora,
    acao = acao,
    medicamentoId = medicamentoId
)
