package com.digitaldose.medtime.utils.constants

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 13/11/2024
 */
class Routes {
    companion object {
        const val LOGIN = "login"
        const val SIGNUP = "signup"
        const val HOME = "home"
        const val CREATE_MEDICAMENTO = "create_medicamento"
        const val UPDATE_MEDICAMENTO = "update_medicamento/{medicamentoId}"
        const val DELETE_MEDICAMENTO = "delete_medicamento"
        const val MENU = "menu"
        const val SETTINGS = "settings"
        const val USER_PROFILE = "user_profile/{userId}"
        const val USER_PROFILE_EDIT = "user_profile_edit/{userId}"
        const val MAIN = "main"

    }
}