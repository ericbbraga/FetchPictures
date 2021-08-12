package br.com.fetchpictures.environment.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.fetchpictures.R
import br.com.fetchpictures.databinding.ActivityMainBinding
import br.com.fetchpictures.environment.adapters.MediaImageAdapter
import br.com.fetchpictures.environment.adapters.MediaImageLoader
import br.com.fetchpictures.environment.application.App
import br.com.fetchpictures.model.MediaImage
import br.com.fetchpictures.viewmodels.SearchViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var searchViewModel: SearchViewModel
    @Inject lateinit var mediaImageLoader: MediaImageLoader

    private lateinit var binding: ActivityMainBinding
    private lateinit var mediaImageAdapter: MediaImageAdapter

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

            setAdapter()
        }
    }

    private fun ActivityMainBinding.setAdapter() {
        mediaImageAdapter = MediaImageAdapter(mediaImageLoader)

        val lookUp = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = 1
        }

        val layoutManager = GridLayoutManager(this@MainActivity, 2)
        layoutManager.spanSizeLookup = lookUp

        viewImagesRecyclerView.layoutManager = layoutManager
        viewImagesRecyclerView.adapter = mediaImageAdapter
    }

    private fun startSearchImages(query: String) {
        mediaImageAdapter.clear()
        searchViewModel.searchImagesBy(query)
    }

    private fun setViewModels() {
        searchViewModel.getImages().observe(this) { medias ->
            medias.forEach { mediaImage ->
                println("MediaImage $mediaImage")
            }

            mediaImageAdapter.add(medias)
        }
    }
}