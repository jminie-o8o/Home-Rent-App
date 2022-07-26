package com.example.home_rent_app.ui.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.model.DetailHomeData
import com.example.home_rent_app.data.model.MapResponse
import com.example.home_rent_app.data.repository.detail.DetailRepository
import com.example.home_rent_app.data.repository.map.MapRepository
import com.example.home_rent_app.data.session.UserSession
import com.example.home_rent_app.util.ChatChannel
import com.example.home_rent_app.util.RENT
import com.example.home_rent_app.util.UiState
import com.example.home_rent_app.util.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailHomeViewModel @Inject constructor(
    private val detailRepository: DetailRepository,
    private val mapRepository: MapRepository,
    private val chatChannel: ChatChannel,
    private val userSession: UserSession
) : ViewModel() {

    private val _detailHomeData = MutableStateFlow<UiState<DetailHomeData>>(UiState.Loading)
    val detailHomeData = _detailHomeData.asStateFlow()

    private val _position = MutableStateFlow<UiState<MapResponse>>(UiState.Loading)
    val position = _position.asStateFlow()

    fun getDetailHomeData(id: Int) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _detailHomeData.value = UiState.Error("네트워크 에러")
            logger("CoroutineExceptionHandler ")
        }

        viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            _detailHomeData.value = UiState.Success(detailRepository.getHomeDetail(id))
            logger("Success ")
        }
    }

    fun getPosition() {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _detailHomeData.value = UiState.Error("네트워크 에러")
        }

        viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            val address = _detailHomeData.value._data?.address.orEmpty()
            _position.value = UiState.Success(mapRepository.getAddress(address))
        }
    }

    fun joinNewChannel() = chatChannel.joinNewChannel(
        RENT,
        userSession.userId.toString(),
        requireNotNull(detailHomeData.value._data?.user?.userId).toString(),
        detailHomeData.value._data?.id.toString(),
        detailHomeData.value._data?.user?.profileImageUrl ?: ""
    )
//    fun joinNewChannel() = flow {
//        val id = requireNotNull(detailHomeData.value._data?.user?.userId)
//        val homeId = detailHomeData.value._data?.id ?: 0
//        val profileImage = detailHomeData.value._data?.user?.profileImageUrl ?: ""
//        val result = chatClient.createChannel(
//            channelType = "messaging",
//            channelId = homeId.toString(),
//            memberIds = listOf(userSession.userId.toString(), id.toString()),
//            extraData = mapOf("homeType" to "rent", "homeId" to "$homeId", "image" to profileImage) // userId 필드 생기면 수정하기
//        ).await()
//
//        result.onSuccessSuspend {
//            logger("성공 ${it.id}")
//            emit(it)
//        }
//        result.onErrorSuspend {
//            logger("실패 ${it.message}")
//        }
//    }
}
