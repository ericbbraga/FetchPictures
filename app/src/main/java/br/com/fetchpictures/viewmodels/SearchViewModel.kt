package br.com.fetchpictures.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.fetchpictures.interactors.SearchMediaImage
import br.com.fetchpictures.model.MediaImage
import kotlinx.coroutines.*

class SearchViewModel(private val searchMediaImage: SearchMediaImage): ViewModel() {

    private val images : MutableLiveData<List<MediaImage>> = MutableLiveData()
    private var currentPage: Int = 1

    fun getImages(): LiveData<List<MediaImage>> {
        return images
    }

    fun searchImagesBy(query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = withContext(Dispatchers.IO) {
                searchMediaImage.exec(query, currentPage)
            }

            images.value = response
        }
    }

    fun loadMoreItemsFrom(query: String) {
        currentPage += 1
        searchImagesBy(query)
    }

}