package com.pominova.surfmemesapp.util

object AppConstant {
    const val BASE_URL =
        "https://virtserver.swaggerhub.com"
    const val AUTH_URL = "/AndroidSchool/SurfAndroidSchool/1.0.0/auth/login"
    const val WRONG_AUTH_DATA_ERROR =
        "Во время запроса произошла ошибка, возможно вы неверно ввели логин/пароль"
    const val EMPTY_FIELD_ERROR = "Поле не может быть пустым"

    const val APP_REFERENCES = "common_settings"

    const val TOKEN_FIELD = "token"
    const val ID_FIELD = "id"
    const val USERNAME_FIELD = "username"
    const val FIRST_NAME_FIELD = "firstName"
    const val LAST_NAME_FIELD = "lastName"
    const val USER_DESCRIPTION_FIELD = "userDescription"
}
