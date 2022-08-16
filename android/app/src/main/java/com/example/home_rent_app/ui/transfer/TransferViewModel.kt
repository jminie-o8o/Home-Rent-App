package com.example.home_rent_app.ui.transfer

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.home_rent_app.util.RentType
import com.example.home_rent_app.util.RoomType
import com.example.home_rent_app.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _isCorrectDate =
        MutableSharedFlow<Boolean>(
            replay = 0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    val isCorrectDate = _isCorrectDate.asSharedFlow()

    private val _picture = MutableStateFlow<List<Bitmap>>(emptyList())
    val picture = _picture.asStateFlow()

    private val _mainPicture = MutableStateFlow<UiState<Bitmap>>(UiState.Loading)
    val mainPicture = _mainPicture.asStateFlow()

    fun setHomeDescriptionState() {
        when (rentType.value) {
            RentType.JEONSE -> setJeonseHomeDescriptionState()
            RentType.MONTHLY -> setMonthlyHomeDescriptionState()
        }
    }

    fun setPicture(bitmap: Bitmap) {
        if(_mainPicture.value == UiState.Loading) {
            _mainPicture.value = UiState.Success(bitmap)
        } else {
            val list = mutableListOf<Bitmap>()
            list.addAll(_picture.value)
            list.add(bitmap)
            _picture.value = list
        }
    }

    fun removePic(index : Int) {
        val list = mutableListOf<Bitmap>()
        list.addAll(_picture.value)
        list.removeAt(index)
        _picture.value = list
    }

    fun removeMainPic() {
        _mainPicture.value = UiState.Loading
    }

    private fun setJeonseHomeDescriptionState() {
        if (title.value != "" &&
            deposit.value != "" &&
            maintenance.value != "" &&
            maintenanceDescription.value != "" &&
            startDate.value != "" &&
            endDate.value != ""
        ) {
            _homeDescriptionState.value = true
        }
    }

    private fun setMonthlyHomeDescriptionState() {
        if (title.value != "" &&
            deposit.value != "" &&
            monthly.value != "" &&
            maintenance.value != "" &&
            maintenanceDescription.value != "" &&
            startDate.value != "" &&
            endDate.value != ""
        ) {
            _homeDescriptionState.value = true
        }
    }

    suspend fun checkCorrectDate() {

        if (startDate.value != "" && endDate.value != "") {
            _isCorrectDate.emit(compareToDate() < 0)
        }
    }

    private fun compareToDate() = startDate.value.compareTo(endDate.value)
}
