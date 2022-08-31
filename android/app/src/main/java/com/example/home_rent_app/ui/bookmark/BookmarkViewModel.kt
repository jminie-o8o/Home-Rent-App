package com.example.home_rent_app.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.home_rent_app.data.dto.WantedArticleBookmark
import com.example.home_rent_app.data.model.BookmarkRequest
import com.example.home_rent_app.data.repository.bookmark.BookmarkRepository
import com.example.home_rent_app.util.UserSession
import com.example.home_rent_app.util.deleteBookmarkAtView
import com.example.home_rent_app.util.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
    private val userSession: UserSession
) : ViewModel() {

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
            _wantHomeBookmarkResult.value = bookmarkRepository.getWantBookmark(userId)
//                .cachedIn(viewModelScope)
//                .collect { response ->
//                    _wantHomeBookmarkResult.value = response
//                    logger("bookmark $response")
//                }
        }
    }

    fun deleteBookmark(bookmarkRequest: BookmarkRequest) {
        viewModelScope.launch {
            _deleteBookmarkStatusCode.emit(bookmarkRepository.deleteBookmark(bookmarkRequest).code)
            _wantHomeBookmarkResult.deleteBookmarkAtView(bookmarkRequest.articleId)
        }
    }
}
