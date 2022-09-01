package com.example.home_rent_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.model.RoomSearchResult
import com.example.home_rent_app.data.repository.findroom.FindRoomRepository
import com.example.home_rent_app.data.repository.token.TokenRepository
import com.example.home_rent_app.util.CoroutineException
import com.example.home_rent_app.util.UiState
import com.example.home_rent_app.util.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class FindRoomViewModel @Inject constructor(
    private val repository: FindRoomRepository,
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
               .catch { exception ->
                   _result.value = UiState.Error("네트워크 에러")
           }.collect {
               _result.value = UiState.Success(it)
           }
       }
    }

}
