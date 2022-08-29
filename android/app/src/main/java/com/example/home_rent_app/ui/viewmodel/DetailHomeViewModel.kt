package com.example.home_rent_app.ui.viewmodel

import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.model.DetailHomeData
import com.example.home_rent_app.data.model.MapResponse
import com.example.home_rent_app.data.repository.detail.DetailRepository
import com.example.home_rent_app.data.repository.map.MapRepository
import com.example.home_rent_app.ui.HomeActivity.User.user
import com.example.home_rent_app.util.UiState
import com.example.home_rent_app.util.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.utils.onErrorSuspend
import io.getstream.chat.android.client.utils.onSuccessSuspend
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailHomeViewModel @Inject constructor(
    private val detailRepository: DetailRepository,
    private val mapRepository: MapRepository,
    private val chatClient: ChatClient
) : ViewModel() {

    private val _detailHomeData = MutableStateFlow<UiState<DetailHomeData>>(UiState.Loading)
    val detailHomeData = _detailHomeData.asStateFlow()

    private val _position = MutableStateFlow<UiState<MapResponse>>(UiState.Loading)
    val position = _position.asStateFlow()

    fun getDetailHomeData(id: Int) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _detailHomeData.value = UiState.Error("네트워크 에러")
        }

        viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            _detailHomeData.value = UiState.Success(detailRepository.getHomeDetail(id))
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

    @WorkerThread
    fun joinNewChannel() = flow {
//        val id = requireNotNull(detailHomeData.value._data?.user?.userId)
        val homeId = detailHomeData.value._data?.id ?: 0
        val profileImage = detailHomeData.value._data?.user?.profileImageUrl ?: ""
        val result = chatClient.createChannel(
            channelType = "messaging",
            channelId = homeId.toString(),
            memberIds = listOf(user.id, "3"),
            extraData = mapOf("homeType" to "rent", "homeId" to "$homeId", "image" to profileImage) // userId 필드 생기면 수정하기
        ).await()

        result.onSuccessSuspend {
            logger("성공 ${it.id}")
            emit(it)
        }
        result.onErrorSuspend {
            logger("실패 ${it.message}")
        }
    }

}
