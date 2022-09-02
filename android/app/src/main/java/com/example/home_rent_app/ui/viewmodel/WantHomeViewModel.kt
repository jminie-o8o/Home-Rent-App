package com.example.home_rent_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.dto.WantHomeDetailResponseDTO
import com.example.home_rent_app.data.model.AddWantHomeRequest
import com.example.home_rent_app.data.model.CEHModel
import com.example.home_rent_app.data.repository.wanthome.WantHomeRepository
import com.example.home_rent_app.util.CoroutineException
import com.example.home_rent_app.util.ItemIdSession
import com.example.home_rent_app.util.Location
import com.example.home_rent_app.util.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WantHomeViewModel @Inject constructor(
    private val wantHomeRepository: WantHomeRepository,
    private val idSession: ItemIdSession
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

    private val _wantHomeDetail = MutableStateFlow<WantHomeDetailResponseDTO?>(null)
    val wantHomeDetail: StateFlow<WantHomeDetailResponseDTO?> get() = _wantHomeDetail

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

    suspend fun addWantHome(userId: Int): Int {
        val response = viewModelScope.async(exceptionHandler) {
            val response = wantHomeRepository.addWantHome(
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
            )
            response.id
        }
        return response.await()
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

    fun getWantHomeDetail(itemId: Int) {
        viewModelScope.launch(exceptionHandler) {
            _wantHomeDetail.value = wantHomeRepository.getWantHome(itemId)
        }
    }
}
