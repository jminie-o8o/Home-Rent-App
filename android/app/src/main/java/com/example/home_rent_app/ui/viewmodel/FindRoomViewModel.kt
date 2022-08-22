package com.example.home_rent_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.model.RoomSearchResult
import com.example.home_rent_app.data.repository.findroom.FindRoomRepository
import com.example.home_rent_app.data.repository.login.LoginRepository
import com.example.home_rent_app.util.UiState
import com.example.home_rent_app.util.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class FindRoomViewModel @Inject constructor(
    private val repository: FindRoomRepository,
    private val loginRepository: LoginRepository
): ViewModel() {

    val searchAddress = MutableStateFlow("")

    private val _result = MutableStateFlow<UiState<RoomSearchResult>>(UiState.Loading)
    val result = _result.asStateFlow()

    init {
        viewModelScope.launch {
            searchAddress.debounce(300L)
                .filter { it.isNotEmpty() }
                .onEach { getSearchResult() }
                .launchIn(this)
        }
    }

    private fun getSearchResult() {
       viewModelScope.launch {
           repository.getSearchResult(searchAddress = searchAddress.value)
               .catch { e ->
                   logger("${e.message}")
                   if(e.message == "HTTP 404 ") {
                       logger("e in ${e.message}")
                        repository.refreshAuthToken().collect {
                            loginRepository.saveToken(listOf(it.accessToken.tokenCode, it.refreshToken.tokenCode))
                            loginRepository.setAppSession(listOf(it.accessToken.tokenCode, it.refreshToken.tokenCode))
                        }
                   } else {
                       _result.value = UiState.Error("네트워크 에러")
                   }
                   logger("end")
           }.collect {
               _result.value = UiState.Success(it)
           }
       }
    }

}