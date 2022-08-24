package com.example.home_rent_app.util

sealed class ResponseState<out T>(val _data: T?, val _message: String?) {
    data class Success<out R>(val data: R) : UiState<R>(_data = data, _message = null)
    data class Error<Nothing>(val message: String?) :
        UiState<Nothing>(_data = null, _message = message)
}