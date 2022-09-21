package com.example.home_rent_app.util

import android.view.View
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

// 클릭 이벤트를 Flow 로 변경
fun View.clickToFlow(): Flow<Unit> = callbackFlow {
    setOnClickListener {
        this.trySend(Unit)
    }
    awaitClose { setOnClickListener(null) }
}

// 마지막 발행 시간과 현재 시간을 비교해서 이벤트 발행, 나머지는 무시
// Flow 에는 throttleFirst 가 존재하지 않기 때문에 만들어준다.
fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = flow {
    var lastEmissionTime = 0L
    collect { upStream ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmissionTime > windowDuration) {
            lastEmissionTime = currentTime
            emit(upStream)
        }
    }
}
