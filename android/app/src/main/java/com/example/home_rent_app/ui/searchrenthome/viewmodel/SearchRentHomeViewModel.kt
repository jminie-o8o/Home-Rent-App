package com.example.home_rent_app.ui.searchrenthome.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.model.CEHModel
import com.example.home_rent_app.data.model.RoomSearchResult
import com.example.home_rent_app.data.repository.bookmark.BookmarkRepository
import com.example.home_rent_app.data.repository.searchrenthome.SearchRentHomeRepository
import com.example.home_rent_app.util.CoroutineException
import com.example.home_rent_app.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchRentHomeViewModel @Inject constructor(
    private val repository: SearchRentHomeRepository,
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

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

    fun addBookmark(articleId: Int) {
        viewModelScope.launch(exceptionHandler) {
            if (bookmarkRepository.addRentHomeBookmark(articleId).code == 200) {
                _bookmarkEvent.emit("저장")
            }
        }
    }

    fun deleteBookmark(articleId: Int) {
        viewModelScope.launch(exceptionHandler) {
            if (bookmarkRepository.deleteRentHomeBookmark(articleId).code == 200) {
                _bookmarkEvent.emit("삭제")
            }
        }
    }
}
