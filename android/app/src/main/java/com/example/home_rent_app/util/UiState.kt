package com.example.home_rent_app.util

// stateFlow를 이용할때 사용할 sealed class
sealed class UiState<out T>(val _data: T?, val _message: String?) {
    object Loading : UiState<Nothing>(_data = null, _message = null)
    data class Success<out R>(val data: R) : UiState<R>(_data = data, _message = null)
    data class Error<Nothing>(val message: String?) :
        UiState<Nothing>(_data = null, _message = message)
}
