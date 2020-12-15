package com.pominova.surfmemesapp.util

object AppConstant {
    const val PROGRESS_BUTTON_DELAY = 1000L
    const val SPLASH_ACTIVITY_DELAY = 300L

    const val BASE_URL =
        "https://r2.mocker.surfstudio.ru"
    const val AUTH_URL = "/android_vsu/auth/login"
    const val MEMES_URL = "/android_vsu/memes"

    const val WRONG_AUTH_DATA_ERROR =
        "Во время запроса произошла ошибка, возможно вы неверно ввели логин/пароль"
    const val EMPTY_FIELD_ERROR = "Поле не может быть пустым"
    const val PASSWORD_LENGTH_HELPER_MESSAGE = "Пароль должен содержать 6 символов"
    const val EMPTY_HELPER_MESSAGE = " "

    const val APP_REFERENCES = "common_settings"

    const val TOKEN_FIELD = "token"
    const val ID_FIELD = "id"
    const val USERNAME_FIELD = "username"
    const val FIRST_NAME_FIELD = "firstName"
    const val LAST_NAME_FIELD = "lastName"
    const val USER_DESCRIPTION_FIELD = "userDescription"

    const val ENTER_MESSAGE = "ВОЙТИ"
}
