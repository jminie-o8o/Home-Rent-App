package com.example.home_rent_app.data.model

data class CEHModel(
    val throwable: Throwable?,
    val errorMessage: String?,
) {
    companion object {
        const val INITIAL_MESSAGE = ""
    }
}
