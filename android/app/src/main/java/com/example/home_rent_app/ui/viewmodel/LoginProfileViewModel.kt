package com.example.home_rent_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.repository.loginProfile.LoginProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginProfileViewModel @Inject constructor(
    private val loginProfileRepository: LoginProfileRepository
) : ViewModel() {

    private val _nickNameCheck: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val nickNameCheck: StateFlow<Boolean> get() = _nickNameCheck

    fun checkNickName(nickName: String) {
        viewModelScope.launch {
            _nickNameCheck.value = loginProfileRepository.checkNickName(nickName).isDuplicated
        }
    }
}