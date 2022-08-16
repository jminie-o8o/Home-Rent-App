package com.example.home_rent_app.ui.transfer

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.home_rent_app.data.model.RoomPicture
import com.example.home_rent_app.data.repository.transfer.TransferRepository
import com.example.home_rent_app.util.RentType
import com.example.home_rent_app.util.RoomType
import com.example.home_rent_app.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(private val transferRepository: TransferRepository) : ViewModel() {

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


    private val _isCorrectDate = MutableSharedFlow<Boolean>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val isCorrectDate = _isCorrectDate.asSharedFlow()

    private val _picture = MutableStateFlow<List<RoomPicture>>(emptyList())
    val picture = _picture.asStateFlow()

    private val _mainPicture = MutableStateFlow<UiState<Uri>>(UiState.Loading)
    val mainPicture = _mainPicture.asStateFlow()

    private val _overPictures = MutableStateFlow(false)
    val overPictures = _overPictures.asStateFlow()

    private var id = 0

    fun setHomeDescriptionState() {
        when (rentType.value) {
            RentType.JEONSE -> setJeonseHomeDescriptionState()
            RentType.MONTHLY -> setMonthlyHomeDescriptionState()
        }
    }

    fun setPictureUri(uri: Uri) {
        when (_picture.value.size) {
            0 -> {
                _picture.value = listOf(RoomPicture(id, uri, true))
                id++
            }
            6 -> {
                _overPictures.value = true
            }
            else -> {
                val list = mutableListOf<RoomPicture>()
                list.addAll(_picture.value)
                list.add(RoomPicture(id, uri))
                _picture.value = list
                id++
            }
        }
    }

    fun removePicUri(index: Int) {
        val list = mutableListOf<RoomPicture>()
        list.addAll(_picture.value)
        list.removeAt(index)
        list[0].isMain = true
        _picture.value = list
        _overPictures.value = false
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
        when {
            targetPosition == 0 -> {
                val list = mutableListOf<RoomPicture>()
                list.addAll(_picture.value)
                list[targetPosition].isMain = false
                list[beforePosition].isMain = true

                val temp = list[beforePosition]
                list[beforePosition] = list[targetPosition]
                list[targetPosition] = temp

                _picture.value = list
            }
            beforePosition == 0 -> {
                val list = mutableListOf<RoomPicture>()
                list.addAll(_picture.value)
                list[beforePosition].isMain = false
                list[targetPosition].isMain = true

                val temp = list[beforePosition]
                list[beforePosition] = list[targetPosition]
                list[targetPosition] = temp
                _picture.value = list
            }
            else -> {
                val list = mutableListOf<RoomPicture>()
                list.addAll(_picture.value)
                val temp = list[beforePosition]
                list[beforePosition] = list[targetPosition]
                list[targetPosition] = temp
                _picture.value = list
            }
        }
    }

    suspend fun checkCorrectDate() {

        if (startDate.value != "" && endDate.value != "") {
            _isCorrectDate.emit(compareToDate() < 0)
        }
    }

    private fun compareToDate() = startDate.value.compareTo(endDate.value)

    fun getImageUrl() {

        transferRepository.getImageUrl()
    }

}
