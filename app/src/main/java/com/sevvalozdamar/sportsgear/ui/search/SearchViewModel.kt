package com.sevvalozdamar.sportsgear.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevvalozdamar.sportsgear.data.model.ProductUI
import com.sevvalozdamar.sportsgear.data.repository.ProductRepository
import com.sevvalozdamar.sportsgear.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private var _searchState = MutableLiveData<SearchState>()
    val searchState: LiveData<SearchState> get() = _searchState

    fun getSearchProduct(query: String) = viewModelScope.launch {
        _searchState.value = SearchState.Loading
        _searchState.value = when (val result = productRepository.getSearchProduct(query)) {
            Resource.Loading -> SearchState.Loading
            is Resource.Success -> SearchState.SuccessScreen(result.data)
            is Resource.Fail -> SearchState.EmptyScreen(result.failMessage)
            is Resource.Error -> SearchState.PopUpScreen(result.errorMessage)
        }
    }
}

sealed interface SearchState {
    data object Loading : SearchState
    data class SuccessScreen(val products: List<ProductUI>) : SearchState
    data class PopUpScreen(val errorMessage: String) : SearchState
    data class EmptyScreen(val failMessage: String) : SearchState
}