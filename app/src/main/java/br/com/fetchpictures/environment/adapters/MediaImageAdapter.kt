package br.com.fetchpictures.environment.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.fetchpictures.R
import br.com.fetchpictures.model.MediaImage

class MediaImageAdapter(private val imageLoader: MediaImageLoader) :
    RecyclerView.Adapter<MediaImageAdapter.MediaImageViewHolder>() {
    private val mediaImages = mutableListOf<MediaImage>()

    fun add(medias: List<MediaImage>) {
        mediaImages.addAll(medias)
        notifyDataSetChanged()
    }

    fun clear() {
        mediaImages.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaImageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.media_images_layout, parent, false
        )

        return MediaImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MediaImageViewHolder, position: Int) {
        val view = holder.itemView
        val imageView = view.findViewById<ImageView>(R.id.media_item_image)
        val mediaImage = mediaImages[position]

        val uri = Uri.parse(mediaImage.lowQualityUrl)
        imageLoader.loadInto(uri, imageView)
    }

    override fun getItemCount() = mediaImages.size

    inner class MediaImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}