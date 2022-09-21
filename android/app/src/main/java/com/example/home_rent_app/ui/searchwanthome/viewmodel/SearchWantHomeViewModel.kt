package com.example.home_rent_app.ui.searchwanthome.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.home_rent_app.data.dto.WantedArticle
import com.example.home_rent_app.data.model.CEHModel
import com.example.home_rent_app.data.model.WantHomeResultRequest
import com.example.home_rent_app.data.repository.searchwanthome.SearchWantHomeRepository
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
class SearchWantHomeViewModel @Inject constructor(
    private val searchWantHomeRepository: SearchWantHomeRepository
) :
    ViewModel() {

    private val _searchWord = MutableSharedFlow<String>()
    val searchWord = _searchWord.debounce { 400L }

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
            searchWantHomeRepository.getResult(wantHomeResultRequest)
                .cachedIn(viewModelScope)
                .collect { response ->
                    _wantHomeResult.value = UiState.Success(response)
                }
        }
    }

    fun addBookmark(articleId: Int) {
        viewModelScope.launch(exceptionHandler) {
            _addBookmarkStatusCode.emit(searchWantHomeRepository.addBookmark(articleId).code)
        }
    }

    fun deleteBookmark(articleId: Int) {
        viewModelScope.launch(exceptionHandler) {
            _deleteBookmarkStatusCode.emit(searchWantHomeRepository.deleteBookmark(articleId).code)
        }
    }
}
