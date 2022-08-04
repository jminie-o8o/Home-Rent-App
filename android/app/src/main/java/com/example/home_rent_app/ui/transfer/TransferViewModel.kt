package com.example.home_rent_app.ui.transfer

import androidx.lifecycle.ViewModel
import com.example.home_rent_app.util.RentType
import com.example.home_rent_app.util.RoomType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(): ViewModel() {

    val title = MutableStateFlow("")

    val roomType = MutableStateFlow(RoomType.ONE_ROOM)

    val rentType = MutableStateFlow(RentType.MONTHLY)

    val deposit = MutableStateFlow("")

    val monthly = MutableStateFlow("")

    val maintenance = MutableStateFlow("")

    val maintenanceDescription = MutableStateFlow("")

    private val _startDate = MutableStateFlow("")
    val startDate = _startDate.asStateFlow()

    private val _endDate = MutableStateFlow("")
    val endDate = _endDate.asStateFlow()


}