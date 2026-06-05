package com.leticiafernandes.letsmovie.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leticiafernandes.letsmovie.login.domain.CreateGuestSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val createGuestSessionUseCase: CreateGuestSessionUseCase
) : ViewModel() {

    private val _ready = MutableStateFlow(false)
    val ready: StateFlow<Boolean> = _ready.asStateFlow()

    init {
        ensureSession()
    }

    private fun ensureSession() {
        viewModelScope.launch {
            createGuestSessionUseCase()
            _ready.value = true
        }
    }
}
