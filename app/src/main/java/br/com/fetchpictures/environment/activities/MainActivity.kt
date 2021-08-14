package br.com.fetchpictures.environment.activities

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
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

    @Inject
    lateinit var searchViewModel: SearchViewModel
    @Inject
    lateinit var mediaImageLoader: MediaImageLoader

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
                hideKeyboard()
                startSearchImages()
            }

            setAdapter()
        }
    }

    private fun hideKeyboard() {
        val inputMethod = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethod.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    private fun startSearchImages() {
        binding.middleProgressbar.visibility = View.VISIBLE

        val query = binding.editSearch.text.toString()
        mediaImageAdapter.clear()
        searchViewModel.searchImagesBy(query)
    }

    private fun ActivityMainBinding.setAdapter() {
        mediaImageAdapter = MediaImageAdapter(mediaImageLoader)

        val lookUp = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = 1
        }

        // Create Custom RecycleView that
        // exposes when it reach the final element

        val layoutManager = GridLayoutManager(this@MainActivity, 2)
        layoutManager.spanSizeLookup = lookUp

        viewImagesRecyclerView.layoutManager = layoutManager
        viewImagesRecyclerView.adapter = mediaImageAdapter

        viewImagesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0 && !recyclerView.canScrollVertically(1)) {
                    loadMoreItems()
                }
            }
        })
    }

    private fun loadMoreItems() {
        val query = binding.editSearch.text.toString()
        searchViewModel.loadMoreItemsFrom(query)
    }

    private fun setViewModels() {
        searchViewModel.getImages().observe(this) { medias ->

            hideProgressBars()

            if (medias.isEmpty()) {
                showEmptyQueryListMessage()
            }

            mediaImageAdapter.add(medias)
        }
    }

    private fun hideProgressBars() {
        binding.middleProgressbar.visibility = View.GONE
    }

    private fun showEmptyQueryListMessage() {
        val query = binding.editSearch.text.toString()
        Toast.makeText(
            this,
            "Could not found elements with search query = $query",
            Toast.LENGTH_SHORT
        ).show()
    }
}