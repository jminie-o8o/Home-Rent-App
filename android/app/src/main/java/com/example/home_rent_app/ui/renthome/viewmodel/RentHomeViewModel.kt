package com.example.home_rent_app.ui.renthome.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.dto.AddRentHomeRequest
import com.example.home_rent_app.data.model.ImageUrl
import com.example.home_rent_app.data.model.RoomPicture
import com.example.home_rent_app.data.repository.imageurl.ImageUrlRepository
import com.example.home_rent_app.data.repository.renthome.RentHomeRepository
import com.example.home_rent_app.data.session.UserSession
import com.example.home_rent_app.util.FileController
import com.example.home_rent_app.util.UiState
import com.example.home_rent_app.util.logger
import com.example.home_rent_app.util.type.HouseType
import com.example.home_rent_app.util.type.RentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class RentHomeViewModel @Inject constructor(
    private val rentHomeRepository: RentHomeRepository,
    private val imageUrlRepository: ImageUrlRepository,
    private val fileController: FileController,
    private val userSession: UserSession,
) : ViewModel() {

    private val _page = MutableStateFlow(0)
    val page = _page.asStateFlow()

    val title = MutableStateFlow("")

    val houseType = MutableStateFlow(HouseType.ONE_ROOM)

    val contractType = MutableStateFlow(RentType.MONTHLY)

    val deposit = MutableStateFlow("")

    val monthly = MutableStateFlow("")

    val maintenance = MutableStateFlow("")

    val maintenanceDescription = MutableStateFlow("")

    val availableFrom = MutableStateFlow("")

    val contractExpiresAt = MutableStateFlow("")

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

    private val _overPictures = MutableStateFlow(false)
    val overPictures = _overPictures.asStateFlow()

    private val _houseImages = MutableStateFlow<UiState<ImageUrl>>(UiState.Loading)
    val houseImages = _houseImages.asStateFlow()

    val address = MutableStateFlow("")

    val addressDetail = MutableStateFlow("")

    private val _addressPageState = MutableStateFlow(false)
    val addressPageState = _addressPageState.asStateFlow()

    private val facilities = MutableStateFlow<List<String>>(emptyList())

    private val securityFacilities = MutableStateFlow<List<String>>(emptyList())

    val content = MutableStateFlow("")

    val maxFloor = MutableStateFlow("")

    val thisFloor = MutableStateFlow("")

    private val hasParkingLot = MutableStateFlow(false)

    private val hasBalcony = MutableStateFlow(false)

    private val hasElevator = MutableStateFlow(false)

    private val _rentDetailPageState = MutableStateFlow(false)
    val rentDetailPageState = _rentDetailPageState.asStateFlow()

    private var id = 0

    private val _homeId = MutableStateFlow(-1)
    val homeId = _homeId.asStateFlow()

    fun addAccountRent() {
        viewModelScope.launch {
            val addData = AddRentHomeRequest(
                userId = requireNotNull(userSession.userId),
                houseImages = houseImages.value._data?.images.orEmpty(),
                title = title.value,
                contractType = contractType.value.value,
                rentFee = monthly.value.replace(",", "").toInt(),
                deposit = deposit.value.replace(",", "").toInt(),
                availableFrom = availableFrom.value,
                contractExpiresAt = contractExpiresAt.value,
                maintenanceFeeDescription = maintenanceDescription.value,
                maintenanceFee = maintenance.value.toInt(),
                latitude = 523.12132,
                longitude = 10.232,
                address = address.value,
                addressDetail = addressDetail.value,
                addressDescription = "22-1",
                facilities = facilities.value,
                securityFacilities = securityFacilities.value,
                content = content.value,
                houseType = houseType.value.value,
                maxFloor = maxFloor.value.toInt(),
                thisFloor = thisFloor.value.toInt(),
                hasParkingLot = hasParkingLot.value,
                hasBalcony = hasBalcony.value,
                hasElevator = hasElevator.value
            )
            logger("addData : $addData")
            kotlin.runCatching {
                rentHomeRepository.addRentHome(
                    addData
                )
            }.onFailure {
                logger("${it.message}")
            }.onSuccess {
                _homeId.value = it.id
            }
        }
    }

    fun setHomeDescriptionState() {
        when (contractType.value) {
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
        if (list.isNotEmpty()) {
            list[0].isMain = true
        }
        _picture.value = list
        _overPictures.value = false
    }

    private fun setJeonseHomeDescriptionState() {
        monthly.value = "0"
        _homeDescriptionState.value = title.value != "" &&
            deposit.value != "" &&
            maintenance.value != "" &&
            maintenanceDescription.value != "" &&
            availableFrom.value != "" &&
            contractExpiresAt.value != ""
    }

    private fun setMonthlyHomeDescriptionState() {
        _homeDescriptionState.value = title.value != "" &&
            deposit.value != "" &&
            monthly.value != "" &&
            maintenance.value != "" &&
            maintenanceDescription.value != "" &&
            availableFrom.value != "" &&
            contractExpiresAt.value != ""
    }

    fun setAddressPageState() {
        _addressPageState.value = address.value != "" && addressDetail.value != ""
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
        if (availableFrom.value != "" && contractExpiresAt.value != "") {
            _isCorrectDate.emit(compareToDate() < 0)
        }
    }

    private fun compareToDate() = availableFrom.value.compareTo(contractExpiresAt.value)

    fun getImageUrl() {
        val list = mutableListOf<MultipartBody.Part>()
        _picture.value.forEach { roomPic ->
            logger("URI: ${roomPic.uri}")
            list.add(fileController.uriToMultiPart(roomPic.uri))
        }
        viewModelScope.launch {
            list.forEach {
                logger("image : ${it.headers}, ${it.body}")
            }
            imageUrlRepository.getImageUrl(list).catch { e ->
                _houseImages.value = UiState.Error(e.stackTraceToString())
            }.collect {
                _houseImages.value = UiState.Success(it)
            }
        }
    }

    fun setDetailPageState() {
        _rentDetailPageState.value = content.value != "" &&
            facilities.value.isNotEmpty() &&
            thisFloor.value != "" &&
            maxFloor.value != "" &&
            securityFacilities.value.isNotEmpty()
    }

    fun setFacilitiesList(checkFacilities: List<String>) {
        val list = mutableListOf<String>()
        list.addAll(checkFacilities)
        facilities.value = list
    }

    fun setSecurity(checkList: List<String>) {
        val list = mutableListOf<String>()
        list.addAll(checkList)
        securityFacilities.value = list
    }

    fun setHasElevator(check: Boolean) {
        hasElevator.value = check
    }

    fun setHasParking(check: Boolean) {
        hasParkingLot.value = check
    }

    fun setHasBalcony(check: Boolean) {
        hasBalcony.value = check
    }

    fun setNextPage() {
        _page.value += 1
    }

    fun setBackPage() {
        _page.value -= 1
    }
}
