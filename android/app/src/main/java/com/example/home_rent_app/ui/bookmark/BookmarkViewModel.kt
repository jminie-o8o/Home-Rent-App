package com.example.home_rent_app.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.dto.RentArticleBookmark
import com.example.home_rent_app.data.dto.WantedArticleBookmark
import com.example.home_rent_app.data.model.BookmarkRequest
import com.example.home_rent_app.data.model.CEHModel
import com.example.home_rent_app.data.repository.bookmark.BookmarkRepository
import com.example.home_rent_app.util.CoroutineException
import com.example.home_rent_app.util.UserSession
import com.example.home_rent_app.util.deleteGiveBookmarkAtView
import com.example.home_rent_app.util.deleteWantBookmarkAtView
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

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
    private val userSession: UserSession
) : ViewModel() {

    private var page = 0

    private val _wantHomeBookmarkResult =
        MutableStateFlow<MutableList<WantedArticleBookmark>>(mutableListOf())
    val wantHomeBookmarkResult: StateFlow<MutableList<WantedArticleBookmark>> = _wantHomeBookmarkResult

    private val _giveHomeBookmarkResult =
        MutableStateFlow<MutableList<RentArticleBookmark>>(mutableListOf())
    val giveHomeBookmarkResult: StateFlow<MutableList<RentArticleBookmark>> = _giveHomeBookmarkResult

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

    init {
        getWantHomeResult(userId = userSession.userId ?: 0)
        getGiveHomeResult(userId = userSession.userId ?: 0)
    }

    fun getWantHomeResult(userId: Int) {
        viewModelScope.launch(exceptionHandler) {
            val response = bookmarkRepository.getWantBookmark(userId, page)
            if (response.hasNext) {
                return@launch
            }
            val list = mutableListOf<WantedArticleBookmark>()
            list.addAll(_wantHomeBookmarkResult.value)
            list.addAll(response.wantedArticles)
            _wantHomeBookmarkResult.value = list
            page += 1
        }
    }

    fun deleteWantHomeBookmark(bookmarkRequest: BookmarkRequest) {
        viewModelScope.launch(exceptionHandler) {
            _deleteBookmarkStatusCode.emit(bookmarkRepository.deleteBookmark(bookmarkRequest).code)
            _wantHomeBookmarkResult.deleteWantBookmarkAtView(bookmarkRequest.articleId)
        }
    }

    fun getGiveHomeResult(userId: Int) {
        viewModelScope.launch(exceptionHandler) {
            val response = bookmarkRepository.getGiveBookmark(userId, page)
            if (response.hasNext) {
                return@launch
            }
            val list = mutableListOf<RentArticleBookmark>()
            list.addAll(_giveHomeBookmarkResult.value)
            list.addAll(response.rentArticles)
            _giveHomeBookmarkResult.value = list
            page += 1
        }
    }

    fun deleteGiveHomeBookmark(bookmarkRequest: BookmarkRequest) {
        viewModelScope.launch(exceptionHandler) {
            _deleteBookmarkStatusCode.emit(bookmarkRepository.deleteBookmark(bookmarkRequest).code)
            _giveHomeBookmarkResult.deleteGiveBookmarkAtView(bookmarkRequest.articleId)
        }
    }
}
