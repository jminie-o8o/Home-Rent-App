package com.example.home_rent_app.ui.viewmodel

import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.model.DetailHomeData
import com.example.home_rent_app.data.repository.detail.DetailRepository
import com.example.home_rent_app.ui.HomeActivity.User.user
import com.example.home_rent_app.util.UiState
import com.example.home_rent_app.util.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.client.utils.onErrorSuspend
import io.getstream.chat.android.client.utils.onSuccessSuspend
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailHomeViewModel @Inject constructor(
    private val detailRepository: DetailRepository,
    private val chatClient: ChatClient
) : ViewModel() {

    private val _detailHomeData = MutableStateFlow<UiState<DetailHomeData>>(UiState.Loading)
    val detailHomeData = _detailHomeData.asStateFlow()

    fun getDetailHomeData(id: Int) {
        viewModelScope.launch {
            detailRepository.getHomeDetail(id)
                .catch { exception ->
                    _detailHomeData.value = UiState.Error("데이터 불러오기가 실패하였습니다.")
                    logger("load fail: Detail Home Data ${exception.printStackTrace()}")
                }.collect {
                    _detailHomeData.value = UiState.Success(it)
                }
        }
    }

    @WorkerThread
    fun joinNewChannel() = flow {
//        val id = requireNotNull(detailHomeData.value._data?.user?.userId)
        val homeId = requireNotNull(detailHomeData.value._data?.id)
        val profileImage = requireNotNull(detailHomeData.value._data?.user?.profileImageUrl)
        val result = chatClient.createChannel(
            channelType = "messaging",
            channelId = homeId.toString(),
            memberIds = listOf(user.id, "ese111"),
            extraData = mapOf("homeType" to "rent", "homeId" to "$homeId", "image" to profileImage) // userId 필드 생기면 수정하기
        ).await()

        result.onSuccessSuspend {
            logger("onSuccessSuspend   ============================")
            emit(it)
        }
        result.onErrorSuspend {
            logger("실패다 ${it.message}")
        }
    }

}
