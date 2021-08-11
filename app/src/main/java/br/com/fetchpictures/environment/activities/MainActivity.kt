package br.com.fetchpictures.environment.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.fetchpictures.R
import br.com.fetchpictures.databinding.ActivityMainBinding
import br.com.fetchpictures.environment.application.App
import br.com.fetchpictures.viewmodels.SearchViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        autoBindFromApplication()
        setViews()
        setViewModels()
    }

    private fun autoBindFromApplication() {
        (applicationContext as App).inject(this)
    }

    private fun setViews() {
        binding = ActivityMainBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            buttonSearch.setOnClickListener {
                val query = editSearch.text.toString()
                startSearchImages(query)
            }
        }
    }

    private fun startSearchImages(query: String) {
        searchViewModel.searchImagesBy(query)
    }

    private fun setViewModels() {
        searchViewModel.getImages().observe(this) {
            it.forEach { mediaImage ->
                println("MediaImage $mediaImage")
            }
        }
    }
}