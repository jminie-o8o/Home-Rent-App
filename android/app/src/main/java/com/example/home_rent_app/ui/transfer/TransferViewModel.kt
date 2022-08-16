package com.example.home_rent_app.ui.transfer

import android.graphics.Bitmap
import android.net.Uri
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

    private val _picture = MutableStateFlow<List<Uri>>(emptyList())
    val picture = _picture.asStateFlow()

    private val _mainPicture = MutableStateFlow<UiState<Uri>>(UiState.Loading)
    val mainPicture = _mainPicture.asStateFlow()

    fun setHomeDescriptionState() {
        when (rentType.value) {
            RentType.JEONSE -> setJeonseHomeDescriptionState()
            RentType.MONTHLY -> setMonthlyHomeDescriptionState()
        }
    }

    fun setPictureUri(uri: Uri) {
        if(_mainPicture.value == UiState.Loading) {
            _mainPicture.value = UiState.Success(uri)
        } else {
            val list = mutableListOf<Uri>()
            list.addAll(_picture.value)
            list.add(uri)
            _picture.value = list
        }
    }

    fun removePicUri(index : Int) {
        val list = mutableListOf<Uri>()
        list.addAll(_picture.value)
        list.removeAt(index)
        _picture.value = list
    }

    fun removeMainPicUri() {
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

    fun replacePic(beforePosition: Int, targetPosition: Int) {
        val list = mutableListOf<Uri>()
        list.addAll(_picture.value)
        val temp = list[beforePosition]
        list[beforePosition] = list[targetPosition]
        list[targetPosition] = temp
        _picture.value = list
    }

    suspend fun checkCorrectDate() {

        if (startDate.value != "" && endDate.value != "") {
            _isCorrectDate.emit(compareToDate() < 0)
        }
    }

    private fun compareToDate() = startDate.value.compareTo(endDate.value)
}
