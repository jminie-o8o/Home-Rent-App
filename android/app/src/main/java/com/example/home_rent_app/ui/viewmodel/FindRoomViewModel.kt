package com.example.home_rent_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.model.BookmarkRequest
import com.example.home_rent_app.data.model.CEHModel
import com.example.home_rent_app.data.model.RoomSearchResult
import com.example.home_rent_app.data.repository.bookmark.BookmarkRepository
import com.example.home_rent_app.data.repository.findroom.FindRoomRepository
import com.example.home_rent_app.data.repository.token.TokenRepository
import com.example.home_rent_app.data.repository.wanthomeresult.WantHomeResultRepository
import com.example.home_rent_app.util.CoroutineException
import com.example.home_rent_app.util.UiState
import com.example.home_rent_app.util.UserSession
import com.example.home_rent_app.util.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class FindRoomViewModel @Inject constructor(
    private val repository: FindRoomRepository,
    private val userSession: UserSession
): ViewModel() {

    val searchAddress = MutableStateFlow("")

    private val _result = MutableStateFlow<UiState<RoomSearchResult>>(UiState.Loading)
    val result = _result.asStateFlow()

    private val _bookmarkEvent = MutableSharedFlow<String>(replay = 0)
    val bookmarkEvent = _bookmarkEvent.asSharedFlow()

    private val _error = MutableSharedFlow<CEHModel>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val error = _error.asSharedFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _error.emit(CoroutineException.checkThrowable(throwable))
        }
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            searchAddress.debounce(300L)
                .filter { it.isNotEmpty() }
                .onEach { getSearchResult() }
                .launchIn(this)
        }
    }

    private fun getSearchResult() {
       viewModelScope.launch(exceptionHandler) {
           repository.getSearchResult(searchAddress = searchAddress.value)
               .catch { exception ->
                   _result.value = UiState.Error("네트워크 에러")
           }.collect {
               _result.value = UiState.Success(it)
           }
       }
    }

    fun addBookmark(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            if (repository.addBookmark(BookmarkRequest(userSession.userId ?: 0, id)).code == 200) {
                _bookmarkEvent.emit("저장")
            }
        }
    }

    fun deleteBookmark(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            if(repository.deleteBookmark(BookmarkRequest(userSession.userId ?: 0, id)).code == 200) {
                _bookmarkEvent.emit("삭제")
            }
        }
    }
}
