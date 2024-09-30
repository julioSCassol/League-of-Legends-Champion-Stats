package com.example.league_of_legends_application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.league_of_legends_application.model.Champion
import com.example.league_of_legends_application.network.ChampionService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChampionViewModel : ViewModel() {
    private val _champions = MutableStateFlow<List<Champion>>(emptyList())
    val champions: StateFlow<List<Champion>> = _champions

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchChampions()
    }

    fun fetchChampions() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val fetchedChampions = ChampionService.fetchChampions()
                _champions.value = fetchedChampions
            } catch (e: Exception) {
                _champions.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
