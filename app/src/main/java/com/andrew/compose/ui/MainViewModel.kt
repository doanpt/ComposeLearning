package com.andrew.compose.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    val inputText = mutableStateOf("")

    val names = MutableLiveData(mutableListOf<String>())

    private val _selectedPageIndex = mutableIntStateOf(0)
    val selectedPageIndex = _selectedPageIndex

    fun addName(name: String) {
        names.value?.add(name)
        inputText.value = ""
    }

    fun changeInputText(newText: String) {
        inputText.value = newText
    }

    fun changeSelectedPage(index: Int) {
        _selectedPageIndex.value = index
    }
}