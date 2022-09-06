package com.example.home_rent_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.home_rent_app.data.dto.WantedArticle
import com.example.home_rent_app.data.model.BookmarkRequest
import com.example.home_rent_app.data.model.CEHModel
import com.example.home_rent_app.data.model.WantHomeResultRequest
import com.example.home_rent_app.data.repository.wanthomeresult.WantHomeResultRepository
import com.example.home_rent_app.util.CoroutineException
import com.example.home_rent_app.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WantHomeResultViewModel @Inject constructor(
    private val wantHomeResultRepository: WantHomeResultRepository
) :
    ViewModel() {

    private val _searchWord = MutableSharedFlow<String>()
    val searchWord = _searchWord.debounce { 400 }

    private val _wantHomeResult =
        MutableStateFlow<UiState<PagingData<WantedArticle>>>(UiState.Loading)
    val wantHomeResult: StateFlow<UiState<PagingData<WantedArticle>>> get() = _wantHomeResult

    private val _addBookmarkStatusCode = MutableSharedFlow<Int>()
    val addBookmarkStatusCode: SharedFlow<Int> get() = _addBookmarkStatusCode

    private val _deleteBookmarkStatusCode = MutableSharedFlow<Int>()
    val deleteBookmarkStatusCode: SharedFlow<Int> get() = _deleteBookmarkStatusCode

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

    fun handleSearchWork(searchWord: String) {
        viewModelScope.launch(exceptionHandler) {
            _searchWord.emit(searchWord)
        }
    }

    fun getWantHomeResult(wantHomeResultRequest: WantHomeResultRequest) {
        viewModelScope.launch {
            wantHomeResultRepository.getResult(wantHomeResultRequest)
                .cachedIn(viewModelScope)
                .collect { response ->
                    _wantHomeResult.value = UiState.Success(response)
                }
        }
    }

    fun addBookmark(bookmarkRequest: BookmarkRequest) {
        viewModelScope.launch(exceptionHandler) {
            _addBookmarkStatusCode.emit(wantHomeResultRepository.addBookmark(bookmarkRequest).code)
        }
    }

    fun deleteBookmark(bookmarkRequest: BookmarkRequest) {
        viewModelScope.launch(exceptionHandler) {
            _deleteBookmarkStatusCode.emit(wantHomeResultRepository.deleteBookmark(bookmarkRequest).code)
        }
    }
}
