package com.example.league_of_legends_application.viewmodel

import android.util.Log
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
    private val currentPage = MutableStateFlow(1)
    private val totalPages = 15
    private val pageSize = 20
    private val TAG = "ChampionViewModel"

    init {
        fetchChampions()
    }

    private fun fetchChampions(page: Int = 1, size: Int = pageSize) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val fetchedChampions = ChampionService.fetchChampions(size, page)
                _champions.value += fetchedChampions
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
            var currentPage = 1
            val allChampions = mutableListOf<Champion>()

            while (currentPage <= totalPages) {
                try {
                    val pageChampions = ChampionService.fetchChampions(pageSize, currentPage)
                    if (pageChampions.isEmpty()) break
                    allChampions.addAll(pageChampions)
                    currentPage++
                } catch (e: Exception) {
                    Log.e(TAG, "Error fetching champions on page $currentPage: ${e.localizedMessage}")
                    break
                }
            }

            _champions.value = allChampions
            _isLoading.value = false
        }
    }

    fun loadNextPage() {
        if (currentPage.value < totalPages && !_isLoading.value) {
            fetchChampions(page = currentPage.value + 1)
        }
    }
}
