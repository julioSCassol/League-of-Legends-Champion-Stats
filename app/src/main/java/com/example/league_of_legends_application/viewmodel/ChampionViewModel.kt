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

class ChampionViewModel(
    private val repository: ChampionRepository
) : ViewModel() {

    private val _champions = MutableStateFlow<List<Champion>>(emptyList())
    val champions: StateFlow<List<Champion>> = _champions

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val TAG = "ChampionViewModel"

    init {
        fetchChampions()
    }

    private fun fetchChampions() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d(TAG, "Fetching champions from cache...")
                val cachedChampions = repository.getAllChampions()

                if (cachedChampions.isNotEmpty()) {
                    Log.d(TAG, "Champions found in cache: ${cachedChampions.size}")
                    _champions.value = cachedChampions
                } else {
                    Log.d(TAG, "No champions found in cache.")
                }

                Log.d(TAG, "Fetching champions from the network...")
                val fetchedChampions = ChampionService.fetchChampions()

                if (fetchedChampions != cachedChampions) {
                    Log.d(TAG, "Champions from network differ from cache, updating cache...")
                    repository.updateChampions(fetchedChampions)
                    _champions.value = fetchedChampions
                    Log.d(TAG, "Cache updated with new champions: ${fetchedChampions.size}")
                } else {
                    Log.d(TAG, "No changes in the fetched champions.")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching champions: ${e.localizedMessage}")
                if (_champions.value.isEmpty()) {
                    Log.d(TAG, "No champions in cache or network, setting empty list.")
                    _champions.value = emptyList()
                }
            } finally {
                _isLoading.value = false
                Log.d(TAG, "Loading state set to false.")
            }
        }
    }
}
