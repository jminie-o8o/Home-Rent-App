package com.example.home_rent_app.ui.bookmark.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.dto.WantedArticleBookmark
import com.example.home_rent_app.data.model.Article
import com.example.home_rent_app.data.model.CEHModel
import com.example.home_rent_app.data.repository.bookmark.BookmarkRepository
import com.example.home_rent_app.util.CoroutineException
import com.example.home_rent_app.util.deleteGiveBookmarkAtView
import com.example.home_rent_app.util.deleteWantBookmarkAtView
import com.example.home_rent_app.util.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val FIRST = 0

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

    private var page = 1

    private val _wantHomeBookmarkResult =
        MutableStateFlow<MutableList<WantedArticleBookmark>>(mutableListOf())
    val wantHomeBookmarkResult: StateFlow<MutableList<WantedArticleBookmark>> = _wantHomeBookmarkResult

    private val _giveHomeBookmarkResult =
        MutableStateFlow<MutableList<Article>>(mutableListOf())
    val giveHomeBookmarkResult: StateFlow<MutableList<Article>> = _giveHomeBookmarkResult

    private val _deleteRentBookmarkStatusCode = MutableSharedFlow<Int>()
    val deleteRentBookmarkStatusCode: SharedFlow<Int> get() = _deleteRentBookmarkStatusCode

    private val _deleteWantBookmarkStatusCode = MutableSharedFlow<Int>()
    val deleteWantBookmarkStatusCode: SharedFlow<Int> get() = _deleteWantBookmarkStatusCode

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
        getWantHomeResultAtFirstPage()
        getGiveHomeResultAtFirstPage()
    }

    fun getWantHomeResult() {
        viewModelScope.launch(exceptionHandler) {
            val response = bookmarkRepository.getWantBookmark(page)
            if (!response.hasNext) {
                return@launch
            }
            val list = mutableListOf<WantedArticleBookmark>()
            list.addAll(_wantHomeBookmarkResult.value)
            list.addAll(response.wantedArticles)
            _wantHomeBookmarkResult.value = list
            page += 1
        }
    }

    fun getWantHomeResultAtFirstPage() {
        viewModelScope.launch {
            val response = bookmarkRepository.getWantBookmark(FIRST)
            _wantHomeBookmarkResult.value = response.wantedArticles.toMutableList()
        }
    }

    fun deleteWantHomeBookmark(articleId: Int) {
        viewModelScope.launch(exceptionHandler) {
            _deleteWantBookmarkStatusCode.emit(bookmarkRepository.deleteWantHomeBookmark(articleId).code)
            _wantHomeBookmarkResult.deleteWantBookmarkAtView(articleId)
        }
    }

    fun getRentHomeResult() {
        viewModelScope.launch(exceptionHandler) {
            val response = bookmarkRepository.getGiveBookmark(page)
            if (!response.hasNext) {
                return@launch
            }
            val list = mutableListOf<Article>()
            list.addAll(_giveHomeBookmarkResult.value)
            list.addAll(response.rentArticles)
            _giveHomeBookmarkResult.value = list
            page += 1
        }
    }

    fun getGiveHomeResultAtFirstPage() {
        viewModelScope.launch {
            val response = bookmarkRepository.getGiveBookmark(FIRST)
            _giveHomeBookmarkResult.value = response.rentArticles.toMutableList()
        }
    }

    fun deleteGiveHomeBookmark(articleId: Int) {
        viewModelScope.launch(exceptionHandler) {
            _deleteRentBookmarkStatusCode.emit(bookmarkRepository.deleteRentHomeBookmark(articleId).code)
            _giveHomeBookmarkResult.deleteGiveBookmarkAtView(articleId)
        }
    }
}
