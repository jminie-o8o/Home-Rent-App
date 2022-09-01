package com.example.home_rent_app.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.repository.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> get() = _message

    fun deleteItem(id: Int) {
        viewModelScope.launch {
            val response = profileRepository.delete(id)
            if (response.statusCode == 200) _message.emit(response.message)
        }
    }
}
