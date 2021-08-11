package br.com.fetchpictures.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.fetchpictures.interactors.SearchMediaImage
import br.com.fetchpictures.model.MediaImage
import kotlinx.coroutines.*

class SearchViewModel(private val searchMediaImage: SearchMediaImage): ViewModel() {

    private val images : MutableLiveData<List<MediaImage>> = MutableLiveData()

    fun getImages(): LiveData<List<MediaImage>> {
        return images
    }

    fun searchImagesBy(query: String) {
        runBlocking {
            Log.i("here", "searchImagesBy")

            val response = withContext(Dispatchers.IO) {
                searchMediaImage.exec(query, 1)
            }

            images.value = response
        }
    }

}