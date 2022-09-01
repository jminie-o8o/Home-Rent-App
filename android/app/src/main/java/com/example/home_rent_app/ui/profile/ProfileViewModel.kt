package com.example.home_rent_app.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.dto.RentArticleProfile
import com.example.home_rent_app.data.dto.WantArticleProfile
import com.example.home_rent_app.data.repository.profile.ProfileRepository
import com.example.home_rent_app.util.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val userSession: UserSession
) : ViewModel() {

    private var page = 0

    private val _giveHomeProfileResult =
        MutableStateFlow<MutableList<RentArticleProfile>>(mutableListOf())
    val giveHomeProfileResult: StateFlow<MutableList<RentArticleProfile>> = _giveHomeProfileResult

    private val _wantHomeProfileResult =
        MutableStateFlow<MutableList<WantArticleProfile>>(mutableListOf())
    val wantHomeProfileResult: StateFlow<MutableList<WantArticleProfile>> = _wantHomeProfileResult

    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> get() = _message

    init {
        getGiveHomeProfile(userSession.userId ?: 0)
        getWantHomeProfile(userSession.userId ?: 0)
    }

    fun getGiveHomeProfile(userId: Int) {
        viewModelScope.launch {
            val response = profileRepository.getGiveHomeProfileResult(userId, page)
            if (response.hasNext) {
                return@launch
            }
            val list = mutableListOf<RentArticleProfile>()
            list.addAll(_giveHomeProfileResult.value)
            list.addAll(response.rentArticles)
            _giveHomeProfileResult.value = list
            page += 1
        }
    }

    fun getWantHomeProfile(userId: Int) {
        viewModelScope.launch {
            val response = profileRepository.getWantHomeProfileResult(userId, page)
            if (response.hasNext) {
                return@launch
            }
            val list = mutableListOf<WantArticleProfile>()
            list.addAll(_wantHomeProfileResult.value)
            list.addAll(response.wantedArticles)
            _wantHomeProfileResult.value = list
            page += 1
        }
    }

    fun deleteGiveItem(id: Int) {
        viewModelScope.launch {
            val response = profileRepository.delete(id)
            if (response.statusCode == 200) _message.emit(response.message)
        }
    }

    fun deleteWantItem(id: Int) {
        viewModelScope.launch {
            val response = profileRepository.deleteWantHome(id)
            if (response.statusCode == 200) _message.emit(response.message)
        }
    }
}
