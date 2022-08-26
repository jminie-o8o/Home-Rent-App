package com.example.home_rent_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.home_rent_app.util.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class WantHomeViewModel @Inject constructor() : ViewModel() {

    val location = MutableStateFlow(Location.SEOUL)

    private val _goInDate = MutableStateFlow("")
    val goInDate: StateFlow<String> get() =  _goInDate

    private val _goOutDate = MutableStateFlow("")
    val goOutDate: StateFlow<String> get() =  _goOutDate

    private val _deposit = MutableStateFlow(0)
    val deposit: StateFlow<Int> get() =  _deposit

    private val _monthlyRent = MutableStateFlow(0)
    val monthlyRent: StateFlow<Int> get() =  _monthlyRent

    fun setRange(rangeList: List<String>) {
        _goInDate.value = rangeList.first()
        _goOutDate.value = rangeList.last()
    }

    fun setDeposit(rangeList: List<Int>) {
        _deposit.value = rangeList.first()
        _monthlyRent.value = rangeList.last()
    }
}
