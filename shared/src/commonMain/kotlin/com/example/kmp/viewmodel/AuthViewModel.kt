package com.example.kmp.viewmodel

import com.example.kmp.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class AuthViewModel(
    private val repo: AuthRepository = AuthRepository()
) {

    private val scope = CoroutineScope(Dispatchers.Default)

    private val _state = MutableStateFlow<String?>(null)
    val state: StateFlow<String?> = _state

    fun login(username: String, password: String) {
        scope.launch {
            try {
                val token = repo.login(username, password)
                _state.value = token
            } catch (e: Exception) {
                _state.value = "ERROR: ${e.message}"
            }
        }
    }
}
