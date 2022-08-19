package com.example.home_rent_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_rent_app.data.repository.wanthomeresult.WantHomeResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WantHomeResultViewModel @Inject constructor(private val wantHomeResultRepository: WantHomeResultRepository) : ViewModel() {

    private val _searchWord = MutableSharedFlow<String>()
    val searchWord get() = _searchWord.debounce { 400 }

    fun handleSearchWork(searchWord: String) {
        viewModelScope.launch {
            _searchWord.emit(searchWord)
        }
    }

    fun getWantHomeResult(searchWord: String) {
        viewModelScope.launch {
            wantHomeResultRepository.getResult()
        }
    }
}
