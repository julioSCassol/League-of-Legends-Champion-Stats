package com.example.league_of_legends_application.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.league_of_legends_application.model.Champion
import com.example.league_of_legends_application.network.ChampionService
import com.example.league_of_legends_application.repositories.ChampionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChampionViewModel(private val repository: ChampionRepository) : ViewModel() {

    private val _champions = MutableStateFlow<List<Champion>>(emptyList())
    val champions: StateFlow<List<Champion>> = _champions

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val currentPage = MutableStateFlow(1)
    private val pageSize = 20
    private val TAG = "ChampionViewModel"

    init {
        fetchAllChampions()
    }

    private fun fetchChampions(page: Int = 1, size: Int = pageSize) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val fetchedChampions = ChampionService.fetchChampions(size, page)
                if (fetchedChampions.isNotEmpty()) {
                    _champions.value += fetchedChampions
                    currentPage.value = page + 1
                } else {
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching champions: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchAllChampions() {
        viewModelScope.launch {
            _isLoading.value = true
            val allChampions = mutableListOf<Champion>()
            var page = 1

            while (true) {
                try {
                    val pageChampions = ChampionService.fetchChampions(pageSize, page)
                    if (pageChampions.isEmpty()) break
                    allChampions.addAll(pageChampions)
                    page++
                } catch (e: Exception) {
                    Log.e(TAG, "Error fetching champions on page $page: ${e.localizedMessage}")
                    break
                }
            }

            _champions.value = allChampions
            _isLoading.value = false
        }
    }

    fun loadNextPage() {
        if (!_isLoading.value) {
            fetchChampions(page = currentPage.value + 1)
        }
    }
}
