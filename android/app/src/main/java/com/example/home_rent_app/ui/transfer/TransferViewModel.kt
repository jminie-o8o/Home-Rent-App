package com.example.home_rent_app.ui.transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.util.RentType
import com.example.home_rent_app.util.RoomType
import com.example.home_rent_app.util.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor() : ViewModel() {

    val title = MutableStateFlow("")

    val roomType = MutableStateFlow(RoomType.ONE_ROOM)

    val rentType = MutableStateFlow(RentType.MONTHLY)

    val deposit = MutableStateFlow("")

    val monthly = MutableStateFlow("")

    val maintenance = MutableStateFlow("")

    val maintenanceDescription = MutableStateFlow("")

    val startDate = MutableStateFlow("")

    val endDate = MutableStateFlow("")

    private val _homeDescriptionState = MutableStateFlow(false)
    val homeDescriptionState = _homeDescriptionState.asStateFlow()

    private val _isCorrectDate = MutableSharedFlow<Boolean>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val isCorrectDate = _isCorrectDate.asSharedFlow()

    fun setHomeDescriptionState() {
        when (rentType.value) {
            RentType.JEONSE -> setJeonseHomeDescriptionState()
            RentType.MONTHLY -> setMonthlyHomeDescriptionState()
        }
    }

    private fun setJeonseHomeDescriptionState() {
        if (title.value != ""
            && deposit.value != ""
            && maintenance.value != ""
            && maintenanceDescription.value != ""
            && startDate.value != ""
            && endDate.value != ""
        ) {
            _homeDescriptionState.value = true
        }
    }

    private fun setMonthlyHomeDescriptionState() {
        if (title.value != ""
            && deposit.value != ""
            && monthly.value != ""
            && maintenance.value != ""
            && maintenanceDescription.value != ""
            && startDate.value != ""
            && endDate.value != ""
        ) {
            _homeDescriptionState.value = true
        }
    }

    suspend fun checkCorrectDate() {
        logger("compareToDate() < 0 ${compareToDate() < 0}")
        if(startDate.value != "" && endDate.value != "") {

            _isCorrectDate.emit(compareToDate() < 0)
        }
    }

    private fun compareToDate() = startDate.value.compareTo(endDate.value)

}