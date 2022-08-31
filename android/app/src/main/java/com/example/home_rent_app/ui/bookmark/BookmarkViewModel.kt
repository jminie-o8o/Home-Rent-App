package com.example.home_rent_app.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.dto.WantedArticleBookmark
import com.example.home_rent_app.data.model.BookmarkRequest
import com.example.home_rent_app.data.repository.bookmark.BookmarkRepository
import com.example.home_rent_app.util.UserSession
import com.example.home_rent_app.util.deleteBookmarkAtView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
    private val userSession: UserSession
) : ViewModel() {

    private var page = 0

    private var hasNext = false

    private val _wantHomeBookmarkResult =
        MutableStateFlow<MutableList<WantedArticleBookmark>>(mutableListOf())
    val wantHomeBookmarkResult: StateFlow<MutableList<WantedArticleBookmark>> = _wantHomeBookmarkResult

    private val _deleteBookmarkStatusCode = MutableSharedFlow<Int>()
    val deleteBookmarkStatusCode: SharedFlow<Int> get() = _deleteBookmarkStatusCode

    init {
        getWantHomeResult(userId = userSession.userId ?: 0)
    }

    fun getWantHomeResult(userId: Int) {
        viewModelScope.launch {
            if (hasNext) {
                return@launch
            }
            val list = mutableListOf<WantedArticleBookmark>()
            list.addAll(_wantHomeBookmarkResult.value)
            list.addAll(bookmarkRepository.getWantBookmark(userId, page))
            _wantHomeBookmarkResult.value = list
            page += 1
        }
    }

    fun deleteBookmark(bookmarkRequest: BookmarkRequest) {
        viewModelScope.launch {
            _deleteBookmarkStatusCode.emit(bookmarkRepository.deleteBookmark(bookmarkRequest).code)
            _wantHomeBookmarkResult.deleteBookmarkAtView(bookmarkRequest.articleId)
        }
    }
}
