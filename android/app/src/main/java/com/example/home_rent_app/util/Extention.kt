package com.example.home_rent_app.util

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.example.home_rent_app.data.dto.WantedArticleBookmark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

fun <T> Fragment.collectStateFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(collect)
        }
    }
}

fun <T> AppCompatActivity.collectStateFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(collect)
        }
    }
}

fun <T> Fragment.collectLatestStateFlow(flow: Flow<T>, collector: suspend (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collector)
        }
    }
}

fun <T> AppCompatActivity.collectLatestStateFlow(flow: Flow<T>, collector: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collector)
        }
    }
}

fun View.setLikeClickEvent(
    uiScope: CoroutineScope,
    windowDuration: Long = 3000L,
    onClick: () -> Unit
) {
    clickToFlow()
        .throttleFirst(windowDuration)
        .onEach { onClick.invoke() }
        .launchIn(uiScope)
}

fun MutableStateFlow<MutableList<WantedArticleBookmark>>.deleteBookmarkAtView(id: Int) {
    val tempList = this.value.filter { WantedArticleBookmark ->
        WantedArticleBookmark.id != id
    }.map {
        it.copy()
    }
    this.value = tempList.toMutableList()
}
