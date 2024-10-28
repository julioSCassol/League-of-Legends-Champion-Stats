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
    private val currentPage = MutableStateFlow(1)
    private val pageSize = 20
    private val TAG = "ItemViewModel"

    init {
        fetchAllItems()
    }

    private fun fetchItems(page: Int = 1, size: Int = pageSize) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val fetchedItems = ItemService.fetchItems(size, page)
                if (fetchedItems.isNotEmpty()) {
                    _items.value += fetchedItems
                    currentPage.value = page + 1
                } else {
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching items: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchAllItems() {
        viewModelScope.launch {
            _isLoading.value = true
            val allItems = mutableListOf<Item>()
            var page = 1

            while (true) {
                try {
                    val pageItems = ItemService.fetchItems(pageSize, page)
                    if (pageItems.isEmpty()) break
                    allItems.addAll(pageItems)
                    page++
                } catch (e: Exception) {
                    Log.e(TAG, "Error fetching items on page $page: ${e.localizedMessage}")
                    break
                }
            }

            _items.value = allItems
            _isLoading.value = false
        }
    }

    fun fetchRandomItems(count: Int = 6) {
        viewModelScope.launch {
            if (_items.value.size < count) {
                fetchAllItems()
            }
            _items.value = _items.value.shuffled().take(count)
        }
    }
}

