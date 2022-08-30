package com.example.home_rent_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.home_rent_app.data.dto.WantHomeDetailResponseDTO
import com.example.home_rent_app.data.dto.WantedArticle
import com.example.home_rent_app.data.model.AddWantHomeRequest
import com.example.home_rent_app.data.model.BookmarkRequest
import com.example.home_rent_app.data.model.WantHomeResultRequest
import com.example.home_rent_app.data.repository.wanthome.WantHomeRepository
import com.example.home_rent_app.data.repository.wanthomeresult.WantHomeResultRepository
import com.example.home_rent_app.util.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WantHomeViewModel @Inject constructor(
    private val wantHomeRepository: WantHomeRepository,
    private val wantHomeResultRepository: WantHomeResultRepository
) : ViewModel() {

    // 양방향 데이터 바인딩 이용
    val location = MutableStateFlow(Location.SEOUL)

    private val _goInDate = MutableStateFlow("")
    val goInDate: StateFlow<String> get() = _goInDate

    private val _goOutDate = MutableStateFlow("")
    val goOutDate: StateFlow<String> get() = _goOutDate

    private val _deposit = MutableStateFlow(0)
    val deposit: StateFlow<Int> get() = _deposit

    private val _monthlyRent = MutableStateFlow(0)
    val monthlyRent: StateFlow<Int> get() = _monthlyRent

    private val _detailAddress = MutableStateFlow("")
    val detailAddress: StateFlow<String> get() = _detailAddress

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> get() = _title

    private val _detailContents = MutableStateFlow("")
    val detailContents: StateFlow<String> get() = _detailContents

    private val _searchWord = MutableSharedFlow<String>()
    val searchWord = _searchWord.debounce { 400 }

    private val _wantHomeResult = MutableStateFlow<PagingData<WantedArticle>>(PagingData.empty())
    val wantHomeResult: StateFlow<PagingData<WantedArticle>> get() = _wantHomeResult

    private val _addBookmarkStatusCode = MutableSharedFlow<Int>()
    val addBookmarkStatusCode: SharedFlow<Int> get() = _addBookmarkStatusCode

    private val _deleteBookmarkStatusCode = MutableSharedFlow<Int>()
    val deleteBookmarkStatusCode: SharedFlow<Int> get() = _deleteBookmarkStatusCode

    private val _selectedItemId = MutableStateFlow(0)
    val selectedItemId: StateFlow<Int> get() = _selectedItemId

    private val _wantHomeDetail = MutableStateFlow<WantHomeDetailResponseDTO?>(null)
    val wantHomeDetail: StateFlow<WantHomeDetailResponseDTO?> get() = _wantHomeDetail

    fun addWantHome(userId: Int) {
        viewModelScope.launch {
            _selectedItemId.value = wantHomeRepository.addWantHome(
                AddWantHomeRequest(
                    userId = userId,
                    address = location.value.locationName + " " + detailAddress.value,
                    title = title.value,
                    content = detailContents.value,
                    moveInDate = goInDate.value,
                    moveOutDate = goOutDate.value,
                    rentBudget = monthlyRent.value,
                    depositBudget = deposit.value
                )
            ).id
        }
    }

    fun setRange(rangeList: List<String>) {
        _goInDate.value = rangeList.first()
        _goOutDate.value = rangeList.last()
    }

    fun setDeposit(rangeList: List<Int>) {
        _deposit.value = rangeList.first()
        _monthlyRent.value = rangeList.last()
    }

    fun setDetailAddress(detailAddress: String) {
        _detailAddress.value = detailAddress
    }

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setDetailContents(detailContents: String) {
        _detailContents.value = detailContents
    }

    fun handleSearchWork(searchWord: String) {
        viewModelScope.launch {
            _searchWord.emit(searchWord)
        }
    }

    fun getWantHomeResult(wantHomeResultRequest: WantHomeResultRequest) {
        viewModelScope.launch {
            wantHomeResultRepository.getResult(wantHomeResultRequest).collect { response ->
                _wantHomeResult.value = response
            }
        }
    }

    fun addBookmark(bookmarkRequest: BookmarkRequest) {
        viewModelScope.launch {
            _addBookmarkStatusCode.emit(wantHomeResultRepository.addBookmark(bookmarkRequest).code)
        }
    }

    fun deleteBookmark(bookmarkRequest: BookmarkRequest) {
        viewModelScope.launch {
            _deleteBookmarkStatusCode.emit(wantHomeResultRepository.deleteBookmark(bookmarkRequest).code)
        }
    }

    fun putItemIdAtAdapter(id: Int) {
        _selectedItemId.value = id
    }

    fun getWantHomeDetail(itemId: Int) {
        viewModelScope.launch {
            _wantHomeDetail.value = wantHomeRepository.getWantHome(itemId)
        }
    }
}
