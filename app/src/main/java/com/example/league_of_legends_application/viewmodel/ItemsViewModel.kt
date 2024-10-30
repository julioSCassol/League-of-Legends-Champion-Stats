package com.example.league_of_legends_application.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.league_of_legends_application.model.Item
import com.example.league_of_legends_application.network.ItemService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val pageSize = 20
    private val totalItemsNeeded = 180
    private val TAG = "ItemViewModel"

    init {
        fetchAllItems()
    }

    private suspend fun fetchItems(page: Int = 1, size: Int = pageSize): List<Item> {
        return try {
            ItemService.fetchItems(size, page)
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching items: ${e.localizedMessage}")
            emptyList()
        }
    }

    private fun fetchAllItems() {
        viewModelScope.launch {
            _isLoading.value = true
            val allItems = mutableListOf<Item>()
            var page = 1

            while (allItems.size < totalItemsNeeded) {
                val pageItems = fetchItems(pageSize, page)
                if (pageItems.isEmpty()) break
                allItems.addAll(pageItems)
                page++
            }

            _items.value = allItems.take(totalItemsNeeded)
            _isLoading.value = false
        }
    }

    fun getRandomItems(count: Int): List<Item> {
        return _items.value.shuffled().take(count)
    }
}
